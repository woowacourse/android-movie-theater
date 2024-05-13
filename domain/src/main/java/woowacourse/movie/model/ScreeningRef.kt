package woowacourse.movie.model

import java.time.LocalDateTime
import java.time.ZoneOffset

data class ScreeningRef(
    val id: Long,
    val movieId: Long,
    val theaterId: Long,
    val screeningTimeStamp: Long,
) {
    companion object {
        val STUB =
            ScreeningRef(
                1,
                Screening.STUB.movie.id,
                Screening.STUB.theater.id,
                Screening.STUB.localDateTime.toEpochSecond(ZoneOffset.UTC),
            )

        fun getStubList(): List<ScreeningRef> {
            var id = 0L
            val listA =
                (1..3).flatMap { day ->
                    (9..12).map { time ->
                        id++
                        ScreeningRef(
                            id,
                            Movie.STUB_A.id,
                            Theater.STUB_A.id,
                            LocalDateTime.of(2024, 3, day, time, 0).toEpochSecond(
                                ZoneOffset.UTC,
                            ),
                        )
                    }
                }

            val listB =
                (1..3).flatMap { day ->
                    (9..12).map { time ->
                        id++
                        ScreeningRef(
                            id,
                            Movie.STUB_A.id,
                            Theater.STUB_B.id,
                            LocalDateTime.of(2024, 3, day, time, 0).toEpochSecond(
                                ZoneOffset.UTC,
                            ),
                        )
                    }
                }

            val listC =
                (1..3).flatMap { day ->
                    (9..12).map { time ->
                        id++
                        ScreeningRef(
                            id,
                            Movie.STUB_A.id,
                            Theater.STUB_C.id,
                            LocalDateTime.of(2024, 3, day, time, 0).toEpochSecond(
                                ZoneOffset.UTC,
                            ),
                        )
                    }
                }

            val listD =
                (1..3).flatMap { day ->
                    (9..12).map { time ->
                        id++
                        ScreeningRef(
                            id,
                            Movie.STUB_B.id,
                            Theater.STUB_C.id,
                            LocalDateTime.of(2024, 3, day, time, 0).toEpochSecond(
                                ZoneOffset.UTC,
                            ),
                        )
                    }
                }

            val listE =
                (1..3).flatMap { day ->
                    (9..12).map { time ->
                        id++
                        ScreeningRef(
                            id,
                            Movie.STUB_C.id,
                            Theater.STUB_C.id,
                            LocalDateTime.of(2024, 3, day, time, 0).toEpochSecond(
                                ZoneOffset.UTC,
                            ),
                        )
                    }
                }
            return listA + listB + listC + listD + listE
        }
    }
}
