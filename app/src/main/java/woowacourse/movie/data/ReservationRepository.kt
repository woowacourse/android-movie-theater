package woowacourse.movie.data

import android.content.Context
import androidx.room.Room

class ReservationRepository private constructor(context: Context) {
    private val database: ReservationDatabase = Room.databaseBuilder (
        context.applicationContext,
        ReservationDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val reservationDao = database.reservationDao()

    fun getReservations(): List<Reservation> = reservationDao.getAll()

    fun getReservation(id: Long): Reservation? = reservationDao.getById(id)

    fun insert(reservation: Reservation) = reservationDao.insert(reservation)

    companion object {
        private const val DATABASE_NAME = "reservation"
        private var INSTANCE: ReservationRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ReservationRepository(context)
            }
        }
        fun get(): ReservationRepository {
            return INSTANCE ?:
            throw IllegalStateException("ReservationRepository must be initialized")
        }
    }
}
