package com.example.exploreat.data.mapper

import com.example.exploreat.data.model.ApiPlaceResult
import com.example.exploreat.data.model.DomainPlaceModel


fun ApiPlaceResult.toDomainModel(): DomainPlaceModel {
    return DomainPlaceModel(id = this.id,
                            name = this.name ?: "",
                            lat = this.geocodes.geocodesMain.lat ?: 0.0,
                            lng = this.geocodes.geocodesMain.lng ?: 0.0,
                            distance = this.distance ?: -1,
                            address = this.location.address ?: "",
                            iconUrl = this.categories[0]?.icon?.prefix + "64" + this.categories[0]?.icon?.suffix,
                            type = this.categories[0]?.name ?: ""
    )
}

fun ApiPlaceResult.toDomainModelWithPhoto(prefix: String, suffix: String): DomainPlaceModel {
    return DomainPlaceModel(id = this.id,
        name = this.name ?: "",
        lat = this.geocodes.geocodesMain.lat ?: 0.0,
        lng = this.geocodes.geocodesMain.lng ?: 0.0,
        distance = this.distance ?: -1,
        address = this.location.address ?: "",
        iconUrl = prefix + "64" + suffix,
        type = this.categories[0]?.name ?: ""
    )
}