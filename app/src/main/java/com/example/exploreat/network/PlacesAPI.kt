package com.example.exploreat.network

import com.example.exploreat.data.model.ApiPlacePhoto
import com.example.exploreat.data.model.ApiPlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PlacesAPI {

    @GET("places/search")
    suspend fun searchForPlaces(@Query("ll") ll: String,
                                @Query("limit") limit: Int = 6,
                                @Query("categories") categoryId: String = "13000",
                                @Query("radius") radius: Int = 2000) : Response<ApiPlacesResponse>

    @GET("places/{fsq_id}/photos")
    suspend fun getPhotosForPlace(@Path("fsq_id") id: String) : Response<List<ApiPlacePhoto>>

}