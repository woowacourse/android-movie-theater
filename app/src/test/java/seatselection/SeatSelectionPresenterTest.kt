package seatselection

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.SeatInfo
import woowacourse.movie.domain.theater.Size
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationOptions
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatInfoUiModel
import woowacourse.movie.view.seatselection.SeatSelectionContract
import woowacourse.movie.view.seatselection.SeatSelectionPresenter
import java.time.LocalDateTime

class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionContract.Presenter
    private lateinit var view: SeatSelectionContract.View
    private val reserveOption =
        ReservationOptions("해리포터와 마법사의 돌", LocalDateTime.of(2023, 4, 1, 1, 12, 0), 1, "선릉 극장")

    @Before
    fun setUp() {
        view = mockk()
        every { view.showSeatMaxSelectionToast() } just runs
        every { view.showWrongInputToast() } just runs

        val reservationRepository = object : ReservationRepository {
            val reservations = mutableListOf<Reservation>()
            override fun add(reservation: Reservation) {
                reservations.add(reservation)
            }

            override fun findAll(): List<Reservation> {
                return reservations.toList()
            }
        }

        val theaterRepository = object : TheaterRepository {
            private val rowGrade = mapOf(
                0 to Grade.B,
                1 to Grade.S,
                2 to Grade.A,
            )
            private val seatInfo = SeatInfo(Size(3, 4), rowGrade)

            val theaters: List<Theater> = listOf(Theater("선릉 극장", seatInfo, listOf()))

            override fun findAll(): List<Theater> {
                return theaters.toList()
            }

            override fun findTheater(name: String): Theater {
                return theaters.find { it.name == name }
                    ?: throw java.lang.IllegalArgumentException("존재하지 않는 상영관입니다.")
            }
        }
        presenter =
            SeatSelectionPresenter(view, reserveOption, reservationRepository, theaterRepository)
    }

    @Test
    fun 좌석_정보_UiModel을_띄울_수_있다() {
        val gradeColor = mapOf(
            Grade.B to 1,
            Grade.S to 2,
            Grade.A to 3,
        )

        val slot = slot<SeatInfoUiModel>()
        every { view.createRow(capture(slot)) } just runs

        presenter.fetchSeatsData(gradeColor)

        val expected = SeatInfoUiModel(3, 4, mapOf(0 to 1, 1 to 2, 2 to 3))

        assertEquals(expected, slot.captured)
        verify { view.createRow(expected) }
    }

    @Test
    fun 예약_확인_버튼_클릭_시_예약을_진행할_수_있다() {
        val slot = slot<ReservationUiModel>()
        val reservation = Reservation(
            reserveOption.title,
            reserveOption.screeningDateTime,
            listOf(),
            Price(0),
            "선릉 극장",
        )
        every { view.onReserveClick(capture(slot)) } just runs
        presenter.reserve()
        verify { view.onReserveClick(reservation.toUiModel()) }
    }

    @Test
    fun 좌석_선택_시_좌석과_가격_뷰의_상태를_변경할_수_있다() {
        val indexSlot = slot<Int>()
        val isSelectAllSlot = slot<Boolean>()
        val priceSlot = slot<Int>()
        every { view.onSeatSelectedByIndex(capture(indexSlot), capture(isSelectAllSlot)) } just runs
        every { view.setPrice(capture(priceSlot)) } just runs

        presenter.updateSeat(0, 0)
        assertEquals(0, indexSlot.captured)
        assertEquals(true, isSelectAllSlot.captured)
        assertEquals(10000, priceSlot.captured)
        verify { view.onSeatSelectedByIndex(indexSlot.captured, isSelectAllSlot.captured) }
        verify { view.setPrice(priceSlot.captured) }
    }

    @Test
    fun 좌석_선택_해제_시_좌석과_가격_뷰의_상태를_변경할_수_있다() {
        val indexSlot = slot<Int>()
        val priceSlot = slot<Int>()
        every { view.onSeatSelectedByIndex(any(), any()) } just runs
        every { view.onSeatDeselectedByIndex(capture(indexSlot)) } just runs
        every { view.setPrice(capture(priceSlot)) } just runs

        presenter.updateSeat(0, 0)
        presenter.updateSeat(0, 0)
        assertEquals(0, indexSlot.captured)
        assertEquals(0, priceSlot.captured)
        verify { view.onSeatDeselectedByIndex(indexSlot.captured) }
        verify { view.setPrice(priceSlot.captured) }
    }

    @Test
    fun 이미_선택한_좌석_수가_최대_인_경우_토스트를_띄운다() {
        val priceSlot = slot<Int>()
        every { view.onSeatSelectedByIndex(any(), any()) } just runs
        every { view.setPrice(capture(priceSlot)) } just runs

        presenter.updateSeat(0, 0)
        presenter.updateSeat(0, 1)
        assertEquals(10000, priceSlot.captured)
        verify(exactly = 1) { view.showSeatMaxSelectionToast() }
        verify { view.setPrice(priceSlot.captured) }
    }

    @Test
    fun 선택한_좌석이_범위_밖일_경우_토스트를_띄운다() {
        val priceSlot = slot<Int>()
        every { view.setPrice(capture(priceSlot)) } just runs

        presenter.updateSeat(5, 5)
        assertEquals(0, priceSlot.captured)
        verify(exactly = 1) { view.showWrongInputToast() }
        verify { view.setPrice(priceSlot.captured) }
    }
}
