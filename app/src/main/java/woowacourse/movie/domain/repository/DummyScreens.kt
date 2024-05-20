package woowacourse.movie.domain.repository

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Seats
import java.time.LocalDate

class DummyScreens(
    private val seatsRepository: SeatsRepository = DummySeats(),
) : ScreenRepository {
    // TODO 더미 데이터
    private val screenData =
        listOf(
            ScreenData(
                id = 1,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 15)),
            ),
            ScreenData(
                id = 2,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 3, 17)),
            ),
            ScreenData(
                id = 3,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 19)),
            ),
            ScreenData(
                id = 4,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 20)),
            ),
            ScreenData(
                id = 5,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 21)),
            ),
            ScreenData(
                id = 6,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 24)),
            ),
            ScreenData(
                id = 7,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 25)),
            ),
            ScreenData(
                id = 8,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 25)),
            ),
            ScreenData(
                id = 9,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 26)),
            ),
            ScreenData(
                id = 10,
                Movie(
                    id = 1,
                    "해리 포터와 마법사의 돌",
                    151,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 27)),
            ),
            ScreenData(
                id = 11,
                Movie(
                    id = 2,
                    "해리 포터와 마법사의 돌2",
                    152,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 28)),
            ),
            ScreenData(
                id = 12,
                Movie(
                    id = 3,
                    "해리 포터와 마법사의 돌3",
                    153,
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                ),
                dateRange = DateRange(LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 29)),
            ),
        )

    override fun load(): List<ScreenData> = screenData

    override fun findById(id: Int): Result<ScreenData> = runCatching { screenData.find { it.id == id } ?: throw NoSuchElementException() }

    override fun seats(screenId: Int): Seats = seatsRepository.findById(screenId)
}
