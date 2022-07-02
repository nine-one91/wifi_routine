package kr.ayaan.domain.wifi.repository

import kr.ayaan.domain.wifi.model.Wifi

interface WifiRepository {

    fun getWifiList(): List<Wifi>

    fun addWifi(wifi: Wifi)

    fun removeWifi(wifi: Wifi)

    fun updateWifi(wifi: Wifi)

}