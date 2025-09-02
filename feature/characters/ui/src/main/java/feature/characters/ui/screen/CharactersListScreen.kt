package feature.characters.ui.screen

import android.widget.Toast
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.components.scaffold.CustomScaffold
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.NavEventHandler
import core.ui.utils.SideEffectHandler
import domain.settings.model.ThemeMode
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.CharactersListFilterSettingsDestination
import feature.characters.navigation.result.CharactersListFilterSettingsResult
import feature.characters.presentation.model.CharactersListFilterSettings
import feature.characters.presentation.viewmodel.CharactersListViewModel
import feature.characters.presentation.viewmodel.CharactersSearchViewModel
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchIntent
import feature.characters.ui.R
import feature.characters.ui.screen.components.list.CharactersListScreenContent
import feature.characters.ui.screen.components.list.CharactersListTopAppBar
import feature.characters.ui.screen.components.search.CharactersSearchSection
import feature.characters.ui.screen.components.search.CharactersSearchTopAppBar
import feature.characters.ui.screen.preview.CharactersPreviewMocks
import feature.settings.presentation.viewmodel.SettingsViewModel
import feature.splash.presentation.viewmodel.SplashViewModel
import navigation.compose.router.Router
import navigation.compose.utils.navigateForResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    searchViewModel: CharactersSearchViewModel,
    settingsViewModel: SettingsViewModel,
    splashViewModel: SplashViewModel,
    router: Router,
) {
    val gridState = rememberLazyGridState()

    CharactersListSideEffectHandler(viewModel = viewModel, gridState = gridState)

    CharactersListNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(CharactersListIntent.OnViewStarted)
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    if (!viewState.showProgress) splashViewModel.requirementDone(SplashViewModel.Requirement.START_SCREEN_DATA_SET_LOADED)
    val searchViewState by searchViewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(
        topAppBar = {
            if (searchViewState.isSearching) {
                CharactersSearchTopAppBar(
                    searchQuery = searchViewState.searchQuery,
                    onSearchQueryChange = { searchViewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(it)) },
                    onSearchToggle = { searchViewModel.onIntent(CharactersSearchIntent.OnSearchToggle) },
                )
            } else {
                CharactersListTopAppBar(
                    onThemeSelected = { newThemeMode -> settingsViewModel.onThemeSelected(newThemeMode) },
                    onSortClicked = { viewModel.onIntent(CharactersListIntent.OnSortClicked) },
                    onSearchClicked = { searchViewModel.onIntent(CharactersSearchIntent.OnSearchToggle) },
                    onFilterClicked = { viewModel.onIntent(CharactersListIntent.OnFilterClicked) },
                )
            }
        }
    ) {
        CharactersListScreenContent(
            gridState = gridState,
            viewState = viewState,
            onItemClicked = { viewModel.onIntent(CharactersListIntent.OnCharacterClicked(it)) },
            onRefreshed = { viewModel.onIntent(CharactersListIntent.OnRefreshed) },
            onScrolled = { viewModel.onIntent(CharactersListIntent.OnScrolled(it)) },
        )
        CharactersSearchSection(
            listState = rememberLazyListState(),
            viewState = searchViewState,
            onItemClicked = { viewModel.onIntent(CharactersListIntent.OnCharacterClicked(it)) },
            onScrolled = { searchViewModel.onIntent(CharactersSearchIntent.OnScrolled(it)) },
        )
    }
}

@Composable
private fun CharactersListSideEffectHandler(viewModel: CharactersListViewModel, gridState: LazyGridState) {
    val context = LocalContext.current
    SideEffectHandler(viewModel) { mviEffect ->
        when (mviEffect) {
            is CharactersListSideEffect.ShowErrorMessage -> {
                val message = context.getString(R.string.error_unknown)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

            is CharactersListSideEffect.ScrollToTop -> gridState.animateScrollToItem(0)
        }
    }
}

@Composable
private fun CharactersListNavEventHandler(viewModel: CharactersListViewModel, router: Router) {
    NavEventHandler(viewModel) { mviNavEvent ->
        when (mviNavEvent) {
            is CharactersListNavEvent.NavigateToFilterSettings -> {
                val destination = CharactersListFilterSettingsDestination(
                    status = mviNavEvent.filterSettings.statusType,
                    gender = mviNavEvent.filterSettings.genderType,
                )
                router.navigateForResult<CharactersListFilterSettingsResult>(destination)?.let { result ->
                    viewModel.onIntent(
                        CharactersListIntent.OnFilterApplied(
                            filterSettings = CharactersListFilterSettings(
                                statusType = result.statusType,
                                genderType = result.genderType,
                            ),
                        )
                    )
                }
            }

            is CharactersListNavEvent.NavigateToCharacterDetails -> {
                router.navigateTo(CharacterDetailsDestination(mviNavEvent.character.id))
            }

            is CharactersListNavEvent.NavigateBack -> router.back()
        }
    }
}

@Preview
@Composable
private fun CharactersListScreenPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) theme: ThemeMode) {
    RickAndMortyTheme(themeMode = theme) {
        CustomScaffold(
            topAppBar = {
                CharactersListTopAppBar(
                    onThemeSelected = {},
                    onSortClicked = {},
                    onSearchClicked = {},
                    onFilterClicked = {},
                )
            }
        ) {
            CharactersListScreenContent(
                viewState = CharactersListViewState(
                    charactersList = CharactersPreviewMocks.charactersList,
                    isRefreshing = false,
                    showProgress = false,
                ),
                gridState = rememberLazyGridState(),
                onItemClicked = {},
                onRefreshed = {},
                onScrolled = {},
            )
        }
    }
}
