package woowacourse.movie.data.mock

import domain.Reservation

object MockReservationsFactory {

    fun makeReservations(): List<Reservation> {
        val movies = MockMoviesFactory.makeMovies()
        val tickets = MockTicketsFactory.makeTickets()
        return List(100) {
            movies.value[it].makeReservation(tickets)
        }
    }
}
