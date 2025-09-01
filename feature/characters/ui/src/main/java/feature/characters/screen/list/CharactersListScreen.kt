package feature.characters.screen.list

import android.widget.Toast
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import feature.characters.R
import feature.characters.navigation.CharacterDetailsDestination
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.screen.list.components.CharactersListScreenContent
import feature.characters.screen.list.components.appbar.CharactersListTopAppBar
import feature.characters.screen.list.mvi.CharactersListIntent
import feature.characters.screen.list.mvi.CharactersListNavEvent
import feature.characters.screen.list.mvi.CharactersListSideEffect
import feature.characters.screen.list.mvi.CharactersListViewState
import feature.settings.viewmodel.SettingsViewModel
import navigation.compose.router.Router

@Composable
internal fun CharactersListScreen(
    viewModel: CharactersListViewModel,
    settingsViewModel: SettingsViewModel,
    router: Router,
) {
    val gridState = rememberLazyGridState()

    CharactersListSideEffectHandler(viewModel = viewModel, gridState = gridState)

    CharactersListNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(CharactersListIntent.OnViewStarted)
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(
        topAppBar = {
            CharactersListTopAppBar(
                onThemeSelected = { newThemeMode -> settingsViewModel.onThemeSelected(newThemeMode) },
                onSortClicked = { viewModel.onIntent(CharactersListIntent.OnSortClicked) },
            )
        }
    ) {
        CharactersListScreenContent(
            gridState = gridState,
            viewState = viewState,
            onItemClicked = { viewModel.onIntent(CharactersListIntent.OnCharacterClicked(it)) },
            onRefreshed = { viewModel.onIntent(CharactersListIntent.OnRefreshed) },
            onScrolled = { viewModel.onIntent(CharactersListIntent.OnScrolled(it)) },
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
