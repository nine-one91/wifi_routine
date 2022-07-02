package kr.ayaan.data.repository

import kr.ayaan.data.data_source.database.dao.WifiDao
import kr.ayaan.data.data_source.database.entitiy.WifiEntity
import kr.ayaan.domain.wifi.model.Wifi
import kr.ayaan.domain.wifi.repository.WifiRepository

class WifiRepositoryImpl(
    private val wifiDao: WifiDao
) : WifiRepository {
    override fun getWifiList(): List<Wifi> {
        return wifiDao.getWifiList().map { wifiEntity -> wifiEntity.toModel() }
    }

    override fun addWifi(wifi: Wifi) {
        wifiDao.insertWifi(wifiEntity = WifiEntity.fromModel(wifi))
    }

    override fun removeWifi(wifi: Wifi) {
        wifiDao.deleteWifi(wifiEntity = WifiEntity.fromModel(wifi))
    }

    override fun updateWifi(wifi: Wifi) {
        wifiDao.updateWifi(wifiEntity = WifiEntity.fromModel(wifi))
    }
}