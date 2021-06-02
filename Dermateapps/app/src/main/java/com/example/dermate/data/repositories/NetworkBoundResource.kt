package com.example.dermate.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dermate.data.source.remote.ApiResponse
import com.example.dermate.data.source.remote.StatusResponse
import com.example.dermate.utils.AppExecutor

@Suppress("LeakingThis")
abstract class NetworkBoundResource<ResultType, RequestType>(private val appExecutor: AppExecutor) {
    private val resultSet = MediatorLiveData<Resource<ResultType>>()

    init {
        resultSet.value = Resource.Loading(null)
        val dbSource = loadFromDB()
        resultSet.addSource(dbSource) { data ->
            resultSet.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                resultSet.addSource(dbSource) {
                    resultSet.value = Resource.Success(it)
                }
            }
        }
    }

    private fun onFetchFailed() {}
    protected abstract fun loadFromDB(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?
    protected abstract fun saveCallResult(data: RequestType)
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall() as LiveData<ApiResponse<RequestType>>
        resultSet.addSource(dbSource) { newData -> resultSet.value = Resource.Loading(newData) }
        resultSet.addSource(apiResponse) { response ->
            resultSet.removeSource(apiResponse)
            resultSet.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS -> appExecutor.diskIO().execute {
                    saveCallResult(response.body)
                    appExecutor.mainThread().execute {
                        resultSet.addSource(loadFromDB()) {
                            resultSet.value = Resource.Success(it)
                        }
                    }
                }
                StatusResponse.EMPTY -> appExecutor.mainThread().execute {
                    resultSet.addSource(loadFromDB()) {
                        resultSet.value = Resource.Success(it)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    resultSet.addSource(dbSource) {
                        resultSet.value = Resource.Error(response.message.toString(), it)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return resultSet
    }
}