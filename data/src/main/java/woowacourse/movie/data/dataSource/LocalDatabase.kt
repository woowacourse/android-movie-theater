package woowacourse.movie.data.dataSource

import android.content.Context
import woowacourse.movie.data.database.MovieDao

object LocalDatabase {
    var movieDao: MovieDao? = null

    fun initWithContext(context: Context) {
        movieDao = MovieDao(context)
    }
}
