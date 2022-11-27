package com.tods.mapchallenge.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tods.mapchallenge.data.model.ResponseModel

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun String.limitedDescription(characters: Int): String {
    if (this.length > characters) {
        val firstCharacter = 0
        return this.substring(firstCharacter, characters)
    }
    return this
}