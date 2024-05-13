package woowacourse.movie.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationTicketEntity::class], version = 1)
@TypeConverters(ReservationTicketConverters::class)
abstract class ReservationTicketDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationTicketDao

    companion object {
        private var instance: ReservationTicketDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ReservationTicketDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                ReservationTicketDatabase::class.java,
                "reservation_ticket.db",
            ).build()
        }
    }
}
