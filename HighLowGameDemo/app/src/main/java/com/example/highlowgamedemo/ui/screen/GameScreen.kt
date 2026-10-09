package com.example.highlowgamedemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    var guess by rememberSaveable() { mutableStateOf("") }

    val gameLogic: GameViewModel = viewModel()

    var resultText: String by rememberSaveable { (mutableStateOf("")) }

    var isValidInput: Boolean by rememberSaveable() { mutableStateOf(false) }

    var isGameOver: Boolean by rememberSaveable() { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = guess,
            label = { Text("Type your guess here") },
            onValueChange = {
                guess = it
                isValidInput = gameLogic.isValidGuess(guess)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Button(
            onClick = {
                resultText = when{
                    gameLogic.checkGuessEqualsToGeneratedNumber(guess.toInt()) -> {isGameOver = true; "You guessed it"}
                    gameLogic.isGuessHigerThanGeneratedNumber(guess.toInt()) ->  "Too high"
                    gameLogic.isGuessLowerThanGeneratedNumber(guess.toInt()) ->  "Too low"
                    else -> "You havent guessed it"
                }

            },
            enabled = isValidInput
        )
        {
            Text(text = "Guess")
        }

        Text(resultText)

        GameEndedDialog(isGameOver,
            onDismiss = {gameLogic.generateNewNumber()},
            onConfirm = {gameLogic.generateNewNumber()}
            )
    }


}


