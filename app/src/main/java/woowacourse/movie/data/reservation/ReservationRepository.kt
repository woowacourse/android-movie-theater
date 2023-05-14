package woowacourse.movie.data.reservation

import woowacourse.movie.ui.model.MovieTicketModel

class ReservationRepository(private val localDataSource: ReservationLocalDataSource) {
    fun insertReservation(ticket: MovieTicketModel) {
        localDataSource.insertReservation(ticket)
    }

    fun getReservations(): List<MovieTicketModel> = localDataSource.getReservations()
}
