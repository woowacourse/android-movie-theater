package woowacourse.movie.domain.repository

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Theaters
import java.time.LocalDate

class DummyTheaters : TheaterRepository {
    private val theaters =
        mutableListOf(
            Theater(
                1,
                "선릉 극장",
                listOf(
                    ScreenData(
                        id = 1,
                        MovieData(
                            id = 1,
                            "해리 포터와 마법사의 돌",
                            151,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
                    ),
                    ScreenData(
                        id = 2,
                        MovieData(
                            id = 2,
                            "해리 포터와 마법사의 돌2",
                            152,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
                    ),
                    ScreenData(
                        id = 3,
                        MovieData(
                            id = 3,
                            "해리 포터와 마법사의 돌3",
                            153,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 5)),
                    ),
                    ScreenData(
                        id = 12,
                        MovieData(
                            id = 3,
                            "해리 포터와 마법사의 돌3",
                            153,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 12), LocalDate.of(2024, 3, 14)),
                    ),
                ),
            ),
            Theater(
                2,
                "잠실 극장",
                listOf(
                    ScreenData(
                        id = 4,
                        MovieData(
                            id = 1,
                            "해리 포터와 마법사의 돌4",
                            151,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 4), LocalDate.of(2024, 3, 6)),
                    ),
                    ScreenData(
                        id = 5,
                        MovieData(
                            id = 2,
                            "해리 포터와 마법사의 돌5",
                            152,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 7)),
                    ),
                    ScreenData(
                        id = 6,
                        MovieData(
                            id = 3,
                            "해리 포터와 마법사의 돌6",
                            153,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 6), LocalDate.of(2024, 3, 8)),
                    ),
                    ScreenData(
                        id = 7,
                        MovieData(
                            id = 1,
                            "해리 포터와 마법사의 돌7",
                            151,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 7), LocalDate.of(2024, 3, 9)),
                    ),
                    ScreenData(
                        id = 9,
                        MovieData(
                            id = 3,
                            "해리 포터와 마법사의 돌3",
                            153,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 9), LocalDate.of(2024, 3, 11)),
                    ),
                    ScreenData(
                        id = 10,
                        MovieData(
                            id = 1,
                            "해리 포터와 마법사의 돌",
                            151,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 12)),
                    ),
                    ScreenData(
                        id = 11,
                        MovieData(
                            id = 2,
                            "해리 포터와 마법사의 돌2",
                            152,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 13)),
                    ),
                ),
            ),
            Theater(
                3,
                "강남 극장",
                listOf(
                    ScreenData(
                        id = 1,
                        MovieData(
                            id = 1,
                            "해리 포터와 마법사의 돌",
                            151,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
                    ),
                    ScreenData(
                        id = 2,
                        MovieData(
                            id = 2,
                            "해리 포터와 마법사의 돌2",
                            152,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
                    ),
                    ScreenData(
                        id = 8,
                        MovieData(
                            id = 2,
                            "해리 포터와 마법사의 돌2",
                            152,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 8), LocalDate.of(2024, 3, 10)),
                    ),
                    ScreenData(
                        id = 9,
                        MovieData(
                            id = 3,
                            "해리 포터와 마법사의 돌3",
                            153,
                            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                                "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        ),
                        dateRange = DateRange(LocalDate.of(2024, 3, 9), LocalDate.of(2024, 3, 11)),
                    ),
                ),
            ),
        )

    override fun loadAll(): Theaters = Theaters(theaters)

    override fun findById(id: Int): Theater =
        theaters.firstOrNull { it.id == id } ?: throw IllegalArgumentException("해당하는 id의 Theater가 없습니다.")
}
