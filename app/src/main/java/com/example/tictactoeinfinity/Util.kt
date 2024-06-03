package com.example.tictactoeinfinity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.fragment.app.Fragment

private const val DEFAULT_CLIPBOARD_LABEL_TEXT = "Copied text"

fun Fragment.copyTextToClipboard(
    text: String,
    label: String = DEFAULT_CLIPBOARD_LABEL_TEXT
) {
    (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
        .setPrimaryClip(
            ClipData.newPlainText(label, text)
        )
}