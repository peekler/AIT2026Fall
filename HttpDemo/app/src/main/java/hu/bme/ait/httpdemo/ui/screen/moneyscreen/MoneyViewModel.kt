package hu.bme.ait.httpdemo.ui.screen.moneyscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.ait.httpdemo.data.MoneyResult
import hu.bme.ait.httpdemo.network.MoneyAPI
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface MoneyUiState {
    object Init : MoneyUiState
    object Loading : MoneyUiState
    data class Success(val moneyRates: MoneyResult) : MoneyUiState
    object Error : MoneyUiState
}


@HiltViewModel
class MoneyViewModel @Inject constructor(
    val moneyAPI: MoneyAPI
) : ViewModel() {

    var moneyUiState: MoneyUiState by mutableStateOf(
        MoneyUiState.Init)

    fun getRates() {
        moneyUiState = MoneyUiState.Loading

        viewModelScope.launch {
            try {
                val result: MoneyResult = moneyAPI.getRates(
                    "969c37b5a73f8cb2d12c081dcbdc35e6")

                moneyUiState = MoneyUiState.Success(result)
            } catch (e: Exception) {
                moneyUiState = MoneyUiState.Error
                e.printStackTrace()
            }
        }


    }

}