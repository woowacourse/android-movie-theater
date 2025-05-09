package woowacourse.movie

import android.app.Application
import androidx.room.Room
import woowacourse.movie.data.MovieTheaterDatabase

class MovieTheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MovieTheaterDatabase.db =
            Room.databaseBuilder(
                applicationContext,
                MovieTheaterDatabase::class.java,
                "movie_theater_db",
            )
                // 수업 시간에 동시성, 병렬성을 배우지 않았기 때문에 일단 메인 스레드에서 쿼리를 허용하겠습니다
                .allowMainThreadQueries()
                .build()
    }
}
