package woowacourse.movie.util

import java.text.DecimalFormat

fun Int.formatPrice(): String {
    return DecimalFormat("#,###").format(this)
}

fun Int.formatSeatRow(): Char {
    return (this + 'A'.code).toChar()
}

fun String.unFormatSeatRow(): Int {
    return this.first() - 'A'
}

fun Int.formatSeatColumn(): String {
    return (this + 1).toString()
}

fun String.unFormatSeatColumn(): Int {
    return substring(1).toInt()
}
