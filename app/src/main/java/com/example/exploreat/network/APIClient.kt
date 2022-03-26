package com.example.exploreat.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {

    companion object {

        private const val BASE_URL = "https://api.foursquare.com/v3/"
        private var retrofit: PlacesAPI? = null



        fun getClientInstance(): PlacesAPI {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Accept", "application/json")
                        builder.header("Authorization", "fsq37B3HdEavt7muSCKrPXkM36lJqjhp4KlZgigPvcUiupY=")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
                .addInterceptor(loggingInterceptor)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PlacesAPI::class.java)
            }
            return retrofit!!
        }

    }

}