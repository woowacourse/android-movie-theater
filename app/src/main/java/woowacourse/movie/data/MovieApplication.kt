package woowacourse.movie.data

import android.app.Application
import android.util.Log
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

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate")
    }

    override fun onTerminate() {
        Log.d("TAG", "onTerminate")
        super.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("TAG", "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d("TAG", "onTrimMemory")
    }
}
