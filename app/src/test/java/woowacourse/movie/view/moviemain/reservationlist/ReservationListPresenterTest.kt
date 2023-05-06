package woowacourse.movie.view.moviemain.reservationlist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Money
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationUiModel
import java.time.LocalDate
import java.time.LocalDateTime

internal class ReservationListPresenterTest {
    private lateinit var reservationListPresenter: ReservationListPresenter
    private lateinit var view: ReservationListContract.View
    private lateinit var reservationRepository: ReservationRepository

    @Before
    fun setUp() {
        view = mockk()
        reservationRepository = mockk()
        reservationListPresenter = ReservationListPresenter(view, reservationRepository)
    }

    @Test
    fun `리포지토리에서 예매 목록을 받아와 뷰에 띄운다`() {
        val reservationSlot = slot<List<ReservationUiModel>>()
        every { view.showReservationList(capture(reservationSlot)) } returns Unit
        every { reservationRepository.findAll() } returns fakeReservations()

        reservationListPresenter.loadReservationList()

        val expected = fakeReservations().map { it.toUiModel() }
        val actual = reservationSlot.captured

        assertEquals(expected, actual)
        verify { view.showReservationList(expected) }
    }

    private fun fakeReservation(): Reservation {
        val movie = Movie(
            1,
            "아바타",
            LocalDate.of(2024, 3, 2),
            LocalDate.of(2024, 3, 31),
            Minute(120),
            1,
            "줄거리"
        )
        return Reservation(
            1,
            "선릉 극장",
            movie,
            listOf(Seat(1, 1)),
            LocalDateTime.of(2024, 3, 5, 12, 0),
            Money(10000)
        )
    }

    private fun fakeReservations(): List<Reservation> = listOf(fakeReservation())
}
