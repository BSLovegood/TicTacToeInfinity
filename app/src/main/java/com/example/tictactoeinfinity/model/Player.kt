package com.example.tictactoeinfinity.model

import com.example.tictactoeinfinity.R

enum class Player(val symbol: String, val imageRes: Int) {
    X("X", R.drawable.x_image),
    O("O", R.drawable.o_image)
}