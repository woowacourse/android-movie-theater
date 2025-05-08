package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReservationEntity::class,
    ],
    version = 1,
)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        fun getInstance(context: Context): ReservationDatabase =
            instance ?: Room
                .databaseBuilder(
                    context.applicationContext,
                    ReservationDatabase::class.java,
                    "reservations",
                ).build()
                .also { instance = it }
    }
}
