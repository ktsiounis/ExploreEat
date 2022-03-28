package com.example.exploreat

import com.example.exploreat.data.mapper.toDomainModel
import com.example.exploreat.data.mapper.toDomainModelWithPhoto
import com.example.exploreat.data.model.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class ApiPlaceMapperTest {

    private val apiPlaceCategoryIcon = ApiPlaceCategoryIcon("https://example.com/image_", ".png")
    private val apiPlaceCategory = ApiPlaceCategory(
        id = "212121",
        name = "Cafe",
        icon = apiPlaceCategoryIcon
    )
    private val apiPlaceLocation = ApiPlaceLocation(
        address = "karaiskaki 22",
        crossStreet = null,
        postalCode = "12100",
        country = "Greece",
        formattedAddress = "karaiskaki 22, Greece, 12100"
    )
    private val apiGeocodesMain = ApiGeocodesMain(21.2222, 21.2222)
    private val apiPlaceGeocodes = ApiPlaceGeocodes(apiGeocodesMain)

    @Test
    fun `ApiPlaceResult toDomainModel(), returns expected DomainPlaceModel`() {

        val given = ApiPlaceResult(
            id = "1234",
            name = "CoffeCoffe",
            categories = listOf(apiPlaceCategory),
            location = apiPlaceLocation,
            geocodes = apiPlaceGeocodes,
            distance = 200
        )
        val givenWithNulls = ApiPlaceResult(
            id = "1234",
            name = null,
            categories = listOf(null),
            location = apiPlaceLocation,
            geocodes = apiPlaceGeocodes,
            distance = null
        )

        val expected = DomainPlaceModel(
            id = "1234",
            name = "CoffeCoffe",
            lat = 21.2222,
            lng = 21.2222,
            distance = 200,
            address = "karaiskaki 22",
            iconUrl = "https://example.com/image_64.png",
            type = "Cafe"
        )
        val expectedWithNulls = DomainPlaceModel(
            id = "1234",
            name = "",
            lat = 21.2222,
            lng = 21.2222,
            distance = -1,
            address = "karaiskaki 22",
            iconUrl = "null64null",
            type = ""
        )

        val result = given.toDomainModel()
        val resultWithNulls = givenWithNulls.toDomainModel()

        assertThat(result, equalTo(expected))
        assertThat(resultWithNulls, equalTo(expectedWithNulls))

    }

    @Test
    fun `ApiPlaceResult toDomainModelWithPhoto(), returns expected DomainPlaceModel`() {

        val given = ApiPlaceResult(
            id = "1234",
            name = "CoffeCoffe",
            categories = listOf(apiPlaceCategory),
            location = apiPlaceLocation,
            geocodes = apiPlaceGeocodes,
            distance = 200
        )

        val expected = DomainPlaceModel(
            id = "1234",
            name = "CoffeCoffe",
            lat = 21.2222,
            lng = 21.2222,
            distance = 200,
            address = "karaiskaki 22",
            iconUrl = "https://example.com/image_2_64.png",
            type = "Cafe"
        )

        val result = given.toDomainModelWithPhoto("https://example.com/image_2_", ".png")

        assertThat(result, equalTo(expected))

    }

}