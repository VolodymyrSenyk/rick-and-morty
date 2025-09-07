package feature.characters.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.dialog.BaseDialogContent
import core.ui.components.dialog.model.DialogButtonData
import core.ui.components.dialog.parts.BaseDialogButtonsRow
import core.ui.components.dropdown.SimpleDropdown
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import feature.characters.navigation.result.CharactersListFilterResult
import feature.characters.ui.R
import navigation.compose.router.Router
import navigation.compose.router.RouterStub
import core.ui.R as CoreR

@Composable
internal fun CharactersListFilterDialog(
    router: Router,
    previouslySelectedStatus: StatusType?,
    previouslySelectedGender: GenderType?,
) {
    var selectedStatus by remember { mutableStateOf(previouslySelectedStatus) }
    val statuses = mutableListOf<Pair<StatusType?, String>>(null to stringResource(R.string.status_all))
    statuses.addAll(
        StatusType.entries.map { status ->
            when (status) {
                StatusType.ALIVE -> status to stringResource(R.string.status_alive)
                StatusType.DEAD -> status to stringResource(R.string.status_dead)
                StatusType.UNKNOWN -> status to stringResource(R.string.status_unknown)
            }
        }
    )

    var selectedGender by remember { mutableStateOf(previouslySelectedGender) }
    val genders = mutableListOf<Pair<GenderType?, String>>(null to stringResource(R.string.gender_all))
    genders.addAll(
        GenderType.entries.map { gender ->
            when (gender) {
                GenderType.UNKNOWN -> gender to stringResource(R.string.gender_unknown)
                GenderType.FEMALE -> gender to stringResource(R.string.gender_female)
                GenderType.MALE -> gender to stringResource(R.string.gender_male)
                GenderType.GENDERLESS -> gender to stringResource(R.string.gender_genderless)
            }
        }
    )

    BaseDialogContent(
        onDismiss = router::back,
        title = stringResource(R.string.dialog_title_characters_list_filter_settings),
        message = {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.Padding.VerySmall)) {
                SimpleDropdown(
                    options = statuses.map { it.second },
                    selectedOption = statuses.firstOrNull { it.first == selectedStatus }?.second,
                    onOptionSelected = { statusString ->
                        selectedStatus = statuses.first { it.second == statusString }.first
                    },
                )
                SimpleDropdown(
                    options = genders.map { it.second },
                    selectedOption = genders.firstOrNull { it.first == selectedGender }?.second,
                    onOptionSelected = { genderString ->
                        selectedGender = genders.first { it.second == genderString }.first
                    },
                )
                BaseDialogButtonsRow(
                    outlinedButtonData = DialogButtonData(
                        text = stringResource(CoreR.string.dialog_answer_cancel),
                        onClick = router::back,
                    ),
                    filledButtonData = DialogButtonData(
                        text = stringResource(CoreR.string.dialog_answer_apply),
                        onClick = {
                            router.backWith(
                                CharactersListFilterResult(
                                    status = selectedStatus,
                                    gender = selectedGender,
                                )
                            )
                        },
                    ),
                )
            }
        },
    )
}

@Preview
@Composable
private fun CharactersListFilterSettingsDialogPreview() {
    RickAndMortyTheme {
        CharactersListFilterDialog(
            router = RouterStub(),
            previouslySelectedStatus = null,
            previouslySelectedGender = null,
        )
    }
}
