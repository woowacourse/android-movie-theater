package woowacourse.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ReservationData::class], version = 1)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        private var instance: ReservationDatabase? = null
        // To use custom-getter, context injection is needed, but don't know where to inject.

        /**
         * This function returns ReservationDatabase instance.
         * As database it should exist only itself, if there is no instance this function will make an instance of database.
         * And if there is any instance already, then it'll return an instance which was already made.
         *
         * @param context To access database file from global area of application, it needs context. You can use "this" keyword, nevertheless this function will use applicationContext.
         * @return ReservationDatabase instance. As it is singleton object, it is same instance whenever you call this function.
         */
        @Synchronized
        fun getInstance(context: Context): ReservationDatabase? {
            if (instance == null) {
                synchronized(ReservationDatabase::class) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            ReservationDatabase::class.java,
                            "reservation-database",
                        ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}
