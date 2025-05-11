package woowacourse.movie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationInfoEntity::class], version = 1)
@TypeConverters(ReservationInfoConverters::class)
abstract class ReservationInfoDatabase : RoomDatabase() {
    abstract fun reservationInfoDao(): ReservationInfoDao

    companion object {
        private const val DATABASE_NAME = "reservationInfo"
        private var instance: ReservationInfoDatabase? = null

        fun getInstance(context: Context): ReservationInfoDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        ReservationInfoDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
            }
    }
}
