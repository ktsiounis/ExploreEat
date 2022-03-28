package com.example.exploreat.repository

import com.example.exploreat.network.PlacesAPI


class MainRepositoryImpl constructor(private val service: PlacesAPI): MainRepository {

    override suspend fun searchForPlaces(ll: String) = service.searchForPlaces(ll = ll)
    override suspend fun getPhotosForPlace(id: String) = service.getPhotosForPlace(id = id)

}