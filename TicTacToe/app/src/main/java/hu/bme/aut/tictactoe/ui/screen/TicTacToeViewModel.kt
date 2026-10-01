package hu.bme.aut.tictactoe.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel

enum class Player { X, O }
data class BoardCell(val row: Int, val col: Int)

class TicTacToeViewModel : ViewModel() {

    var currentPlayer by mutableStateOf(Player.O)
    var board by mutableStateOf(
        Array(3) {
            Array(3) {
                null as Player?
            }
        }
    )

    /*init {
        board[2][1] = Player.X
    }*/

    fun onCellClicked(cell: BoardCell) {
        if (board[cell.row][cell.col] != null) return

        val newBoard = board.copyOf()
        newBoard[cell.row][cell.col] = currentPlayer
        board = newBoard // state change

        if (checkWin()) {
            //
        } else {
            currentPlayer = if (currentPlayer == Player.X)
                Player.O else Player.X
        }
    }

    fun resetGame() {
        board = Array(3) { Array(3) { null as Player? } }
        currentPlayer = Player.X
    }

    private fun checkWin(): Boolean {
        return false
    }
}