package com.example.gweilandandroidtask.data_layer.remote_data_source.models


import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("BTC")
    val bTC: BTC?,
    @SerializedName("ETH")
    val eTH: ETH?,
    @SerializedName("USD")
    val uSD: USD?
)