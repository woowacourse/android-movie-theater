package woowacourse.movie.presenter.reservationDetails

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.model.ticket.MovieTicket

@Database(
    entities = [
        MovieTicket::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: ReservationDatabase? = null

        fun getDatabase(context: Context): ReservationDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ReservationDatabase::class.java,
                            "reservation-database",
                        ).build()
                INSTANCE = instance
                instance
            }
    }
}
