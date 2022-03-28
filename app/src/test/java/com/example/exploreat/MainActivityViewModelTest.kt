package com.example.exploreat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.exploreat.data.model.ApiPlacesResponse
import com.example.exploreat.data.model.DomainPlaceModel
import com.example.exploreat.data.model.Result
import com.example.exploreat.network.PlacesAPI
import com.example.exploreat.repository.MainRepository
import com.example.exploreat.viewmodel.MainActivityViewModel
import com.google.android.gms.maps.model.LatLng
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response


@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    private val location = LatLng(37.9786816,23.7280806)
    private val locationString = "37.9786816,23.7280806"

    // Subject under test
    private lateinit var viewModel: MainActivityViewModel

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var observer: Observer<Result<List<DomainPlaceModel>>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainActivityViewModel(repository)
    }

    @After
    fun tearDown() {
        reset(repository, observer)
    }

    @Test
    fun `Verify when calling search then return loading`() = runBlockingTest {
        viewModel.placesLiveData.observeForever(observer)
        viewModel.searchForPlaces(location)
        assertThat(viewModel.placesLiveData.getOrAwaitValue(), instanceOf(Result.Loading::class.java))
        viewModel.placesLiveData.removeObserver(observer)
    }

    @Test
    fun `Assert when calling search returns success then return success`() = mainCoroutineRule.runBlockingTest {

        `when`(repository.searchForPlaces(locationString))
            .thenReturn(Response.success(ApiPlacesResponse(listOf())))

        viewModel.searchForPlaces(location)
        viewModel.placesLiveData.observeForever(observer)
        verify(repository).searchForPlaces(locationString)
        assertThat(viewModel.placesLiveData.getOrAwaitValue(), instanceOf(Result.Success::class.java))
        viewModel.placesLiveData.removeObserver(observer)
    }

}