package kr.ayaan.data.data_source.database.entitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class WifiTodos(
    @Embedded
    val wifi: WifiEntity,

    @Relation(
        parentColumn = "wifi_id",
        entityColumn = "wifi_id"
    )
    val todoList: List<TodoEntity>
)
