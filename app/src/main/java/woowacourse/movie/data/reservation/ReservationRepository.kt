package woowacourse.movie.data.reservation

import woowacourse.movie.uimodel.MovieTicketModel

interface ReservationRepository {
    fun getData(): List<MovieTicketModel>
    fun saveData(ticket: MovieTicketModel)
}
