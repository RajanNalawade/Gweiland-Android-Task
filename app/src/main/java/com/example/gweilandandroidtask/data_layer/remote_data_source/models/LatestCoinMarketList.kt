package com.example.gweilandandroidtask.data_layer.remote_data_source.models


import com.google.gson.annotations.SerializedName

data class LatestCoinMarketList(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("status")
    val status: Status?
)