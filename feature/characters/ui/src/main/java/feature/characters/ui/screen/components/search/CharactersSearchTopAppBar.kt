package feature.characters.ui.screen.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.senyk.rickandmorty.feature.characters.ui.R
import core.ui.components.topappbar.SimpleTopAppBar
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.colorscheme.onPrimaryHint
import kotlinx.coroutines.delay
import com.senyk.rickandmorty.core.ui.R as CoreR

@Composable
internal fun CharactersSearchTopAppBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchToggle: () -> Unit,
    enterAnimMillis: Long = 0,
) {
    val focusRequester = remember { FocusRequester() }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = LocalTextSelectionColors.current.handleColor,
        backgroundColor = MaterialTheme.colorScheme.onPrimaryHint,
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        SimpleTopAppBar(
            title = {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.hint_search),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                            )
                        )
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                )

                LaunchedEffect(Unit) {
                    if (searchQuery.isEmpty()) {
                        delay(enterAnimMillis - 200)
                        focusRequester.requestFocus()
                    }
                }
            },
            onNavigateBackClicked = onSearchToggle,
            menu = {
                ClearSearchQueryMenuItem(
                    visible = searchQuery.isNotEmpty(),
                    onClicked = { onSearchQueryChange("") },
                )
            },
            modifier = modifier
        )
    }
}

@Composable
private fun ClearSearchQueryMenuItem(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onClicked: () -> Unit,
) {
    val animationDuration = 500
    val rotationAngle by animateFloatAsState(
        label = "IconRotation",
        targetValue = if (visible) 0f else 90f,
        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing),
    )
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(animationDuration)),
        exit = fadeOut(animationSpec = tween(animationDuration))
    ) {
        IconButton(
            onClick = onClicked,
            modifier = modifier
                .graphicsLayer {
                    rotationZ = rotationAngle
                }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(CoreR.string.menu_item_clear),
            )
        }
    }
}

@Preview
@Composable
private fun CharactersSearchTopAppBarPreview() {
    RickAndMortyTheme {
        CharactersSearchTopAppBar(
            searchQuery = "Rick San",
            onSearchQueryChange = {},
            onSearchToggle = {},
        )
    }
}

@Preview
@Composable
private fun CharactersSearchTopAppBarEmptyPreview() {
    RickAndMortyTheme {
        CharactersSearchTopAppBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onSearchToggle = {},
        )
    }
}
