package com.wahyuapp.cachingapi.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wahyuapp.cachingapi.database.SampleDatabase
import com.wahyuapp.cachingapi.database.User
import com.wahyuapp.cachingapi.database.UserDAO
import com.wahyuapp.cachingapi.database.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    //add repository
    private val repository: UserRepository
    private val userDAO: UserDAO = SampleDatabase.getInstance(application).userDAO()

    private var _items: LiveData<List<User>>

    val items: LiveData<List<User>>
        get() = _items

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response


    private var vmJob = Job()
    private val crScope = CoroutineScope(vmJob + Dispatchers.Main)

    init {
        repository = UserRepository(userDAO)
        _response.value = "Loading ....."

        crScope.launch {
            try {
                repository.refreshUsers()
            } catch (t: Throwable) {
                _response.value = "Failed, Internet OFF"
            }
        }

        _items = repository.users
        _response.value = "OK"
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }

}