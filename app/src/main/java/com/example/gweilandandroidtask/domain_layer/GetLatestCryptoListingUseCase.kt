package com.example.gweilandandroidtask.domain_layer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gweilandandroidtask.data_layer.remote_data_source.models.Data
import com.example.gweilandandroidtask.data_layer.remote_data_source.repository.CoinMarketCapRepository
import com.example.gweilandandroidtask.utils.NetworkResult
import javax.inject.Inject

class GetLatestCryptoListingUseCase @Inject constructor(private val repository: CoinMarketCapRepository) {

    private val latestCryptoListing = MutableLiveData<NetworkResult<List<Data>>>()
    val mLatestCryptoListing: LiveData<NetworkResult<List<Data>>> =
        latestCryptoListing

    suspend operator fun invoke(sortBy: String?) {

        try {
            latestCryptoListing.postValue(NetworkResult.Loading())

            val result = repository.getLatestCryptoListing(sortBy = sortBy)
            if (result.isSuccessful) {
                var top20 = result.body()?.data?.subList(0, 20)!!
                latestCryptoListing.postValue(NetworkResult.Success(top20 as? List<Data>))
            } else if (result.errorBody() != null) {
                latestCryptoListing.postValue(NetworkResult.Error(errorMessage = result.message()))
            } else {
                latestCryptoListing.postValue(NetworkResult.Error(errorMessage = "Something went wrong.."))
            }

        } catch (e: Exception) {
            latestCryptoListing.postValue(NetworkResult.Error(errorMessage = e.message))
        }

    }

}