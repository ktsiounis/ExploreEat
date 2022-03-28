package com.example.exploreat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exploreat.data.mapper.toDomainModel
import com.example.exploreat.data.mapper.toDomainModelWithPhoto
import com.example.exploreat.data.model.DomainPlaceModel
import com.example.exploreat.data.model.Result
import com.example.exploreat.repository.MainRepository
import com.example.exploreat.repository.MainRepositoryImpl
import com.example.exploreat.view.ViewMode
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*


class MainActivityViewModel constructor(private val mainRepository: MainRepository): ViewModel() {

    val placesLiveData = MutableLiveData<Result<List<DomainPlaceModel>>>()
    val viewModeLiveData = MutableLiveData<ViewMode<Any>>()
    var job: Job? = null

    fun searchForPlaces(location: LatLng) {
        val ll = location.latitude.toString() + "," + location.longitude.toString()
        job = CoroutineScope(Dispatchers.IO).launch {
            placesLiveData.postValue(Result.Loading())
            val placesSearchResult = mainRepository.searchForPlaces(ll)
                if (placesSearchResult.isSuccessful) {
                    val places = placesSearchResult
                        .body()
                        ?.results
                        ?.map {
                            val photosResult = mainRepository.getPhotosForPlace(it.id)
                            if (photosResult.isSuccessful) {
                                it.toDomainModelWithPhoto(
                                    prefix = photosResult.body()?.get(0)?.prefix ?: "",
                                    suffix = photosResult.body()?.get(0)?.suffix ?: ""
                                )
                            } else {
                                it.toDomainModel()
                            }
                        }
                    placesLiveData.postValue(Result.Success(data = places))
                } else {
                    placesLiveData.postValue(Result.Error(message = placesSearchResult.message()))
                }
        }
    }

    fun setViewMode(viewMode: ViewMode<Any>) {
        viewModeLiveData.postValue(viewMode)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}