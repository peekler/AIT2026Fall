package hu.ait.aitforum.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object LoginScreenRoute: NavKey

@Serializable
data object MessagesScreenRoute: NavKey

@Serializable
data object WriteMessageScreenRoute: NavKey