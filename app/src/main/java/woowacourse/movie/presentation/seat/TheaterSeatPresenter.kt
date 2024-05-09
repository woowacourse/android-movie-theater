package woowacourse.movie.presentation.seat

import android.util.Log
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.theater.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TheaterSeatPresenter(
    private val movieRepository: MovieRepository,
    private val view: TheaterSeatContract.View,
    private val ticketLimit: Int,
    private val dateTime: String,
    private val cinema: Cinema,
) :
    TheaterSeatContract.Presenter {
    private val seats: MutableMap<String, Seat> = mutableMapOf()
    private val selectedSeats = mutableListOf<String>()
    private var totalPrice = 0

    init {
        val rows = mapOf(1 to "B", 2 to "B", 3 to "S", 4 to "S", 5 to "A")
        for (row in 1..5) {
            for (col in 1..4) {
                val seatId = "${row.toChar() + (('A' - 1).code)}$col"
                val grade = rows[row] ?: "B"
                seats[seatId] = Seat(row.toChar() + (('A' - 1).code), col, grade)
            }
        }
        view.showTitle(cinema.theater.movie.title)
        view.showPrice(totalPrice)
    }

    override fun toggleSeatSelection(seatId: String) {
        val seat = seats[seatId] ?: return
        if (seatId in selectedSeats) {
            seat.chosen = false
            selectedSeats.remove(seatId)
        } else {
            if (selectedSeats.size >= ticketLimit) return
            seat.chosen = true
            selectedSeats.add(seatId)
        }
        updateSeatBackground(seatId)
        calculateAndUpdateTotalPrice()
    }

    private fun updateSeatBackground(seatId: String) {
        val seat = seats[seatId] ?: return
        if (seat.chosen) {
            view.setSeatBackground(seatId, "#FF0000")
        } else {
            view.setSeatBackground(seatId, "#FFFFFF")
        }
    }

    override fun completeSeatSelection() {
        if (seats.values.count { it.chosen } == ticketLimit) {
            view.showConfirmationDialog()
        }
    }

    override fun confirmPurchase() {
        val reservation = Reservation(
            cinemaName = cinema.cinemaName,
            title = cinema.theater.movie.title,
            releaseDate = LocalDateTime.parse(dateTime, Formatter),
            runningTime = cinema.theater.movie.runningTime,
            synopsis = cinema.theater.movie.synopsis,
            seats = selectedSeats.mapNotNull { seats[it] }.toSet(),
        )
        movieRepository.saveReservation(reservation).onSuccess {
            view.navigateToPurchaseConfirmView(it)
        }.onFailure {
            Log.e("confirmPurchase", it.stackTraceToString())
            view.showError()
        }
    }

    private fun calculateAndUpdateTotalPrice() {
        totalPrice =
            selectedSeats.sumOf { seatId ->
                seats[seatId]?.price ?: 0
            }
        view.showPrice(totalPrice)
    }

    companion object {
        private val Formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    }
}
