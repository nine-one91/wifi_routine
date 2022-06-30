package kr.ayaan.presentation.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.rtt.WifiRttManager
import android.os.Build
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@SuppressLint("ServiceCast")
class WifiManager(val applicationContext: Context) {

    companion object {
        const val TAG = "WIFIMANAGER"
    }

    lateinit var wifiManager: android.net.wifi.WifiManager
    lateinit var wifiRttManager: WifiRttManager
    lateinit var wifiScanReceiver: BroadcastReceiver
    var isRegister = false

    init {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    //    callbackFlow<ScanResult> {
//        wifiScanReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent) {
//
//                if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.action)) {
//                    val results = wifiManager.scanResults
//                    Log.d(TAG, "onReceive1: ${results}")
//                    results.forEach {
//                        launch {
//                            send(it)
//                        }
//                    }
//                }
////                if (WifiManager.EXTRA_RESULTS_UPDATED.equals(intent.action)) {
////                    val results = wifiManager.scanResults
////                    Log.d(TAG, "onReceive2: ${results}")
////                }
//
//
//            }
//        }
//        awaitClose{ applicationContext.unregisterReceiver(wifiScanReceiver) }
//    }
    suspend fun getWifiScanResult(): Flow<List<ScanResult>> = callbackFlow {
        wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {

                if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.action)) {
                    val results = wifiManager.scanResults
                    Log.d(TAG, "onReceive1: ${results}")
                    launch {
                        send(results)
                    }
                    stopScan()
                }
            }
        }
        awaitClose { applicationContext.unregisterReceiver(wifiScanReceiver) }
    }

    fun startScan() {
        Log.d("STARTSCAN", "STARTSCAN")
        isRegister = true
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        applicationContext.registerReceiver(wifiScanReceiver, intentFilter)
        wifiManager.startScan()
    }

    fun stopScan() {
        if (isRegister) {
            applicationContext.unregisterReceiver(wifiScanReceiver)
            isRegister = false
        }
    }


    fun getCurrentWifiInfo(): Map<String, String> {
        val info = wifiManager.connectionInfo
        return mapOf("ssid" to info.ssid, "key" to info.bssid)
    }

}