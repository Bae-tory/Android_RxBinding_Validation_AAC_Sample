package com.project.sweettrackersample.component


object StringUtil {
    @JvmStatic
    fun truncate1TimeTyte(str: String) =
        str.substring(0, 10)

    @JvmStatic
    fun truncate2TimeType(str: String) =
        str.substring(str.length - 8, str.length)
}
