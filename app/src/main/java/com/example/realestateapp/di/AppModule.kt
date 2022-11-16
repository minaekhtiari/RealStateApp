package com.example.realestateapp.di

import com.example.realestateapp.BuildConfig
import com.example.realestateapp.cons.Cons.BASE_URL
import com.example.realestateapp.network.RetroServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }


    @Singleton
    @Provides
    fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceApi {
        return retrofit.create(RetroServiceApi::class.java)
    }

    @Singleton
    @Provides
    fun getRetroInstance(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
//
//    @Singleton
//    @Provides
//    fun providesRepository(apiService: RetroServiceApi) =HouseRepository (apiService)


}