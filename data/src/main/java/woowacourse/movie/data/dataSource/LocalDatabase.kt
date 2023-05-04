package woowacourse.movie.data.dataSource

import android.content.Context
import woowacourse.movie.data.database.MovieDBHelper

object LocalDatabase {
    var movieDBHelper: MovieDBHelper? = null

    fun initWithContext(context: Context) {
        movieDBHelper = MovieDBHelper(context)
    }
}
