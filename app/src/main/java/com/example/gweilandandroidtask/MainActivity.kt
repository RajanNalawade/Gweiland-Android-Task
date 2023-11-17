package com.example.gweilandandroidtask

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.gweilandandroidtask.data_layer.remote_data_source.repository.CoinMarketCapRepository
import com.example.gweilandandroidtask.databinding.ActivityMainBinding
import com.example.gweilandandroidtask.presentation_layer.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : FragmentActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var repository: CoinMarketCapRepository

    private val onBAckPressedCallBack: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.viewPager.currentItem == 0) {
                    //onBackPressedDispatcher.onBackPressed()
                    finish()
                } else {
                    binding.viewPager.currentItem = binding.viewPager.currentItem - 1
                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = viewPagerAdapter

        onBackPressedDispatcher.addCallback(onBAckPressedCallBack)

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