package woowacourse.movie.ui.seat.presenter

import android.content.Intent
import woowacourse.movie.domain.seat.SelectedSeats
import woowacourse.movie.mapper.toModel
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.utils.getSerializableExtraCompat
import java.time.LocalDateTime

class SeatPresenter(
    private val view: SeatContractor.View,
) : SeatContractor.Presenter {
    private val selectedSeats by lazy { SelectedSeats() }
    private lateinit var movieTitle: String
    private lateinit var movieTime: LocalDateTime
    private lateinit var peopleCount: PeopleCountModel
    private lateinit var movieTicketModel: MovieTicketModel

    override fun getBookingInfo(intent: Intent) {
        movieTitle = intent.getSerializableExtraCompat(KEY_TITLE)
            ?: throw IllegalArgumentException()
        movieTime = intent.getSerializableExtraCompat(KEY_TIME)
            ?: throw IllegalArgumentException()
        peopleCount = intent.getSerializableExtraCompat(KEY_PEOPLE_COUNT)
            ?: throw IllegalArgumentException()
    }

    override fun getSelectedSeats() {
        view.putSelectedSeats(selectedSeats.toModel())
    }

    override fun getMovieTicket() {
        movieTicketModel = MovieTicketModel(
            title = movieTitle,
            time = movieTime,
            peopleCount = peopleCount,
            seats = selectedSeats.toModel(),
        )

        view.makeDialog(::setEventOnReservationComplete)
    }

    private fun setEventOnReservationComplete() {
        setReservationData(movieTicketModel)
        view.moveToTicketActivity(movieTicketModel)
    }

    private fun setReservationData(ticket: MovieTicketModel) {
        ReservationTicketMachine.add(ticket)
    }
//

//    private fun updatePriceText(price: Int) {
//        binding.seatPrice.text = getString(R.string.price_with_unit, price)
//    }
//
//    private fun updateButtonEnablement() {
//        binding.seatConfirmButton.isEnabled = selectedSeats.isSelectionDone(peopleCount.count)
//    }
//
//    private fun MovieTicketModel.makeAlarm() {
//        val alarmSwitchState: AlarmSwitchState =
//            AlarmSwitchStateImpl.getInstance(this@SeatSelectionActivity)
//
//        if (alarmSwitchState.isAlarmActivated) alarm.makeAlarm(this)
//    }
//
//    private fun canSelectMoreSeat(seatView: View) =
//        !(!seatView.isSelected && selectedSeats.isSelectionDone(peopleCount.count))
//
//    private fun initBottomField() {
//        binding.seatMovieTitle.text = movieTitle
//        updatePriceText(selectedSeats.getAllPrice(movieTime))
//    }

    companion object {
        private const val KEY_TITLE = "title"
        private const val KEY_TIME = "time"
        private const val KEY_PEOPLE_COUNT = "count"
    }
}
