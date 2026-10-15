package com.example.highlowgamedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.highlowgamedemo.ui.navigation.GameScreenRoute
import com.example.highlowgamedemo.ui.navigation.MainMenuScreenRoute
import com.example.highlowgamedemo.ui.screen.GameScreen
import com.example.highlowgamedemo.ui.screen.GameViewModel
import com.example.highlowgamedemo.ui.screen.MainMenuScreen
import com.example.highlowgamedemo.ui.theme.HighLowGameDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HighLowGameDemoTheme {
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
fun NavGraph(modifier: Modifier = Modifier) {
    // this is why the navigation starts with the MainScreen
    val backStack = rememberNavBackStack(MainMenuScreenRoute)

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
            entry<MainMenuScreenRoute> {
                MainMenuScreen(
                    onStartClick = { upperBound ->
                         backStack.add(GameScreenRoute(upperBound))
                    }
                )
            }
            entry<GameScreenRoute> {
                // the 'it' represents the GameScreenRoute(100) object
                // that was added to the backstack when start button was clicked
                GameScreen(
                    upper = it.upperBound,
                    gameLogic = viewModel(
                        factory = viewModelFactory {
                            initializer { GameViewModel(it.upperBound) } }
                    )
                )
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(
                targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it })
        }
    )
}
