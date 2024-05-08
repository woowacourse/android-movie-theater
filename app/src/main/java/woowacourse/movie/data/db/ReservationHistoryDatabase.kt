package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReservationHistoryEntity::class,
    ],
    version = 1,
)
abstract class ReservationHistoryDatabase : RoomDatabase() {
    abstract fun reservationHistoryDao(): ReservationHistoryDAO

    companion object {
        @Volatile
        private var instance: ReservationHistoryDatabase? = null

        fun getInstance(context: Context): ReservationHistoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ReservationHistoryDatabase::class.java,
                    "reservation_database",
                ).build().also { instance = it }
            }
        }
    }
}
