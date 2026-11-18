package hu.bme.ait.httpdemo.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MainScreenRoute: NavKey

@Serializable
data object MoneyScreenRoute: NavKey

@Serializable
data object NewsScreenRoute: NavKey