package kr.ayaan.domain.todo.model

data class Todo(
    val index: Long,
    var content: String,
    var isComplete: Boolean
) {
    fun onChangeComplete(state: Boolean) {
        this.isComplete = state
    }
}
