package com.example.gweilandandroidtask.di

import android.content.Context
import com.example.gweilandandroidtask.BuildConfig
import com.example.gweilandandroidtask.data_layer.remote_data_source.CoinMarketCapApiHelper
import com.example.gweilandandroidtask.data_layer.remote_data_source.CoinMarketCapApiHelperImpl
import com.example.gweilandandroidtask.data_layer.remote_data_source.CoinMarketCapApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                requestBuilder.addHeader("X-CMC_PRO_API_KEY", "${BuildConfig.API_KEY}")
                it.proceed(requestBuilder.build())
            }.cache(myCache).build()
    }

    @Singleton
    @Provides
    fun provideCoinMarketCapApiService(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): CoinMarketCapApiService {
        return retrofitBuilder.client(okHttpClient).baseUrl(BuildConfig.BASE_URL).build()
            .create(CoinMarketCapApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCoinMarketCapApiHelper(apiService: CoinMarketCapApiService): CoinMarketCapApiHelper =
        CoinMarketCapApiHelperImpl(apiService)
}