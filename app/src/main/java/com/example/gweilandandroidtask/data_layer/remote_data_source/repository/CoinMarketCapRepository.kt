package com.example.gweilandandroidtask.data_layer.remote_data_source.repository

import com.example.gweilandandroidtask.data_layer.remote_data_source.CoinMarketCapApiHelper
import com.example.gweilandandroidtask.data_layer.remote_data_source.models.LatestCoinMarketList
import retrofit2.Response
import javax.inject.Inject

class CoinMarketCapRepository @Inject constructor(private val coinMarketCapHelper: CoinMarketCapApiHelper) {
    suspend fun getLatestCryptoListing(sortBy: String?): Response<LatestCoinMarketList> =
        coinMarketCapHelper.getLatestCryptoListing(sortBy)
}