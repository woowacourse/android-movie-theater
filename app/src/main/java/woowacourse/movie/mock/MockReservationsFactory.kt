package woowacourse.movie.mock

import domain.Reservation

object MockReservationsFactory {

    fun makeReservations(): List<Reservation> {
        val movies = MockMoviesFactory.makeMovies()
        val tickets = MockTicketsFactory.makeTickets()
        return List(100) { movies.value[0].makeReservation(tickets) }
    }
}
