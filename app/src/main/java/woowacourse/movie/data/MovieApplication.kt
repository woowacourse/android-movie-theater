package woowacourse.movie.data

import android.app.Application

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ReservationRepository.initialize(this)
    }
}
