package com.example.exploreat.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiPlacePhoto(@SerializedName("id") val id: String,
                         @SerializedName("prefix") val prefix: String?,
                         @SerializedName("suffix") val suffix: String?) : Serializable
