package com.john.recipefinder

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.N)
class ConnectivityViewModel(context : Context) : ViewModel() {

    val isConnected = MutableLiveData<Boolean>()

    val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    init {

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        isConnected.postValue(networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)

        connectivityManager.registerDefaultNetworkCallback(object: ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isConnected.postValue(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                isConnected.postValue(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isConnected.postValue(false)
            }
        })
    }

}

class ConnectivityViewModelFactory(var context: Context) : ViewModelProvider.Factory{
    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectivityViewModel::class.java)){
            return ConnectivityViewModel(context) as T
        }else{
            throw IllegalArgumentException("Unknown View Model")
        }
    }
}