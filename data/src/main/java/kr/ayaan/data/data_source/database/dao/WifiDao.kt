package kr.ayaan.data.data_source.database.dao

import androidx.room.*
import kr.ayaan.data.data_source.database.entitiy.WifiEntity
import kr.ayaan.domain.wifi.model.Wifi


@Dao
interface WifiDao {

    @Query("Select * from WifiEntity")
    fun getWifiList(): List<WifiEntity>

    @Insert
    fun insertWifi(wifiEntity: WifiEntity)

    @Delete
    fun deleteWifi(wifiEntity: WifiEntity)

    @Update
    fun updateWifi(wifiEntity: WifiEntity)

}