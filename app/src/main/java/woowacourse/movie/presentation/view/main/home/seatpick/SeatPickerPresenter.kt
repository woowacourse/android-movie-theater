package woowacourse.movie.presentation.view.main.home.seatpick

import com.example.domain.Seat
import com.example.domain.SeatGrade
import com.example.domain.TicketBundle
import woowacourse.movie.data.SharedPreference
import woowacourse.movie.data.SharedPreferenceUtil
import woowacourse.movie.data.database.MovieHelper
import woowacourse.movie.data.model.MovieBookingEntity
import woowacourse.movie.presentation.model.MovieBookingInfo
import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatGradeModel
import woowacourse.movie.presentation.view.main.home.seatpick.model.SeatModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SeatPickerPresenter(
    private val view: SeatPickerContract.View,
    private val sharedPreferenceUtil: SharedPreference,
    private val movieHelper: MovieHelper,
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
        view.updateDefaultTotalPriceView(BASE_TOTAL_MONEY)
    }

    override fun confirmBooking() {
        if (isTicketCountMax()) {
            view.showConfirmDialog()
        } else {
            view.showTicketIsMaxCountView(movieBookingInfo!!.ticketCount)
        }
    }

    override fun bookComplete() {
        val movieBookingEntity = MovieBookingEntity(
            movieBookingInfo!!.movieInfo.title,
            movieBookingInfo.date,
            movieBookingInfo.time,
            ticketBundle.tickets.size,
            ticketBundle.getSeatNames().joinToString(", "),
            ticketBundle.calculateTotalPrice(movieBookingInfo.date, movieBookingInfo.time)
        )
        movieHelper.writeMovie(movieBookingEntity)

        val allowedPushNotification =
            sharedPreferenceUtil.getBoolean(
                SharedPreferenceUtil.ALARM_KEY,
                false
            )
        setAlarm(movieBookingEntity, allowedPushNotification)
        view.showBookCompleteView(ReservationResult.from(movieBookingEntity))
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
        view.updateTotalPriceView(totalPrice.toString())
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
        bookingData: MovieBookingEntity,
        allowedPushNotification: Boolean
    ) {
        val screeningDateTime = LocalDateTime.of(
            LocalDate.parse(bookingData.date, DateTimeFormatter.ISO_DATE),
            LocalTime.parse(bookingData.time, DateTimeFormatter.ofPattern("H:mm"))
        )

        val notificationTime =
            screeningDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() -
                    TIME_MILLS_OF_HALF_HOUR

        if (allowedPushNotification)
            view.updateNotification(ReservationResult.from(bookingData), notificationTime)
    }

    private fun isTicketCountMax(): Boolean {
        return ticketBundle.tickets.size == movieBookingInfo!!.ticketCount
    }

    companion object {
        const val TIME_MILLS_OF_HALF_HOUR = (1000 * 60 * 30)
        private const val BASE_TOTAL_MONEY = "0"
    }
}