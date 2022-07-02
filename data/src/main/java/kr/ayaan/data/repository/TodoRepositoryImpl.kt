package kr.ayaan.data.repository

import kr.ayaan.data.data_source.database.dao.TodoDao
import kr.ayaan.data.data_source.database.entitiy.TodoEntity
import kr.ayaan.domain.todo.model.Todo
import kr.ayaan.domain.todo.repository.TodoRepository

class TodoRepositoryImpl(
    private val todoDao: TodoDao
): TodoRepository {
    override fun getTodos(wifiId: Long): List<Todo> {
        return todoDao.getTodos(wifiId).map {
            todoEntity ->  todoEntity.toModel()
        }
    }

    override fun addTodo(wifiId: Long, todo: Todo) {
        todoDao.insertTodo(TodoEntity.fromModel(wifiId, todo))
    }

    override fun removeTodo(todo: Todo) {
        todoDao.deleteTodo(TodoEntity.fromModel(todo))
    }

    override fun updateTodo(todo: Todo) {
        todoDao.updateTodo(TodoEntity.fromModel(todo))
    }
}