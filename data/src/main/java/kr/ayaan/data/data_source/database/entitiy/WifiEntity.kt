package kr.ayaan.data.data_source.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kr.ayaan.domain.wifi.model.Wifi

@Entity
data class WifiEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wifi_id")
    val wifiId: Long? = null,
    val ssid: String,
    val bssid: String,
) {
    companion object {
        fun fromModel(wifi: Wifi): WifiEntity {
            return WifiEntity(
                ssid = wifi.ssid,
                bssid = wifi.bssid
            )
        }
    }

    fun toModel() = Wifi(wifiId = this.wifiId ?: 0, ssid = this.ssid, bssid = bssid)

}
