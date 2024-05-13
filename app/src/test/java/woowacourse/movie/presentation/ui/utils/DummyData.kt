package woowacourse.movie.presentation.ui.utils

import woowacourse.movie.R
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenDate
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Ads
import woowacourse.movie.domain.model.ScreenView.Movie
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.domain.model.SeatRank
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.ui.detail.ScreenDetailUiModel
import java.time.LocalDate
import java.time.LocalDateTime

object DummyData {
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

    private val theaters =
        listOf(theater, theater.copy(id = 1, name = "강남"), theater.copy(id = 2, name = "잠실"))

    const val THEATER_ID = 0

    const val RESERVATION_ID = 0L

    const val MOVIE_ID = 0

    val dummySeats =
        listOf(
            Seat("A", 1, SeatRank.B),
            Seat("A", 2, SeatRank.B),
            Seat("A", 3, SeatRank.B),
            Seat("B", 1, SeatRank.S),
            Seat("B", 2, SeatRank.S),
            Seat("B", 3, SeatRank.S),
            Seat("C", 1, SeatRank.A),
            Seat("C", 2, SeatRank.A),
            Seat("C", 3, SeatRank.A),
        )

    val seatBoard =
        SeatBoard(
            1,
            3,
            1,
            dummySeats,
        )

    val dummyScreen =
        Screen(
            0,
            wizardStone,
            createScreenDateList(
                wizardStone.startDate,
                wizardStone.endDate,
            ),
        )

    val dummyScreenDetail =
        ScreenDetailUiModel(
            screenId = dummyScreen.id,
            theaterId = THEATER_ID,
            screen = dummyScreen,
            selectableDates = dummyScreen.selectableDates,
            selectedDate = dummyScreen.selectableDates.first(),
            selectedTime = dummyScreen.selectableDates.first().getSelectableTimes().first(),
        )

    val dummyTheaterCount =
        listOf(
            TheaterCount(
                1,
                "선릉",
                180,
            ),
            TheaterCount(
                3,
                "강남",
                258,
            ),
        )

    val dummyReservationInfo =
        ReservationInfo(
            theaterId = 0,
            dateTime = LocalDateTime.now(),
            ticketQuantity = 2,
        )

    val dummySeat =
        Seat(
            column = "A",
            row = 1,
            seatRank = SeatRank.A,
        )

    val dummySelectedSeatModel =
        SeatModel(
            dummySeat.column,
            dummySeat.row,
            dummySeat.seatRank,
            isSelected = true,
        )

    val dummyUnselectedSeatModel =
        SeatModel(
            dummySeat.column,
            dummySeat.row,
            dummySeat.seatRank,
            isSelected = false,
        )

    val dummyReservation =
        Reservation(
            id = 0,
            theaterName = "선릉",
            movieTitle = piro.title,
            ticketCount = 3,
            seats = listOf(dummySeat),
            dateTime = LocalDateTime.now(),
        )

    val dummyReservations = listOf(dummyReservation)

    fun load(): List<ScreenView> =
        movies.flatMap {
            listOf(
                wizardStone,
                bang,
                zoesu,
                Ads(R.drawable.img_ads),
                piro,
            )
        }

    private fun createScreenDateList(
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<ScreenDate> {
        return generateSequence(startDate) { it.plusDays(1) }.takeWhile { !it.isAfter(endDate) }
            .map { ScreenDate(it) }.toList()
    }
}
