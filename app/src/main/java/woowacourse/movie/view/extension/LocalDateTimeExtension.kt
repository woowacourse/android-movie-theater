package woowacourse.movie.view.extension

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toEpochMilli(): Long {
    return atZone(ZoneId.of(zoneId))
        .toInstant()
        .toEpochMilli()
}

private const val zoneId = "Asia/Seoul"
