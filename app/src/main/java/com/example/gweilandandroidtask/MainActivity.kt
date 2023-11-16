package com.example.gweilandandroidtask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gweilandandroidtask.data_layer.remote_data_source.repository.CoinMarketCapRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var repository: CoinMarketCapRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {

            val output = async(Dispatchers.IO) { repository.getLatestCryptoListing("market_cap") }
            val result = output.await()
            if (result.isSuccessful) {
                val data = result.body()
                data?.data?.forEach {
                    Log.d("Test", "onCreate: ${it?.name}")
                }
            }
        }

    }
}