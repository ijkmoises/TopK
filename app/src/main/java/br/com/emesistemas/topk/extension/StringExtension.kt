package br.com.emesistemas.topk.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.getddMMyy(): String {

    val inputPatternSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputPatternSdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date: Date? = inputPatternSdf.parse(this)
        date?.let {
            outputPatternSdf.format(it)
        }.toString()
    } catch (e: Exception) {
        return "-"
    }
}