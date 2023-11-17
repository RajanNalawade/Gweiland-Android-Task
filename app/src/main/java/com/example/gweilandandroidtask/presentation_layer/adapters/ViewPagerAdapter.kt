package com.example.gweilandandroidtask.presentation_layer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gweilandandroidtask.presentation_layer.crypto_currency.CryptoCurrencyFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return CryptoCurrencyFragment()
    }
}