package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookingInfoEntity::class], version = 1)
abstract class BookingInfoDatabase : RoomDatabase() {
    abstract fun BookingInfoDao(): BookingInfoDao

    companion object {
        @Volatile
        private var instance: BookingInfoDatabase? = null

        fun getDatabase(context: Context): BookingInfoDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        BookingInfoDatabase::class.java,
                        "booking_history_db",
                    ).build()
                    .also { instance = it }
            }
    }
}
