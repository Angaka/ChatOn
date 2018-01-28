package com.projects.venom04.chaton.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Venom on 28/01/2018.
 */
class DateHelper {
    companion object {
        fun longToDate(dateLong: Long, dateFormat: String) : String {
            return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date(dateLong))
        }
    }
}