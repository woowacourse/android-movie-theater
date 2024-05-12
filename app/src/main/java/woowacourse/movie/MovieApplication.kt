package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.AppDatabase

class MovieApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
}
