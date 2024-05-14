package woowacourse.movie.data.repository.remote

import woowacourse.movie.R
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.SeatRank
import woowacourse.movie.domain.model.Theater
import java.time.LocalDate

object DummyData {
    // TODO 더미 데이터
    val seatBoard =
        SeatBoard(
            0,
            4,
            5,
            listOf(
                Seat("A", 0, SeatRank.B),
                Seat("A", 1, SeatRank.B),
                Seat("A", 2, SeatRank.B),
                Seat("A", 3, SeatRank.B),
                Seat("B", 0, SeatRank.B),
                Seat("B", 1, SeatRank.B),
                Seat("B", 2, SeatRank.B),
                Seat("B", 3, SeatRank.B),
                Seat("C", 0, SeatRank.S),
                Seat("C", 1, SeatRank.S),
                Seat("C", 2, SeatRank.S),
                Seat("C", 3, SeatRank.S),
                Seat("D", 0, SeatRank.S),
                Seat("D", 1, SeatRank.S),
                Seat("D", 2, SeatRank.S),
                Seat("D", 3, SeatRank.S),
                Seat("E", 0, SeatRank.A),
                Seat("E", 1, SeatRank.A),
                Seat("E", 2, SeatRank.A),
                Seat("E", 3, SeatRank.A),
            ),
        )

    val seatBoards = listOf(seatBoard, seatBoard.copy(id = 1), seatBoard.copy(id = 2))

    val wizardStone =
        ScreenView.Movie(
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
        ScreenView.Movie(
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
        ScreenView.Movie(
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
        ScreenView.Movie(
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

    val movies =
        List(1) {
            ScreenView.Movie(
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

    val theater =
        Theater(
            id = 0,
            name = "선릉 극장",
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
        listOf(theater, theater.copy(id = 1, name = "강남 극장"), theater.copy(id = 2, name = "잠실 극장"))

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
