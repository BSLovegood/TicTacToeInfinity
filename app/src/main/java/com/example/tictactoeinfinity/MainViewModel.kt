package com.example.tictactoeinfinity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoeinfinity.model.Cell
import com.example.tictactoeinfinity.model.Player

class MainViewModel : ViewModel() {
    private var board: Array<Array<Player?>> = Array(3) { arrayOfNulls<Player>(3) }

    private var currentPlayer = Player.X
    private val moves = mutableListOf<Pair<Int, Int>>()


    private val buttonsLiveData = MutableLiveData<Array<Array<Cell>>>(
        Array(3) {
            Array(3) { Cell() }
        }
    )

    fun getButtonsLiveData(): LiveData<Array<Array<Cell>>> = buttonsLiveData

    private val showDialogLiveData = MutableLiveData<String>()
    fun getShowDialogLiveData(): LiveData<String> = showDialogLiveData


    fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] == null) {
            buttonsLiveData.value!![row][col] =
                buttonsLiveData.value!![row][col].copy(imageRes = currentPlayer.imageRes)
            buttonsLiveData.value = buttonsLiveData.value!!.copyOf()
            board[row][col] = currentPlayer
            moves.add(Pair(row, col))

            if (moves.size > 6) {
                val (oldRow, oldCol) = moves.removeAt(0)
                board[oldRow][oldCol] = null
                buttonsLiveData.value!![oldRow][oldCol] =
                    buttonsLiveData.value!![oldRow][oldCol].copy(imageRes = 0)
                buttonsLiveData.value = buttonsLiveData.value!!.copyOf()
            }

            updateButtonAlpha()

            if (checkWin(currentPlayer)) {
                showDialogLiveData.value = currentPlayer.symbol
                resetBoard()
            } else {
                currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
            }
        }
    }


    private fun updateButtonAlpha() {
        for (i in moves.indices) {
            val (row, col) = moves[i]
            val alpha = when (i) {
                moves.size - 4 -> 0.9f
                moves.size - 5 -> 0.8f
                moves.size - 6 -> 0.5f
                else -> 1f
            }
            buttonsLiveData.value!![row][col] =
                buttonsLiveData.value!![row][col].copy(alpha = alpha)
            buttonsLiveData.value = buttonsLiveData.value!!.copyOf()

        }
    }

    private fun checkWin(player: Player): Boolean {
        for (i in 0..2) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)
            ) return true
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player)
    }

    fun resetBoard() {
        board = Array(3) { arrayOfNulls<Player>(3) }
        for (row in 0..2) {
            for (col in 0..2) {
                buttonsLiveData.value!![row][col] =
                    buttonsLiveData.value!![row][col].copy(imageRes = 0)
                buttonsLiveData.value = buttonsLiveData.value!!.copyOf()
                buttonsLiveData.value!![row][col] =
                    buttonsLiveData.value!![row][col].copy(alpha = 1.0f)
                buttonsLiveData.value = buttonsLiveData.value!!.copyOf()
            }
        }
        moves.clear()
        currentPlayer = Player.X
    }

    fun dissmissed() {
        showDialogLiveData.value = ""
    }
}