package hu.bme.ait.todoapp.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.ait.todoapp.data.TodoDAO
import hu.bme.ait.todoapp.data.TodoItem
import hu.bme.ait.todoapp.data.TodoPriority
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    val todoDAO: TodoDAO
) : ViewModel() {

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodods()
    }

    suspend fun getAllTodoNum(): Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum(): Int {
        return todoDAO.getImportantTodosNum()
    }

    fun addTodoList(todoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.insert(todoItem)
        }
    }


    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.delete(todoItem)
        }
    }

    fun editTodoItem(editedTodo: TodoItem) {
        viewModelScope.launch {
            todoDAO.update(editedTodo)
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val newTodo = todoItem.copy()
        newTodo.isDone = value

        viewModelScope.launch { // we launch a coroutine here
            todoDAO.update(newTodo)
        }
    }

    fun clearAllTodos() {
        viewModelScope.launch {
            todoDAO.deleteAllTodos()
        }
    }

}