package com.example.gweilandandroidtask.data_layer.remote_data_source.models


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("credit_count")
    val creditCount: Int?,
    @SerializedName("elapsed")
    val elapsed: Int?,
    @SerializedName("error_code")
    val errorCode: Int?,
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("timestamp")
    val timestamp: String?
)