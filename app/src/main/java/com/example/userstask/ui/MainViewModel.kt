package com.example.userstask.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.userstask.data.UsersApiRepository
import com.example.userstask.data.UsersDbRepository
import com.example.userstask.data.db.UserDatabase
import com.example.userstask.data.entity.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val usersApiRepository = UsersApiRepository()
    private val usersDbRepository = UsersDbRepository()
    private val userDatabase = UserDatabase(context)
    private val _apiUsersData: MutableLiveData<List<UserData>> = MutableLiveData()
    private val _loadingInProgress = MutableLiveData<Boolean>()
    private var _pageTpe = PageType.LOAD_FROM_API
    val apiUsersData: LiveData<List<UserData>> get() = _apiUsersData
    val loadingInProgress: LiveData<Boolean> get() = _loadingInProgress
    val pageType: PageType get() = _pageTpe

    var userData: UserData? = null

    fun loadUsersData(pageType: PageType) {
        _loadingInProgress.postValue(true)
        _pageTpe = pageType
        when (pageType) {
            PageType.LOAD_FROM_API -> loadFromApi()
            PageType.LOAD_FROM_DB -> loadFromDb()
        }
    }

    fun saveUserData(userData: UserData?) {
        userData?.let {
            viewModelScope.launch(Dispatchers.IO) {
                userDatabase.userDataDao().insert(it)
            }
        }
    }

    fun removeUser(userData: UserData?) {
        userData?.let {
            viewModelScope.launch(Dispatchers.IO) {
                userDatabase.userDataDao().delete(it)
                loadFromDb()
            }
        }
    }

    private fun loadFromApi() {
        if (!isOnline()) {
            _loadingInProgress.postValue(false)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val response = usersApiRepository.getDataAsync().await()
            _apiUsersData.postValue(response.userData)
            _loadingInProgress.postValue(false)
        }
    }

    private fun loadFromDb() {
        val userDataDao = UserDatabase(context).userDataDao()
        viewModelScope.launch(Dispatchers.IO) {
            val userData = usersDbRepository.getData(userDataDao)
            _apiUsersData.postValue(userData)
            _loadingInProgress.postValue(false)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}