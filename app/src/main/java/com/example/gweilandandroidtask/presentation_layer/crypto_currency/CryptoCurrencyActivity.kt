package com.example.gweilandandroidtask.presentation_layer.crypto_currency

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gweilandandroidtask.R
import com.example.gweilandandroidtask.databinding.ActivityMainBinding
import com.example.gweilandandroidtask.databinding.FilterBottomSheetBinding
import com.example.gweilandandroidtask.presentation_layer.adapters.CryptoCurrencyAdapter
import com.example.gweilandandroidtask.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import showToast


@AndroidEntryPoint
class CryptoCurrencyActivity : AppCompatActivity() {


    private var binding: ActivityMainBinding? = null

    private val viewModel by viewModels<CryptoCurrencyViewModel>()

    private lateinit var cryptoCurrencyAdapter: CryptoCurrencyAdapter

    private val onBAckPressedCallBack: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.rvCryptoCurrency?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CryptoCurrencyActivity)
            //Restore RecyclerView scroll position
            adapter?.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        initialiseListeners()
        observeCryptoData()

        onBackPressedDispatcher.addCallback(onBAckPressedCallBack)
    }

    private fun initialiseListeners() {
        binding?.searchViewImages?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    val trimmedQuery = newText.trim { it <= ' ' }
                    val finalQuery = trimmedQuery.replace(" ", "-")
                    cryptoCurrencyAdapter.filter.filter(finalQuery)
                }
                return true
            }

        })

        binding?.btnFilter?.setOnClickListener {

            val filterSheet = layoutInflater.inflate(R.layout.filter_bottom_sheet, null)
            val filterBinding = FilterBottomSheetBinding.bind(filterSheet)
            val dialog = BottomSheetDialog(this@CryptoCurrencyActivity)
            dialog.setContentView(filterBinding.root)

            filterBinding.btnPrice.setOnClickListener {
                viewModel.filterData?.value = "price"
                dialog.dismiss()
            }

            filterBinding.btnMarketCap.setOnClickListener {
                viewModel.filterData?.value = "market_cap"
                dialog.dismiss()
            }

            filterBinding.btnVolume24h.setOnClickListener {
                viewModel.filterData?.value = "volume_24h"
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun observeCryptoData() {

        viewModel.cryptoResult.observe(this@CryptoCurrencyActivity, Observer {
            binding?.circularProgress?.visibility = View.GONE

            when (it) {
                is NetworkResult.Success -> {
                    it.data?.apply {
                        cryptoCurrencyAdapter = CryptoCurrencyAdapter(this)
                        binding?.rvCryptoCurrency?.adapter = cryptoCurrencyAdapter
                        cryptoCurrencyAdapter.submitList(this)
                    }
                }

                is NetworkResult.Error -> {
                    it.errorMessage?.showToast(this@CryptoCurrencyActivity)
                }

                is NetworkResult.Loading -> {
                    binding?.circularProgress?.visibility = View.VISIBLE
                }
            }
        })

        viewModel.filterData?.observe(this@CryptoCurrencyActivity, Observer {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getLatestCryptoList(it)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}