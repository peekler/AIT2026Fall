package hu.ait.aitforum

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import hu.ait.aitforum.ui.navigation.LoginScreenRoute
import hu.ait.aitforum.ui.navigation.MessagesScreenRoute
import hu.ait.aitforum.ui.navigation.WriteMessageScreenRoute
import hu.ait.aitforum.ui.screen.login.LoginScreen
import hu.ait.aitforum.ui.screen.messages.MessagesScreen
import hu.ait.aitforum.ui.screen.newmessage.WriteMessageScreen
import hu.ait.aitforum.ui.theme.AITForumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AITForumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainNav(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainNav(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(LoginScreenRoute)

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
            entry<LoginScreenRoute> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.add(MessagesScreenRoute)
                    }
                )
            }
            entry<MessagesScreenRoute> {
                MessagesScreen(
                    onNewMessage = {
                        backStack.add(WriteMessageScreenRoute)
                    }
                )
            }
            entry<WriteMessageScreenRoute> {
                WriteMessageScreen()
            }
        }
    )
}
