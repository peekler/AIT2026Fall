package com.example.highlowgamedemo.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.highlowgamedemo.ui.navigation.GameScreenRoute
import kotlin.random.Random

class GameViewModel(var upperBound: Int): ViewModel() {

    private var generatedNumber = 0
    val guessNumber = mutableStateOf(0)

    fun generateNewNumber(){
        generatedNumber = Random.nextInt(upperBound)
    }

    fun checkGuessEqualsToGeneratedNumber(guess: Int): Boolean {
        return (generatedNumber == guess)
    }

    fun isValidGuess(guess: String): Boolean {
        return guess.length < 3 && guess.isDigitsOnly()
    }

    fun isGuessLowerThanGeneratedNumber(guess: Int): Boolean{
        return guess < generatedNumber
    }
    fun isGuessHigerThanGeneratedNumber(guess: Int): Boolean{
        return guess > generatedNumber
    }
}