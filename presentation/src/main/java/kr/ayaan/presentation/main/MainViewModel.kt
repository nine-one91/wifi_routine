package kr.ayaan.presentation.main

import android.net.wifi.ScanResult
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.ayaan.presentation.MainActivity
import kr.ayaan.presentation.MyApplication
import kr.ayaan.presentation.util.WifiManager

class MainViewModel : ViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }
    var wifiManager: WifiManager = WifiManager(applicationContext = MyApplication.instance.applicationContext)

    init {
        viewModelScope.launch {
            wifiManager.getWifiScanResult().collect{
                Log.d(TAG, "${it}")
                _wifiList.value = it
            }
        }
    }
    private val _currentWifiInfo = MutableLiveData<Map<String, String>>()
    val currentWifiInfo : LiveData<Map<String, String>>
        get() = _currentWifiInfo

    private val _wifiList = MutableLiveData<List<ScanResult>>()
    val wifiList : LiveData<List<ScanResult>>
        get() = _wifiList

    private val _showWifiList = mutableStateOf(false)
    val showWifiList: State<Boolean>
        get() = _showWifiList


//    fun startScan() {
//        wifiManager.startScan()
//    }
//
//    fun stopScan() {
//        wifiManager.stopScan()
//    }

    fun getCurrentInfo(){
        _currentWifiInfo.value = wifiManager.getCurrentWifiInfo()
    }

    fun changeShowWifiList(){
        _showWifiList.value = _showWifiList.value.not()
        if(_showWifiList.value) {
            wifiManager.startScan()
        }
    }
    fun selectWifi(result: ScanResult) {
        _currentWifiInfo.value = mapOf("ssid" to result.SSID, "key" to result.BSSID)
        _showWifiList.value = false
    }
}