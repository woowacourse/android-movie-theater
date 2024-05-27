package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.source.ReservationTicketRoomDao

@Database(entities = [ReservationTicket::class], version = 2, exportSchema = false)
@TypeConverters(ReservationTicketConverters::class)
abstract class ReservationTicketDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationTicketRoomDao

    companion object {
        @Volatile
        private var instance: ReservationTicketDatabase? = null

        fun getDatabase(context: Context): ReservationTicketDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(context, ReservationTicketDatabase::class.java, "reservation_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build().also { instance = it }
            }
    }
}
