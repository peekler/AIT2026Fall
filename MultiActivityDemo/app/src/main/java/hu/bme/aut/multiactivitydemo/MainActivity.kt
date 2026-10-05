package hu.bme.aut.multiactivitydemo

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.multiactivitydemo.MainActivity.Companion.KEY_DATA
import hu.bme.aut.multiactivitydemo.ui.theme.MultiActivityDemoTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val KEY_DATA = "KEY_DATA"
        const val MZpassword = "fsa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiActivityDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE


    Column(
        modifier = modifier
    ) {
        if (isLandscape) {
            Text("Hello LANDSCAPE ${config.uiMode}")
        } else {
            Text("Hello PORTRAIT ${config.uiMode}")
        }
        Button(
            onClick = {
                val detailsIntent = Intent()
                detailsIntent.setClass(context,
                    DetailsActivity::class.java)
                detailsIntent.putExtra(KEY_DATA,
                    "ID:2")

                context.startActivity(detailsIntent)

                // removes this MainActivity from the BackStack
                (context as MainActivity).finish()
            }
        ) {
            Text("Show Details")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiActivityDemoTheme {
        Greeting("Android")
    }
}