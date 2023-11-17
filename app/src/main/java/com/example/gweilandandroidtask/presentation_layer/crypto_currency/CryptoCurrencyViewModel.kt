package com.example.gweilandandroidtask.presentation_layer.crypto_currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gweilandandroidtask.data_layer.remote_data_source.models.Data
import com.example.gweilandandroidtask.domain_layer.GetLatestCryptoListingUseCase
import com.example.gweilandandroidtask.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoCurrencyViewModel @Inject constructor(private val useCase: GetLatestCryptoListingUseCase) :
    ViewModel() {

    internal val cryptoResult: LiveData<NetworkResult<List<Data>>> get() = useCase.mLatestCryptoListing

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLatestCryptoList("market_cap")
        }
    }

    private suspend fun getLatestCryptoList(sorted: String?) {
        useCase(sortBy = sorted)
    }

}