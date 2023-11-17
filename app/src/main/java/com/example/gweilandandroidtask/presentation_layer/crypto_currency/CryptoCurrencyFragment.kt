package com.example.gweilandandroidtask.presentation_layer.crypto_currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gweilandandroidtask.R
import com.example.gweilandandroidtask.databinding.FragmentCryptoCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoCurrencyFragment : Fragment() {

    private val viewModel: CryptoCurrencyViewModel by viewModels<CryptoCurrencyViewModel>()

    private lateinit var binding: FragmentCryptoCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_crypto_currency,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}