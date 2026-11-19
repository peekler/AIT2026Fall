package hu.bme.ait.httpdemo.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoneyResult(
    @SerialName("base")
    var base: String?,
    @SerialName("date")
    var date: String?,
    @SerialName("rates")
    var rates: Rates?,
    @SerialName("success")
    var success: Boolean?,
    @SerialName("timestamp")
    var timestamp: Int?
)