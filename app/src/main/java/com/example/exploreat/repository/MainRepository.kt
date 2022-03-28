package com.example.exploreat.repository

import com.example.exploreat.data.model.ApiPlacePhoto
import com.example.exploreat.data.model.ApiPlacesResponse
import retrofit2.Response


interface MainRepository {
    suspend fun searchForPlaces(ll: String): Response<ApiPlacesResponse>
    suspend fun getPhotosForPlace(id: String): Response<List<ApiPlacePhoto>>
}