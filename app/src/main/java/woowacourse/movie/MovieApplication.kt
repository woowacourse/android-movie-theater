package woowacourse.movie

import android.app.Application
import woowacourse.movie.model.data.AppDatabase

class MovieApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
}
