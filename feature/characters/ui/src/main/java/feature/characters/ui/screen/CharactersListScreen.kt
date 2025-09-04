package feature.characters.ui.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.ui.utils.NavEventHandler
import core.ui.utils.SideEffectHandler
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.navigation.CharactersListFilterDestination
import feature.characters.navigation.result.CharactersListFilterSettingsResult
import feature.characters.presentation.model.CharactersListFilter
import feature.characters.presentation.viewmodel.CharactersListViewModel
import feature.characters.presentation.viewmodel.CharactersSearchViewModel
import feature.characters.presentation.viewmodel.mvi.list.CharactersListIntent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListNavEvent
import feature.characters.presentation.viewmodel.mvi.list.CharactersListSideEffect
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchIntent
import feature.characters.ui.R
import feature.characters.ui.navigation.toCharacterNavArg
import feature.characters.ui.screen.components.list.CharactersListScreenContent
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
    if (!viewState.showBlockingProgress) splashViewModel.requirementDone(SplashViewModel.Requirement.START_SCREEN_DATA_SET_LOADED)
    val searchViewState by searchViewModel.uiState.collectAsStateWithLifecycle()
    CharactersListScreenContent(
        gridState = gridState,
        viewState = viewState,
        searchViewState = searchViewState,
        onSearchClicked = { searchViewModel.onIntent(CharactersSearchIntent.OnSearchToggle) },
        onSearchQueryChanged = { searchViewModel.onIntent(CharactersSearchIntent.OnSearchQueryChanged(it)) },
        onFilterClicked = { viewModel.onIntent(CharactersListIntent.OnFilterClicked) },
        onThemeSelected = { settingsViewModel.onThemeSelected(it) },
        onCharacterClicked = { viewModel.onIntent(CharactersListIntent.OnCharacterClicked(it)) },
        onRefreshed = { viewModel.onIntent(CharactersListIntent.OnRefreshed) },
        onCharactersGridScrolled = { viewModel.onIntent(CharactersListIntent.OnScrolled(it)) },
        onSearchResultsScrolled = { searchViewModel.onIntent(CharactersSearchIntent.OnScrolled(it)) },
    )

    BackHandler(searchViewState.isSearching) {
        searchViewModel.onIntent(CharactersSearchIntent.OnSearchToggle)
    }
}

@Composable
private fun CharactersListSideEffectHandler(viewModel: CharactersListViewModel, gridState: LazyGridState) {
    val context = LocalContext.current
    SideEffectHandler(viewModel) { mviEffect ->
        when (mviEffect) {
            is CharactersListSideEffect.ShowErrorMessage -> {
                val message = context.getString(R.string.message_error_unknown)
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
            is CharactersListNavEvent.NavigateToCharactersListFilter -> {
                val destination = CharactersListFilterDestination(
                    status = mviNavEvent.filter.statusType,
                    gender = mviNavEvent.filter.genderType,
                )
                router.navigateForResult<CharactersListFilterSettingsResult>(destination)?.let { result ->
                    viewModel.onIntent(
                        CharactersListIntent.OnFilterApplied(
                            charactersListFilter = CharactersListFilter(
                                statusType = result.statusType,
                                genderType = result.genderType,
                            ),
                        )
                    )
                }
            }

            is CharactersListNavEvent.NavigateToCharacterDetails -> {
                router.navigateTo(CharacterDetailsDestination(mviNavEvent.character.toCharacterNavArg()))
            }

            is CharactersListNavEvent.NavigateBack -> router.back()
        }
    }
}
