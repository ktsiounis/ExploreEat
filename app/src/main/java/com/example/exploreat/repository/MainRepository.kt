package com.example.exploreat.repository

import com.example.exploreat.network.PlacesAPI


class MainRepository constructor(private val service: PlacesAPI) {

    suspend fun searchForPlaces(ll: String) = service.searchForPlaces(ll = ll)

}