package hu.bme.ait.httpdemo.network

import hu.bme.ait.httpdemo.data.MoneyResult
import retrofit2.http.GET
import retrofit2.http.Query

// http://data.fixer.io/api/latest?access_key=969c37b5a73f8cb2d12c081dcbdc35e6

//Host: http://data.fixer.io
//Path: api/latest
//Query params: access_key=969c37b5a73f8cb2d12c081dcbdc35e6

// this is like the DAO in case of Room...
interface MoneyAPI {

    @GET("api/latest")
    suspend fun getRates(
        @Query("access_key") accessKey: String): MoneyResult
}