package com.example.highlowgamedemo.ui.screen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel: ViewModel() {

    private var generatedNumber = 0

    init {
        generateNewNumber()
    }

    fun generateNewNumber(){
        generatedNumber = Random.nextInt(10)
    }

    fun checkGuessEqualsToGeneratedNumber(guess: Int): Boolean {
        return (generatedNumber == guess)
    }

    fun isValidGuess(guess: String): Boolean {
        return guess.length < 2 && guess.isDigitsOnly()
    }

    fun isGuessLowerThanGeneratedNumber(guess: Int): Boolean{
        return guess < generatedNumber
    }
    fun isGuessHigerThanGeneratedNumber(guess: Int): Boolean{
        return guess > generatedNumber
    }
}