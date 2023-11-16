package com.example.gweilandandroidtask.data_layer.remote_data_source

import com.example.gweilandandroidtask.data_layer.remote_data_source.models.LatestCoinMarketList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApiService {

    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getLatestCryptoListing(@Query("sort") sortBy: String?): Response<LatestCoinMarketList>
}