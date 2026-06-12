package com.example.gweilandandroidtask.presentation_layer

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gweilandandroidtask.R
import com.example.gweilandandroidtask.databinding.ActivityMain2Binding
import com.example.gweilandandroidtask.presentation_layer.crypto_currency.CryptoCurrencyActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private var myBackground: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main2)

        Log.d(CryptoCurrencyActivity.TAG, "onCreate: Activity-2")

        binding.txtDemo.text = getString(R.string.str_memory_leaks_are_not_good_to_applications)
        if (myBackground == null) {
            myBackground = getDrawable(R.drawable.ic_launcher_background)
        }
        binding.txtDemo.setBackgroundDrawable(myBackground)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(CryptoCurrencyActivity.TAG, "onRestart: Activity-2")
    }

    override fun onStart() {
        super.onStart()
        Log.d(CryptoCurrencyActivity.TAG, "onStart: Activity-2")
    }

    override fun onResume() {
        super.onResume()
        Log.d(CryptoCurrencyActivity.TAG, "onResume: Activity-2")
    }

    override fun onPause() {
        Log.d(CryptoCurrencyActivity.TAG, "onPause: Activity-2")
        super.onPause()
    }

    override fun onStop() {
        Log.d(CryptoCurrencyActivity.TAG, "onStop: Activity-2")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(CryptoCurrencyActivity.TAG, "onDestroy: Activity-2")
        super.onDestroy()
    }
}