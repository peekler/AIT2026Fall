package hu.bme.ait.todoapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object TodoScreenRoute: NavKey

@Serializable
data class SummaryScreenRoute(
    val allTodoNum: Int,
    val importantTodoNum: Int
): NavKey