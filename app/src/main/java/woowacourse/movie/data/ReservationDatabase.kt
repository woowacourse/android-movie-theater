package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reservation::class], version = 2)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        fun getInstance(context: Context): ReservationDatabase {
            return instance ?: synchronized(this) {
                val newInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ReservationDatabase::class.java,
                        "reservationDatabase",
                    ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}
