package com.example.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {
    private val ISO_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun nowIso(): String = ISO_FORMAT.format(Date())

    fun parseIso(iso: String): Date? = try {
        ISO_FORMAT.parse(iso)
    } catch (e: Exception) {
        null
    }

    fun formatFriendly(date: Date, pattern: String = "dd/MM/yyyy HH:mm"): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }
}
