package com.example.tictactoeinfinity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tictactoeinfinity.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private lateinit var board: Array<Array<Player?>>
    private lateinit var buttons: Array<Array<ImageButton>>
    private var currentPlayer = Player.X
    private val moves = mutableListOf<Pair<Int, Int>>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        setupBoard()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRestart.setOnClickListener {
            resetBoard()
        }
    }

    private fun setupBoard() {
        board = Array(3) { arrayOfNulls<Player>(3) }
        buttons = arrayOf(
            arrayOf(binding.button00, binding.button01, binding.button02),
            arrayOf(binding.button10, binding.button11, binding.button12),
            arrayOf(binding.button20, binding.button21, binding.button22)
        )

        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].setOnClickListener { onCellClicked(row, col) }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] == null) {
            buttons[row][col].setImageResource(currentPlayer.imageRes)
            board[row][col] = currentPlayer
            moves.add(Pair(row, col))

            if (moves.size > 6) {
                val (oldRow, oldCol) = moves.removeAt(0)
                board[oldRow][oldCol] = null
                buttons[oldRow][oldCol].setImageResource(0)
            }

            updateButtonAlpha()

            if (checkWin(currentPlayer)) {
                Toast.makeText(requireContext(), "Win ${currentPlayer.symbol}", Toast.LENGTH_SHORT).show()
                resetBoard()
            } else {
                currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
            }
        }
    }

    private fun updateButtonAlpha() {
        for (i in moves.indices) {
            val (row, col) = moves[i]
            buttons[row][col].alpha = when (i) {
                moves.size - 4 -> 0.9f
                moves.size - 5 -> 0.8f
                moves.size - 6 -> 0.5f
                else -> 1f
            }
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

    private fun resetBoard() {
        board = Array(3) { arrayOfNulls<Player>(3) }
        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].setImageResource(0)
                buttons[row][col].alpha = 1.0f
            }
        }
        moves.clear()
        currentPlayer = Player.X
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    enum class Player(val symbol: String, val imageRes: Int) {
        X("X", R.drawable.x_image),
        O("O", R.drawable.o_image)
    }
}