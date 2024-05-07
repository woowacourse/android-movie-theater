package woowacourse.movie.util

import java.text.DecimalFormat

object Formatter {
    fun Int.formatPrice(): String {
        return DecimalFormat("#,###").format(this)
    }

    fun Int.formatRow(): Char {
        return (this + 'A'.code).toChar()
    }

    fun String.unFormatRow(): Int {
        return this.first() - 'A'
    }

    fun Int.formatColumn(): String {
        return (this + 1).toString()
    }

    fun String.unFormatColumn(): Int {
        return this.substring(1).toInt()
    }
}
