package hu.bme.ait.todoapp.data

import hu.bme.ait.todoapp.R

data class TodoItem(
    val id: String,
    val title:String,
    val description:String,
    val createDate:String,
    var priority:TodoPriority,
    var isDone: Boolean
)

enum class TodoPriority {
    NORMAL, HIGH;

    fun getIcon(): Int {
        return if (this==NORMAL) R.drawable.normal else R.drawable.important
    }
}

