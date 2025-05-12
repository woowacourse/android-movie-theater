package woowacourse.movie.data

import android.app.Application
import androidx.room.Room

class MovieApplication : Application() {
    private var _database: ReservationDatabase? = null
    val database: ReservationDatabase
        get() {
            if (_database == null) {
                _database =
                    Room.databaseBuilder(
                        applicationContext,
                        ReservationDatabase::class.java,
                        "reservation",
                    ).build()
            }
            return _database!!
        }
}
