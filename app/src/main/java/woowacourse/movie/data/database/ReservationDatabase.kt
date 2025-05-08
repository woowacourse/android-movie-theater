package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.Reservation

@Database(entities = [Reservation::class], version = 1)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        fun getDatabase(context: Context): ReservationDatabase {
            return this.instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ReservationDatabase::class.java,
                        "reservation",
                    ).build()
                this.instance = instance
                instance
            }
        }
    }
}
