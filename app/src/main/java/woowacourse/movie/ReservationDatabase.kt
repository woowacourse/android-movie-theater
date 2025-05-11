package woowacourse.movie

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
        fun create(applicationContext: Context): ReservationDatabase =
            Room
                .databaseBuilder(
                    applicationContext,
                    ReservationDatabase::class.java,
                    NAME_DATABASE,
                ).build()

        private const val NAME_DATABASE = "reservation database"
    }
}
