package com.example.highlowgamedemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(modifier: Modifier = Modifier,
               upper: Int,
               gameLogic: GameViewModel) {

    var guess by rememberSaveable() { mutableStateOf("") }

    //val gameLogic: GameViewModel = viewModel()

    var resultText: String by rememberSaveable { (mutableStateOf("")) }

    var isValidInput: Boolean by rememberSaveable() { mutableStateOf(false) }

    var isGameOver: Boolean by rememberSaveable() { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gameLogic.generateNewNumber()
    }

    Column(modifier = modifier) {
        Text("Maxium is: $upper")
        OutlinedTextField(
            value = guess,
            label = { Text("Type your guess here") },
            onValueChange = {
                guess = it
                isValidInput = gameLogic.isValidGuess(guess)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = !isValidInput,
            trailingIcon = {
                if (!isValidInput)
                    Icon(
                        Icons.Filled.Warning,
                        "error",
                        tint = MaterialTheme.colorScheme.error)
            }
        )

        if (!isValidInput) {
            Text(
                text = "Enter a proper guess in the field",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp))
        }

        Text(
            "Guess number: ${gameLogic.guessNumber.value}"
        )

        Button(
            onClick = {
                try {
                    resultText = when {
                        gameLogic.checkGuessEqualsToGeneratedNumber(guess.toInt()) -> {
                            isGameOver = true; "You guessed it"
                        }
                        gameLogic.isGuessHigerThanGeneratedNumber(guess.toInt()) -> "Too high"
                        gameLogic.isGuessLowerThanGeneratedNumber(guess.toInt()) -> "Too low"
                        else -> "You havent guessed it"
                    }
                    if (!isGameOver) {
                        gameLogic.guessNumber.value++
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            },
            enabled = isValidInput
        )
        {
            Text(text = "Guess")
        }

        Text(resultText)

        if (isGameOver) {
            GameEndedDialog(
                onDismiss = {
                    gameLogic.generateNewNumber()
                    isGameOver = false
                    resultText = ""
                    guess = ""
                },
                onConfirm = {
                    gameLogic.generateNewNumber()
                    gameLogic.generateNewNumber()
                    isGameOver = false
                    resultText = ""
                    guess = ""
                }
            )
        }
    }


}


