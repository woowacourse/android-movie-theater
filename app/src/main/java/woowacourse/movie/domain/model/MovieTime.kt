package woowacourse.movie.domain.model

import java.time.LocalTime

@JvmInline
value class MovieTime(
    val value: LocalTime = LocalTime.now(),
) {
    constructor(hour: Int, minute: Int) : this(LocalTime.of(hour, minute))
}
