package kr.ayaan.domain.todo.model

data class Todo(
    val todoId: Long?,
    var content: String,
    var isComplete: Boolean = false
) {
    fun onChangeComplete(state: Boolean) {
        this.isComplete = state
    }
}
