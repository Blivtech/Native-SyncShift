package com.blivtech.syncshift.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtils {
    // Predefined Date-Time Formats

    const val FORMAT_1 = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_2 = "yyyy-MM-dd 00:00:00"
    const val FORMAT_3 = "dd MMM yyyy hh:mm a"
    const val FORMAT_4 = "dd MMM yyyy"
    const val FORMAT_5 = "yyyy-MM-dd"
    const val FORMAT_6 = "dd-MM-yyyy"
    const val FORMAT_7 = "MMMM dd yyyy, h:mm a"
    const val FORMAT_8 = "dd"
    const val FORMAT_9 = "dd MMM yyyy"
    const val FORMAT_10 = "M yyyy"
    const val FORMAT_11 = "MMMM"
    const val FORMAT_12 = "yyyy"
    const val FORMAT_13 = "MMMM dd yyyy"
    const val FORMAT_14 = "ddMMyyyyHHmmssSSS"
    const val FORMAT_15 = "MMMM yyyy"
    const val FORMAT_16 = "dd MMM yyyy  hh:mm a"
    const val FORMAT_17 = "d"
    const val FORMAT_18 = "M"
    const val FORMAT_19 = "MMM-dd"
    const val FORMAT_20 = "EEEE"




    fun getCurrentDateTime(format: String): String {
        val timestampMilliseconds = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        return simpleDateFormat.format(Date(timestampMilliseconds))
    }

    fun getConvertedDate(currentFormat: String, requiredFormat: String, date: String): String {
        val currentDateFormat = SimpleDateFormat(currentFormat, Locale.ENGLISH)
        val requiredDateFormat = SimpleDateFormat(requiredFormat, Locale.ENGLISH)
        return try {
            val convertedDate = currentDateFormat.parse(date)
            requiredDateFormat.format(convertedDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun getConvertDateFormat(day: Int, month: Int, year: Int, outputFormat: String): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month - 1) // Months are 0-based in Calendar
            set(Calendar.YEAR, year)
        }
        val date = calendar.time
        val sdf = SimpleDateFormat(outputFormat, Locale.getDefault())

        return sdf.format(date)
    }
}