package hu.bme.ait.todoapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import hu.bme.ait.todoapp.data.TodoItem

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.getAllTodoNum() == 0) {
            Text("No data.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.getAllToDoList()) {
                    TodoCard(it)
                }
            }
        }
    }
}


@Composable
fun TodoCard(
    todoItem: TodoItem
) {
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
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*Image(
                painter = painterResource(id = todoItem.priority.getIcon()),
                contentDescription = "Priority",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )*/

            Text(todoItem.title, modifier = Modifier.fillMaxWidth(0.2f))
            Spacer(modifier = Modifier.fillMaxSize(0.25f))
            Checkbox(
                checked = todoItem.isDone,
                onCheckedChange = { }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {}
            ) { Text("Del") }
            Button(
                onClick = {}
            ) { Text("Edit") }
        }
    }
}
