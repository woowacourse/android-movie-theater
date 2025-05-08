package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationEntity::class], version = 1)
@TypeConverters(ReservationConverters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        private const val DATABASE_NAME = "reservation"
        private var instance: ReservationDatabase? = null

        fun getInstance(context: Context): ReservationDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context,
                        ReservationDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
            }
    }
}
