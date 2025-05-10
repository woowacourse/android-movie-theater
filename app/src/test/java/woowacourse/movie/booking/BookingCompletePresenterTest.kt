package woowacourse.movie.booking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.booking.complete.BookingCompleteContract
import woowacourse.movie.booking.complete.BookingCompletePresenter
import woowacourse.movie.booking.complete.BookingType
import woowacourse.movie.booking.complete.alarm.AlarmScheduler
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenterTest {
    private lateinit var presenter: BookingCompletePresenter
    private lateinit var mockView: BookingCompleteContract.View
    private lateinit var mockReservationDao: ReservationDao
    private lateinit var mockAlarmScheduler: AlarmScheduler
    private lateinit var mockTicket: Ticket
    private lateinit var mockTicketUiData: TicketUiModel

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockReservationDao = mockk(relaxed = true)
        mockAlarmScheduler = mockk(relaxed = true)

        val seats = listOf(SEAT_A1, SEAT_A2, SEAT_C1)
        mockTicket = createTicket(SEOLLEUNG, seats)
        mockTicketUiData = mockTicket.toUiModel()

        presenter =
            BookingCompletePresenter(
                view = mockView,
                reservationDao = mockReservationDao,
                alarmScheduler = mockAlarmScheduler,
            )
    }

    @Test
    fun `영화 예매 정보를 화면에 표시할 수 있다`() {
        presenter.initializeData(mockTicketUiData)

        verify { mockView.showBookingCompleteResult(mockTicketUiData) }
        verify {
            mockView.showBookingCompleteResult(
                match {
                    it.headCount == 3 &&
                        it.selectedDateText == "2028.10.13" &&
                        it.selectedTimeText == "11:00" &&
                        it.seats == "A1, A2, C1" &&
                        it.totalPrice == "35,000"
                },
            )
        }
    }

    @Test
    fun `예매한 시간에 알림을 설정할 수 있다`() {
        presenter.setNotification(
            ticket = mockTicketUiData,
            bookingType = BookingType.RESERVATION.name,
        )

        verify { mockAlarmScheduler.scheduleAlarm(BookingType.RESERVATION.name, mockTicketUiData) }
    }

    @Test
    fun `예매한 내역을 데이터베이스에 저장할 수 있다`() {
        presenter.saveReservation(
            ticket = mockTicketUiData,
            bokkingType = BookingType.RESERVATION.name,
        )

        val reservation = mockTicketUiData.toEntity()

        verify { mockReservationDao.insertReservation(reservation) }
    }
}
