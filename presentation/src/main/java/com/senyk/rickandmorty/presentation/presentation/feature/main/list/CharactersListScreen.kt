package com.senyk.rickandmorty.presentation.presentation.feature.main.list

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
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharactersListViewModel
import com.senyk.rickandmorty.presentation.presentation.feature.main.list.components.CharactersListScreenContent
import com.senyk.rickandmorty.presentation.presentation.feature.main.list.components.appbar.CharactersListTopAppBar
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListIntent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListNavEvent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListSideEffect
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListViewState
import com.senyk.rickandmorty.presentation.presentation.feature.main.preview.CharactersPreviewMocks
import com.senyk.rickandmorty.presentation.presentation.feature.navigation.CharacterDetailsDestination
import com.senyk.rickandmorty.presentation.presentation.feature.navigation.toNavArg
import core.ui.components.scaffold.CustomScaffold
import core.ui.preview.ThemePreviewParameterProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.ThemeMode
import core.ui.utils.NavEventHandler
import core.ui.utils.SideEffectHandler
import navigation.compose.router.Router

@Composable
internal fun CharactersListScreen(viewModel: CharactersListViewModel, router: Router) {
    val gridState = rememberLazyGridState()

    CharactersListSideEffectHandler(viewModel = viewModel, gridState = gridState)

    CharactersListNavEventHandler(viewModel = viewModel, router = router)

    LaunchedEffect(viewModel) {
        viewModel.onIntent(CharactersListIntent.OnViewStarted)
    }

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    CustomScaffold(
        topAppBar = {
            CharactersListTopAppBar(onSortClicked = { viewModel.onIntent(CharactersListIntent.OnSortClicked) })
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
                val destination = mviNavEvent.character.toNavArg()
                router.navigateTo(CharacterDetailsDestination(destination))
            }

            is CharactersListNavEvent.NavigateBack -> router.back()
        }
    }
}

@Preview
@Composable
private fun CharactersListScreenPreview(@PreviewParameter(provider = ThemePreviewParameterProvider::class) theme: ThemeMode) {
    RickAndMortyTheme(themeMode = theme) {
        CustomScaffold(topAppBar = { CharactersListTopAppBar(onSortClicked = {}) }) {
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
