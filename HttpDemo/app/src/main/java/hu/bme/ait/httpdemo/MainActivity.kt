package hu.bme.ait.httpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.ait.httpdemo.ui.navigation.MainScreenRoute
import hu.bme.ait.httpdemo.ui.navigation.MoneyScreenRoute
import hu.bme.ait.httpdemo.ui.navigation.NewsScreenRoute
import hu.bme.ait.httpdemo.ui.screen.mainscreen.MainScreen
import hu.bme.ait.httpdemo.ui.screen.moneyscreen.MoneyScreen
import hu.bme.ait.httpdemo.ui.screen.newsscreen.NewsScreen
import hu.bme.ait.httpdemo.ui.theme.HttpDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HttpDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(modifier: Modifier) {
    // this is why the navigation starts with the HomeScreen
    val backStack = rememberNavBackStack(MainScreenRoute)

    // NavDisplay can actually display the current screen from those listed in the entryProvider
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<MainScreenRoute> {
                MainScreen(
                    onMoneyAPISelected = {backStack.add(MoneyScreenRoute)},
                    onNewsAPISelected = {backStack.add(NewsScreenRoute)}
                )
            }
            entry<MoneyScreenRoute> {
                MoneyScreen()
            }
            entry<NewsScreenRoute> {
                NewsScreen()
            }
        }
    )
}