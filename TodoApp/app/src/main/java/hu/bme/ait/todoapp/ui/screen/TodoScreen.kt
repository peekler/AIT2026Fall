package hu.bme.ait.todoapp.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import hu.bme.ait.todoapp.data.TodoItem
import hu.bme.ait.todoapp.data.TodoPriority
import java.util.Date

@Composable
fun TodoScreen(viewModel: TodoViewModel = hiltViewModel()) {
    var showTodoDialog by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    var todoList = viewModel.getAllToDoList().collectAsState(
        emptyList()
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Button(onClick = {
                showTodoDialog = true
            }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            if (todoList.value.isEmpty()) {
                Text("No data.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(todoList.value) {
                        TodoCard(
                            it,
                            onTodoDelete = { todoItem -> viewModel.removeTodoItem(todoItem) },
                            onTodoChecked = { todoItem ->
                                viewModel.changeTodoState(
                                    todoItem, !todoItem.isDone
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (showTodoDialog) {
        TodoDialog(
            onTodoCreate = { newTodoItem ->
                viewModel.addTodoList(newTodoItem)
                showTodoDialog = false },
            onCancel = {showTodoDialog = false},
            todoToEdit = null
        )
    }
}


@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoDelete: (TodoItem) -> Unit,
    onTodoChecked: (TodoItem) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = todoItem.isDone,
                    onCheckedChange = {
                        onTodoChecked(todoItem)
                    }
                )
                Image(
                    painter = painterResource(id = todoItem.priority.getIcon()),
                    contentDescription = "Priority",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )
                Text(todoItem.title, modifier = Modifier.fillMaxWidth(0.2f))

                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f))


                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onTodoDelete(todoItem)
                    },
                    tint = Color.Red
                )

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        //
                    },
                    tint = Color.Gray
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        }
                    )
                }


            }

            if (expanded) {
                Text(todoItem.description)
                Text(todoItem.createDate)
            }
        }
    }
}


@Composable
fun TodoDialog(
    onTodoCreate: (TodoItem) -> Unit,
    todoToEdit: TodoItem? = null,
    onCancel: () -> Unit
) {
    var todoTitle by remember { mutableStateOf(
        todoToEdit?.title ?: ""
    ) }
    var todoDesc by remember { mutableStateOf(
        todoToEdit?.description ?: ""
    ) }
    var important by remember { mutableStateOf(
        if (todoToEdit != null) {
            todoToEdit.priority == TodoPriority.HIGH
        } else {
            false
        }
    ) }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(if (todoToEdit == null) "New Todo" else "Edit Todo",
                    style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = { Text("Todo title") },
                    value = "$todoTitle",
                    onValueChange = { todoTitle = it })
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = { Text("Todo description") },
                    value = "$todoDesc",
                    onValueChange = { todoDesc = it })
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = important,
                        onCheckedChange = {important = it})
                    Text("Important")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (todoToEdit == null) { // we are in create new todo mode
                            onTodoCreate(
                                TodoItem(
                                    0,
                                    todoTitle,
                                    todoDesc,
                                    Date(System.currentTimeMillis()).toString(),
                                    if (important) TodoPriority.HIGH else TodoPriority.NORMAL,
                                    false
                                )
                            )
                        } else {
                            val editedTodo = todoToEdit.copy(
                                title = todoTitle,
                                description = todoDesc,
                                priority = if (important) TodoPriority.HIGH else TodoPriority.NORMAL
                            )
                            //
                        }

                        onCancel()
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


