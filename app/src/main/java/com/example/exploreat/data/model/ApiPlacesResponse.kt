package com.example.exploreat.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiPlacesResponse (@SerializedName("results") val results: List<ApiPlaceResult>?) : Serializable

data class ApiPlaceResult(@SerializedName("fsq_id") val id: String,
                          @SerializedName("name") val name: String?,
                          @SerializedName("categories") val categories: List<ApiPlaceCategory?>,
                          @SerializedName("location") val location: ApiPlaceLocation,
                          @SerializedName("geocodes") val geocodes: ApiPlaceGeocodes,
                          @SerializedName("distance") val distance: Int?) : Serializable

data class ApiPlaceGeocodes (@SerializedName("main") val geocodesMain: ApiGeocodesMain)

data class ApiPlaceLocation (@SerializedName("address") val address: String?,
                             @SerializedName("cross_street") val crossStreet: String?,
                             @SerializedName("postalCode") val postalCode: String?,
                             @SerializedName("country") val country: String?,
                             @SerializedName("formatted_address") val formattedAddress: String?) : Serializable

data class ApiGeocodesMain (@SerializedName("latitude") val lat: Double?,
                            @SerializedName("longitude") val lng: Double?) : Serializable

data class ApiPlaceCategory (@SerializedName("id") val id: String?,
                             @SerializedName("name") val name: String?,
                             @SerializedName("icon") val icon: ApiPlaceCategoryIcon?) : Serializable

data class ApiPlaceCategoryIcon (@SerializedName("prefix") val prefix: String?,
                                 @SerializedName("suffix") val suffix: String?) : Serializable
