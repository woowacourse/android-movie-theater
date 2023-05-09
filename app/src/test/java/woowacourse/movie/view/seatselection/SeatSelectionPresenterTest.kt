package woowacourse.movie.view.seatselection

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.SeatRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class SeatSelectionPresenterTest {
    private lateinit var view: SeatSelectionContract.View
    private lateinit var reservationRepository: ReservationRepository
    private lateinit var seatRepository: SeatRepository
    private lateinit var seatSelectionPresenter: SeatSelectionPresenter

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        reservationRepository = mockk(relaxed = true)
        seatRepository = mockk(relaxed = true)
        seatSelectionPresenter = SeatSelectionPresenter(
            view, reservationRepository, seatRepository
        )
        every { view.getReservationOptions() } returns fakeReservationOptions()
        every { view.getMovie() } returns fakeMovie().toUiModel()
        seatSelectionPresenter.setUp()
    }

    @Test
    fun `선택한 좌석을 예매하면 예매에 해당하는 알림이 등록된다`() {
        every { view.findSelectedSeatsIndex() } returns listOf(0, 1)
        val reservationSlot = slot<ReservationUiModel>()
        every { view.registerReservationAlarm(capture(reservationSlot)) } returns Unit

        val peopleCount = 2
        val movie = fakeMovie()
        repeat(peopleCount) {
            seatSelectionPresenter.selectSeat() {}
        }
        seatSelectionPresenter.reserveSeats()

        val actual = reservationSlot.captured
        val expected = ReservationUiModel(
            "선릉 극장",
            movie.title,
            LocalDateTime.of(movie.screeningStartDate, LocalTime.of(9, 0)),
            peopleCount,
            listOf("A1", "A2"),
            16000
        )

        assertEquals(expected, actual)
    }

    private fun fakeMovie() = Movie(
        1,
        "해리 포터와 마법사의 돌",
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 3, 5),
        Minute(152),
        R.drawable.harry_potter1_poster,
        "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
    )

    private fun fakeReservationOptions(): ReservationOptions {
        val movie = fakeMovie()
        return ReservationOptions(
            "선릉 극장",
            movie.title,
            LocalDateTime.of(movie.screeningStartDate, LocalTime.of(9, 0)),
            2
        )
    }
}
