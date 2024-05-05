package woowacourse.movie.domain.repository

import woowacourse.movie.R
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Ads
import woowacourse.movie.domain.model.ScreenView.Movie
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterCount
import java.time.LocalDate

class DummyScreens : ScreenRepository {
    // TODO 더미 데이터

    val wizardStone =
        Movie(
            id = 0,
            title = "해리 포터와 마법사의 돌",
            runningTime = 152,
            imageSrc = R.drawable.img_poster,
            description =
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                    "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            startDate = LocalDate.of(2024, 3, 1),
            endDate = LocalDate.of(2024, 3, 31),
        )

    val bang =
        Movie(
            id = 1,
            title = "해리 포터와 비밀의 방",
            runningTime = 152,
            imageSrc = R.drawable.secret_room_poster,
            description =
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                    "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            startDate = LocalDate.of(2024, 4, 1),
            endDate = LocalDate.of(2024, 4, 28),
        )

    val zoesu =
        Movie(
            id = 2,
            title = "해리 포터와 아즈카반의 죄수",
            runningTime = 152,
            imageSrc = R.drawable.azkaban_prisoner_poster,
            description =
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                    "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            startDate = LocalDate.of(2024, 5, 1),
            endDate = LocalDate.of(2024, 5, 31),
        )

    val piro =
        Movie(
            id = 3,
            title = "해리 포터와 불의 잔",
            runningTime = 152,
            imageSrc = R.drawable.goblet_of_fire_poster,
            description =
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                    "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            startDate = LocalDate.of(2024, 6, 1),
            endDate = LocalDate.of(2024, 6, 30),
        )

    private val movies =
        List(1) {
            Movie(
                id = 0,
                title = "해리 포터와 마법사의 돌",
                runningTime = 152,
                imageSrc = R.drawable.img_poster,
                description =
                    "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. " +
                        "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                startDate = LocalDate.of(2024, 4, 1),
                endDate = LocalDate.of(2024, 4, 30),
            )
        }

    private val theater =
        Theater(
            id = 0,
            name = "선릉",
            screens =
                listOf(
                    Screen(
                        0,
                        wizardStone,
                        createScreenDateList(
                            wizardStone.startDate,
                            wizardStone.endDate,
                        ),
                    ),
                    Screen(
                        1,
                        bang,
                        createScreenDateList(
                            bang.startDate,
                            bang.endDate,
                        ),
                    ),
                    Screen(
                        2,
                        zoesu,
                        createScreenDateList(
                            zoesu.startDate,
                            zoesu.endDate,
                        ),
                    ),
                    Screen(
                        3,
                        piro,
                        createScreenDateList(
                            piro.startDate,
                            piro.endDate,
                        ),
                    ),
                ),
        )

    val theaters =
        listOf(theater, theater.copy(id = 1, name = "강남"), theater.copy(id = 2, name = "잠실"))

    override fun findTheaterNameById(theaterId: Int): Result<String> =
        runCatching {
            theaters.find { it.id == theaterId }?.name ?: throw NoSuchElementException()
        }

    override fun load(): List<ScreenView> =
        movies.flatMap { movie ->
            listOf(
                wizardStone,
                bang,
                zoesu,
                Ads(R.drawable.img_ads),
                piro,
            )
        }

    override fun loadSeatBoard(id: Int): Result<SeatBoard> =
        runCatching {
            DummySeatBoard.seatBoards.find { seatBoard -> seatBoard.id == id }
                ?: throw NoSuchElementException()
        }

    override fun findByScreenId(
        theaterId: Int,
        movieId: Int,
    ): Result<Screen> =
        runCatching {
            theaters.find { it.id == theaterId }?.screens?.find { screen -> screen.movie.id == movieId }
                ?: throw NoSuchElementException()
        }

    override fun findTheaterCount(movieId: Int): Result<List<TheaterCount>> =
        runCatching {
            val tmpList: MutableList<TheaterCount> = mutableListOf()
            theaters.forEach { theater ->
                val size = theater.findScreenTimeCount(movieId)
                if (size != 0) {
                    tmpList.add(
                        TheaterCount(
                            id = theater.id,
                            name = theater.name,
                            size = size,
                        ),
                    )
                }
            }
            tmpList
        }

    private fun createScreenDateList(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<ScreenDate> {
        return generateSequence(startDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endDate) }
            .map { ScreenDate(it) }
            .toList()
    }
}
