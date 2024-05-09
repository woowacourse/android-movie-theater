package woowacourse.movie.model

import java.time.ZoneOffset

data class ScreeningRef(
    val id: Long,
    val movieId: Long,
    val theaterId: Long,
    val dateTimeStamp: Long,
) {
    companion object {
        val STUB =
            ScreeningRef(
                1,
                Screening.STUB.movie.id,
                Screening.STUB.theater.id,
                Screening.STUB.localDateTime.toEpochSecond(ZoneOffset.UTC),
            )
    }
}
