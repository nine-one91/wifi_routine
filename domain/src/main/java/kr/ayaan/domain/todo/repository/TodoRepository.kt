package kr.ayaan.domain.todo.repository

import kr.ayaan.domain.todo.model.Todo

interface TodoRepository {

    fun getTodos(wifiId: Long): List<Todo>

    fun addTodo(wifiId: Long, todo: Todo)

    fun removeTodo(todo: Todo)

    fun updateTodo(todo: Todo)
}