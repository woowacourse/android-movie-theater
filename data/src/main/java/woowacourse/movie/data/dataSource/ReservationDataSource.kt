package woowacourse.movie.data.dataSource

import woowacourse.movie.domain.Reservation

class ReservationDataSource : DataSource<Reservation> {
    private val data: MutableList<Reservation> =
        LocalDatabase.movieDBHelper?.selectAllReservations()?.toMutableList() ?: mutableListOf()
    override val value: List<Reservation>
        get() = data

    override fun add(t: Reservation) {
        data.add(t)
        LocalDatabase.movieDBHelper?.insertReservation(
            LocalDatabase.movieDBHelper?.writableDatabase,
            t
        )
    }
}
