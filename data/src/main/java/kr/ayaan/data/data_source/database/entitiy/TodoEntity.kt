package kr.ayaan.data.data_source.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kr.ayaan.domain.todo.model.Todo

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = WifiEntity::class,
            parentColumns = arrayOf("wifi_id"),
            childColumns = arrayOf("wifi_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    val todoId: Long? = null,
    @ColumnInfo(name = "wifi_id")
    val wifiId: Long? = null,
    var content: String,
) {
    companion object {
        fun fromModel(wifiId: Long, todo: Todo) = TodoEntity(
            wifiId = wifiId,
            content = todo.content
        )
        fun fromModel(todo: Todo) = TodoEntity(
            todoId = todo.todoId,
            content = todo.content
        )
    }
    fun toModel() = Todo(todoId = this.todoId ?: 0, content = this.content)
}
