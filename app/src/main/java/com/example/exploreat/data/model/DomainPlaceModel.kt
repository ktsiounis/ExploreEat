package com.example.exploreat.data.model

import java.math.BigDecimal

data class DomainPlaceModel(val id: String,
                            val name: String,
                            val lat: Double,
                            val lng: Double,
                            val distance: Int,
                            val address: String,
                            val iconUrl: String,
                            val type: String)
