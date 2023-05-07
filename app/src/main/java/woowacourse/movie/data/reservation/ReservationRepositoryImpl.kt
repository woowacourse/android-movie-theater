package woowacourse.movie.data.reservation

import android.content.Context
import woowacourse.movie.uimodel.MovieTicketModel

class ReservationRepositoryImpl(
    context: Context,
) : ReservationRepository {
    private val db = ReservationDBHelper(context)

    override fun getData(): List<MovieTicketModel> {
        return db.getAll()
    }

    override fun saveData(ticket: MovieTicketModel) {
        db.insert(ticket)
    }
}
