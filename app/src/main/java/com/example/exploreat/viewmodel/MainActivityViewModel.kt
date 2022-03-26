package com.example.exploreat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exploreat.data.mapper.toDomainModel
import com.example.exploreat.data.model.DomainPlaceModel
import com.example.exploreat.data.model.Result
import com.example.exploreat.repository.MainRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*


class MainActivityViewModel constructor(private val mainRepository: MainRepository): ViewModel() {

    val placesLiveData = MutableLiveData<Result<List<DomainPlaceModel>>>()
    var job: Job? = null

    fun searchForPlaces(location: LatLng) {
        val ll = location.latitude.toString() + "," + location.longitude.toString()
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = mainRepository.searchForPlaces(ll)
            withContext(Dispatchers.IO) {
                if (result.isSuccessful) {
                    val results = result.body()?.results?.map { it.toDomainModel() }
                    placesLiveData.postValue(Result.Success(data = results))
                } else {
                    placesLiveData.postValue(Result.Error(message = result.message()))
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}