package com.example.gweilandandroidtask.data_layer.remote_data_source

import com.example.gweilandandroidtask.data_layer.remote_data_source.models.LatestCoinMarketList
import retrofit2.Response

interface CoinMarketCapApiHelper {
    suspend fun getLatestCryptoListing(sortBy: String?): Response<LatestCoinMarketList>
}