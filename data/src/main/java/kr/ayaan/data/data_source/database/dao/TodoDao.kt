package kr.ayaan.data.data_source.database.dao

import androidx.room.*
import kr.ayaan.data.data_source.database.entitiy.TodoEntity

@Dao
interface TodoDao {

    @Query("Select * from TodoEntity where wifi_id = :wifiId")
    fun getTodos(wifiId: Long): List<TodoEntity>

    @Insert
    fun insertTodo(todoEntity: TodoEntity)

    @Delete
    fun deleteTodo(todoEntity: TodoEntity)

    @Update
    fun updateTodo(todoEntity: TodoEntity)
}