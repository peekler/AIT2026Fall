package hu.bme.ait.todoapp

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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.ait.todoapp.ui.navigation.SummaryScreenRoute
import hu.bme.ait.todoapp.ui.navigation.TodoScreenRoute
import hu.bme.ait.todoapp.ui.screen.SummaryScreen
import hu.bme.ait.todoapp.ui.screen.TodoScreen
import hu.bme.ait.todoapp.ui.theme.TodoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                NavGraph(
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(TodoScreenRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<TodoScreenRoute> {
                TodoScreen(
                    onSummaryClick = {
                        allTodo, importantTodo ->
                            backStack.add(SummaryScreenRoute(
                                allTodo, importantTodo))
                    }
                )
            }

            entry<SummaryScreenRoute> {
                SummaryScreen(
                    it.allTodoNum,
                    it.importantTodoNum
                )
            }
        }
    )
}
