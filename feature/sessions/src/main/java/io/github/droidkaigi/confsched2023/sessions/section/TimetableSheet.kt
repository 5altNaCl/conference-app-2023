package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableItem.Session
import io.github.droidkaigi.confsched2023.sessions.component.TimetableScreenScrollState
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.Empty
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.GridTimetable
import io.github.droidkaigi.confsched2023.sessions.section.TimetableSheetUiState.ListTimetable

sealed interface TimetableSheetUiState {
    object Empty : TimetableSheetUiState
    data class ListTimetable(
        val timetableListUiState: TimetableListUiState,
    ) : TimetableSheetUiState

    data class GridTimetable(
        val timetableGridUiState: TimetableGridUiState,
    ) : TimetableSheetUiState
}

@Composable
fun TimetableSheet(
    onContributorsClick: () -> Unit,
    uiState: TimetableSheetUiState,
    timetableScreenScrollState: TimetableScreenScrollState,
    onFavoriteClick: (Session) -> Unit,
    modifier: Modifier = Modifier,
) {
    val corner by animateIntAsState(
        if (timetableScreenScrollState.isScreenLayoutCalculating || timetableScreenScrollState.isSheetExpandable) 40 else 0,
        label = "Timetable corner state",
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
    ) {
        when (uiState) {
            is Empty -> {
                Text(
                    text = "empty",
                    modifier = Modifier.testTag("empty"),
                )
            }

            is ListTimetable -> {
                TimetableList(
                    uiState = uiState.timetableListUiState,
                    onContributorsClick = onContributorsClick,
                    onFavoriteClick = onFavoriteClick,
                )
            }

            is GridTimetable -> {
                TimetableGrid(
                    uiState = uiState.timetableGridUiState,
                    onBookmarked = {},
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}