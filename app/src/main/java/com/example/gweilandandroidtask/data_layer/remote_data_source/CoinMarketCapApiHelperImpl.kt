package com.example.gweilandandroidtask.data_layer.remote_data_source

import com.example.gweilandandroidtask.data_layer.remote_data_source.models.LatestCoinMarketList
import retrofit2.Response

class CoinMarketCapApiHelperImpl(private val serviceApi: CoinMarketCapApiService) :
    CoinMarketCapApiHelper {
    override suspend fun getLatestCryptoListing(sortBy: String?): Response<LatestCoinMarketList> =
        serviceApi.getLatestCryptoListing(sortBy)
}