package woowacourse.app.data.movie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.app.data.CgvContract.Movie.TABLE_COLUMN_DESCRIPTION
import woowacourse.app.data.CgvContract.Movie.TABLE_COLUMN_END_DATE
import woowacourse.app.data.CgvContract.Movie.TABLE_COLUMN_RUNNING_TIME
import woowacourse.app.data.CgvContract.Movie.TABLE_COLUMN_START_DATE
import woowacourse.app.data.CgvContract.Movie.TABLE_COLUMN_TITLE
import woowacourse.app.data.CgvContract.Movie.TABLE_NAME
import woowacourse.app.data.CgvDbHelper
import java.time.LocalDate

class MovieDao(context: Context) : MovieDataSource {
    private val cgvDb by lazy { CgvDbHelper.getInstance(context).readableDatabase }

    override fun getMovieEntities(): List<MovieEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val cursor = makeCursor(null, null, orderBy)
        val movies = readMovies(cursor)
        cursor.close()
        return movies
    }

    override fun getMovieEntity(movieId: Long): MovieEntity? {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$movieId")
        val cursor = makeCursor(selection, selectionArgs, null)
        val movie = readMovie(cursor)
        cursor.close()
        return movie
    }

    override fun addMovieEntity(
        title: String,
        startDate: LocalDate,
        endDate: LocalDate,
        runningTime: Int,
        description: String,
    ): MovieEntity {
        val data = ContentValues()
        data.put(TABLE_COLUMN_TITLE, title)
        data.put(TABLE_COLUMN_START_DATE, startDate.toString())
        data.put(TABLE_COLUMN_END_DATE, endDate.toString())
        data.put(TABLE_COLUMN_RUNNING_TIME, runningTime)
        data.put(TABLE_COLUMN_DESCRIPTION, description)
        val movieId = cgvDb.insert(TABLE_NAME, null, data)
        return getMovieEntity(movieId)!!
    }

    private fun readMovies(cursor: Cursor): List<MovieEntity> {
        val movies = mutableListOf<MovieEntity>()
        while (true) {
            val movie: MovieEntity = readMovie(cursor) ?: break
            movies.add(movie)
        }
        return movies
    }

    private fun readMovie(cursor: Cursor): MovieEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TITLE))
        val startDate =
            LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_START_DATE)))
        val endDate =
            LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_END_DATE)))
        val runningTime = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_RUNNING_TIME))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_DESCRIPTION))
        return MovieEntity(id, title, startDate, endDate, runningTime, description)
    }

    private fun makeCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        orderBy: String?,
    ): Cursor {
        return cgvDb.query(
            TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                TABLE_COLUMN_TITLE,
                TABLE_COLUMN_START_DATE,
                TABLE_COLUMN_END_DATE,
                TABLE_COLUMN_RUNNING_TIME,
                TABLE_COLUMN_DESCRIPTION,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }

    fun closeDb() {
        cgvDb.close()
    }
}
