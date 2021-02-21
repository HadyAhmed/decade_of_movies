package com.hadi.movies.utils

import android.content.res.AssetManager

// reads file from assets
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }

class CallbackAction(private val retry: () -> Unit) {
    fun sendCallBack() = retry()
}

// converts list of strings to concatenation with , or .
// according to the last word in the list
fun List<String>.convertToLineStrings(): String {
    val builder = StringBuilder()
    forEachIndexed { index, s ->
        if (index == size.minus(1)) {
            builder.append("$s.")
        } else {
            builder.append("$s, ")
        }
    }
    return builder.toString()
}