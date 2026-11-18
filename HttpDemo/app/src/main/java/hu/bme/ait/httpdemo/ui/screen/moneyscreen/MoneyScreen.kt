package hu.bme.ait.httpdemo.ui.screen.moneyscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MoneyScreen(
    viewModel: MoneyViewModel = hiltViewModel()
) {
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Text("Money exchange rates screen")
    }
}