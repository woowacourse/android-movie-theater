package woowacourse.movie.repository

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.presentation.repository.TheaterRepository
import woowacourse.movie.presentation.uimodel.TheaterUiModel

object TheaterRepositoryImpl : TheaterRepository {
    private val dummyTheaters: List<Theater> =
        listOf(
            Theater(id = 0, name = "선릉", movieId = listOf(0, 1, 2, 3, 4, 5, 6)),
            Theater(id = 1, name = "잠실", movieId = listOf(0, 2, 5, 7, 9)),
            Theater(id = 2, name = "강남", movieId = listOf(1, 2, 3, 4, 5, 6, 7, 8)),
            Theater(id = 3, name = "엄청나게 긴 극장이름~~~~~", movieId = listOf(1, 2, 3, 4, 5, 6, 7, 8)),
        )

    // 극장ID, 상영정보< 영화ID, 날짜리스트<날짜, 시간리스트> >
    private val theaterScreeningInfo: Map<Int, Map<Int, Map<String, List<String>>>> =
        mapOf(
            // 극장
            0 to
                mapOf(
                    // 상영 영화
                    0 to
                        mapOf(
                            // 날짜
                            "2024-04-08" to listOf("12:00", "15:00"),
                        ),
                    1 to
                        mapOf(
                            // 날짜
                            "2024-04-09" to listOf("12:00"),
                        ),
                    2 to
                        mapOf(
                            // 날짜
                            "2024-04-10" to listOf("12:00", "15:00"),
                        ),
                    3 to
                        mapOf(
                            // 날짜
                            "2024-04-10" to listOf("12:00", "15:00"),
                        ),
                    4 to
                        mapOf(
                            // 날짜
                            "2024-04-10" to listOf("12:00", "15:00"),
                        ),
                    5 to
                        mapOf(
                            // 날짜
                            "2024-04-10" to listOf("12:00", "15:00"),
                        ),
                    6 to
                        mapOf(
                            // 날짜
                            "2024-04-10" to listOf("12:00", "15:00"),
                        ),
                ),
            1 to
                mapOf(
                    // 상영 영화
                    0 to
                        mapOf(
                            // 날짜
                            "2024-04-02" to listOf("15:00", "18:00", "22:00"),
                        ),
                    2 to
                        mapOf(
                            // 날짜
                            "2024-04-04" to listOf("12:00", "15:00"),
                        ),
                    5 to
                        mapOf(
                            // 날짜
                            "2024-04-04" to listOf("12:00", "15:00"),
                        ),
                    7 to
                        mapOf(
                            // 날짜
                            "2024-04-04" to listOf("12:00", "15:00"),
                        ),
                    9 to
                        mapOf(
                            // 날짜
                            "2024-04-04" to listOf("12:00", "15:00"),
                        ),
                ),
            2 to
                mapOf(
                    // 상영 영화
                    1 to
                        mapOf(
                            // 날짜
                            "2024-04-19" to listOf("12:00", "15:00"),
                        ),
                    2 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    3 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    4 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    5 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    6 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    7 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    8 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                ),
            3 to
                mapOf(
                    // 상영 영화
                    1 to
                        mapOf(
                            // 날짜
                            "2024-04-19" to listOf("12:00", "15:00"),
                        ),
                    2 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    3 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    4 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    5 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    6 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    7 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                    8 to
                        mapOf(
                            // 날짜
                            "2024-04-20" to listOf("15:00", "16:00"),
                            "2024-04-24" to listOf("10:00", "17:00"),
                        ),
                ),
        )

    override fun theaters(): List<Theater> = dummyTheaters

    override fun screenTimesCount(
        theaterId: Int,
        movieId: Int,
    ): Int {
        return theaterScreeningInfo[theaterId]?.get(movieId)?.values?.sumOf { it.count() } ?: 0
    }

    override fun theatersInfo(movieId: Int): List<TheaterUiModel> {
        return dummyTheaters.mapNotNull { theater ->
            val count = screenTimesCount(theater.id, movieId)
            if (count != 0) TheaterUiModel(theater.id, theater.name, count)
            else null
        }
    }

    override fun theaterName(theaterId: Int): String {
        return dummyTheaters.find {
            theaterId == it.id
        }?.name ?: ""
    }

    override fun getScreeningDateInfo(
        theaterId: Int,
        movieId: Int,
    ): List<String> {
        return theaterScreeningInfo[theaterId]?.get(movieId)?.keys?.toList() ?: listOf()
    }

    override fun getScreeningTimeInfo(
        theaterId: Int,
        movieId: Int,
        date: String,
    ): List<String> {
        return theaterScreeningInfo[theaterId]?.get(movieId)?.get(date) ?: listOf()
    }
}
