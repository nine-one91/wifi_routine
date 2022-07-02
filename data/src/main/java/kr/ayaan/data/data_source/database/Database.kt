package kr.ayaan.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.ayaan.data.data_source.database.dao.WifiDao
import kr.ayaan.data.data_source.database.entitiy.WifiEntity

@Database(
    entities = [WifiEntity::class, ],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val wifiDao: WifiDao

    companion object{
        const val DB_NAME = "Room_Database"
    }
}