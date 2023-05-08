package woowacourse.movie.view

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Price
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.ReservationDetail
import woowacourse.movie.domain.seat.MovieSeatRow
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.fragment.reservationlist.ReservationListContract
import woowacourse.movie.fragment.reservationlist.ReservationListPresenter
import woowacourse.movie.view.data.ReservationsViewData
import woowacourse.movie.view.mapper.ReservationMapper.toView
import java.time.LocalDate
import java.time.LocalDateTime

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var view: ReservationListContract.View
    private lateinit var reservationDataSource: ReservationDataSource
    private lateinit var movie: Movie
    private lateinit var reservationDetail: ReservationDetail
    private lateinit var seats: Seats
    private lateinit var seat: Seat
    private lateinit var reservation: Reservation

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        reservationDataSource = mockk(relaxed = true)
        movie = mockk(relaxed = true)
        reservationDetail = mockk(relaxed = true)
        seats = mockk(relaxed = true)
        seat = mockk(relaxed = true)
        reservation = mockk(relaxed = true)
        every { movie } returns Movie(
            Image(0), "", DateRange(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5)), 10, ""
        )
        every { reservationDetail } returns ReservationDetail(
            LocalDateTime.of(2023, 1, 3, 10, 0),
            1,
            "선릉"
        )
        every { seat } returns Seat(MovieSeatRow(0), 1)
        every { seats } returns Seats(listOf(seat))

        presenter = ReservationListPresenter(view, reservationDataSource)
    }

    @Test
    fun `예약 정보를 불러워서 view의 리사이클러뷰 세팅 함수에 넘겨준다`() {
        every { reservationDataSource.getData() } returns listOf(
            Reservation(
                movie,
                reservationDetail, seats, Price(10000)
            )
        )
        // when
        val capturedReservationsViewData = slot<ReservationsViewData>()
        every { view.initReservationRecyclerView(capture(capturedReservationsViewData)) } answers { nothing }

        presenter.initReservationRecyclerView()

        // then
        val actualReservationsViewData = capturedReservationsViewData.captured
        val expectedReservationsViewData = ReservationsViewData(
            listOf(
                Reservation(
                    movie,
                    reservationDetail, seats, Price(10000)
                ).toView()
            )
        )
        assertEquals(expectedReservationsViewData, actualReservationsViewData)
    }
}
