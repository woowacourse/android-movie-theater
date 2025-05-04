package woowacourse.movie.presentation.common.extension

import java.time.format.DateTimeFormatter

fun String.toDateTimeFormatter(): DateTimeFormatter? =
    runCatching {
        DateTimeFormatter.ofPattern(this)
    }.getOrNull()
