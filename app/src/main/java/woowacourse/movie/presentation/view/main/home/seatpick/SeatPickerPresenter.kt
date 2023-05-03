package woowacourse.movie.presentation.view.main.home.seatpick

import android.content.Context
import com.example.domain.Reservation
import com.example.domain.ReservationRepository
import com.example.domain.Seat
import com.example.domain.SeatGrade
import com.example.domain.TicketBundle
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferenceUtil
import woowacourse.movie.model.MovieBookingInfo
import woowacourse.movie.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatGradeModel
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SeatPickerPresenter(
    private val view: SeatPickerContract.View,
    private val context: Context,
    private val movieBookingInfo: MovieBookingInfo?
) :
    SeatPickerContract.Presenter {
    private val ticketBundle = TicketBundle()

    override fun onCreate() {
        if (movieBookingInfo == null) {
            view.finishErrorView()
            return
        }
        view.initView(movieBookingInfo.movieInfo.title)
    }

    override fun getDefaultTotalPrice() {
        view.updateDefaultTotalPriceView(0)
    }

    override fun confirmBooking() {
        if (isTicketCountMax()) {
            view.showTicketIsMaxCountView(movieBookingInfo!!.ticketCount)
        } else {
            view.showConfirmDialog()
        }
    }

    override fun bookComplete() {
        val reservation = Reservation(
            ticketBundle.calculateTotalPrice(movieBookingInfo!!.date, movieBookingInfo.time),
            ticketBundle.tickets.size,
            ticketBundle.getSeatNames().joinToString(", "),
            movieBookingInfo.movieInfo.title,
            movieBookingInfo.date,
            movieBookingInfo.time
        )
        ReservationRepository.save(reservation)

        val allowedPushNotification =
            SharedPreferenceUtil(context).getBoolean(
                context.getString(R.string.push_alarm_setting),
                false
            )
        setAlarm(reservation, allowedPushNotification)
        view.showBookCompleteView(reservation.id ?: -1)
    }

    override fun setSeatInfo(seatIndex: Int): SeatModel {
        val seat = Seat(seatIndex)

        val seatGrade: SeatGradeModel = when (seat.getSeatGrade()) {
            SeatGrade.S_CLASS -> SeatGradeModel.S_CLASS
            SeatGrade.A_CLASS -> SeatGradeModel.A_CLASS
            SeatGrade.B_CLASS -> SeatGradeModel.B_CLASS
            SeatGrade.NONE -> SeatGradeModel.NONE
        }
        return SeatModel(seat.getSeatName(), seatGrade)

    }

    override fun calculateTotalPrice() {
        val totalPrice =
            ticketBundle.calculateTotalPrice(movieBookingInfo!!.date, movieBookingInfo.time)
        view.updateTotalPriceView(totalPrice)
    }

    override fun updateSeatStatus(isSelected: Boolean, seatIndex: Int): Boolean {
        if (isSelected) {
            ticketBundle.popTicket(Seat(seatIndex))
            return false
        } else {
            if (isTicketCountMax()) {
                view.showTicketIsMaxCountView(movieBookingInfo!!.ticketCount)
                return false
            }
            ticketBundle.putTicket(Seat(seatIndex))
            return true
        }
    }

    private fun setAlarm(
        reservation: Reservation,
        allowedPushNotification: Boolean
    ) {
        val screeningDateTime = LocalDateTime.of(
            LocalDate.parse(reservation.date, DateTimeFormatter.ISO_DATE),
            LocalTime.parse(reservation.time, DateTimeFormatter.ofPattern("H:mm"))
        )

        val notificationTime =
            screeningDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() -
                    TIME_MILLS_OF_HALF_HOUR

        if (allowedPushNotification)
            view.updateNotification(ReservationResult.from(reservation), notificationTime)
    }

    private fun isTicketCountMax(): Boolean {
        return ticketBundle.tickets.size == movieBookingInfo!!.ticketCount
    }

    companion object {
        const val TIME_MILLS_OF_HALF_HOUR = (1000 * 60 * 30)
    }
}