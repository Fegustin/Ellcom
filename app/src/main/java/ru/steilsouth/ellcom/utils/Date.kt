package ru.steilsouth.ellcom.utils

import ru.steilsouth.ellcom.utils.enam.Month
import java.text.SimpleDateFormat
import java.util.*

fun getMonthNumber(month: String): String {
    return when (month) {
        Month.January.rusName -> Month.January.number
        Month.February.rusName -> Month.February.number
        Month.March.rusName -> Month.March.number
        Month.April.rusName -> Month.April.number
        Month.May.rusName -> Month.May.number
        Month.June.rusName -> Month.June.number
        Month.July.rusName -> Month.July.number
        Month.August.rusName -> Month.August.number
        Month.September.rusName -> Month.September.number
        Month.October.rusName -> Month.October.number
        Month.November.rusName -> Month.November.number
        else -> Month.December.number
    }
}

fun getDate(pattern: String): String {
    val calendar = Calendar.getInstance().time
    return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar)
}