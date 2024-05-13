package woowacourse.movie.seat

import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.theater.Seat
import kotlin.concurrent.thread

class TheaterSeatPresenter(
    private val view: TheaterSeatContract.View,
    private val database: AppDatabase,
    private val screeningDate: String,
    private val ticketLimit: Int,
    val cinema: Cinema,
) :
    TheaterSeatContract.Presenter {
    private val seats: MutableMap<String, Seat> = mutableMapOf()
    var selectedSeats = mutableListOf<String>()
        private set
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
        if (seatId in selectedSeats) {
            selectedSeats.remove(seatId)
        } else {
            if (selectedSeats.size >= ticketLimit) return
            selectedSeats.add(seatId)
        }
        updateSeatBackground(seatId)
        calculateAndUpdateTotalPrice()
    }

    private fun updateSeatBackground(seatId: String) {
        if (seatId in selectedSeats) {
            view.setSeatBackground(seatId, "#FF0000")
        } else {
            view.setSeatBackground(seatId, "#FFFFFF")
        }
    }

    override fun showConfirmationDialog(
        title: String,
        message: String,
        positiveLabel: String,
        onPositiveButtonClicked: () -> Unit,
        negativeLabel: String,
        onNegativeButtonClicked: () -> Unit,
    ) {
        if (seats.values.any { it.chosen }) {
            view.showConfirmationDialog(
                title,
                message,
                positiveLabel,
                onPositiveButtonClicked,
                negativeLabel,
                onNegativeButtonClicked,
            )
        }
    }

    private fun calculateAndUpdateTotalPrice() {
        totalPrice =
            selectedSeats.sumOf { seatId ->
                seats[seatId]?.price ?: 0
            }
        view.showPrice(totalPrice)
    }

    override fun saveTicketToDatabase(
        movieStartTime: Long,
        cinema: Cinema,
    ) {
        val ticket =
            Ticket(
                screeningDate = screeningDate,
                seatNumbers = selectedSeats.joinToString(","),
                cinemaName = cinema.cinemaName,
                movieTitle = cinema.theater.movie.title.toString(),
                runningTime = cinema.theater.movie.runningTime.toString(),
                ticketPrice = totalPrice,
            )
        var ticketId = -1
        thread {
            ticketId = database.ticketDao().insertTicket(ticket).toInt()
            view.makeNotify(movieStartTime, cinema, ticketId)
        }.join()
        view.navigateToPurchase(ticketId)
    }
}
