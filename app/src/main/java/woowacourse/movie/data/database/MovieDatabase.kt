package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.BookingDao
import woowacourse.movie.data.entity.BookingInfoEntity

@Database(entities = [BookingInfoEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao

    companion object {
        const val DATABASE_NAME = "MOVIE_DATABASE"
    }
}
