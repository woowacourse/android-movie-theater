package woowacourse.app.data.theater

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.app.data.CgvContract.MovieTime.TABLE_COLUMN_MOVIE_ID
import woowacourse.app.data.CgvContract.MovieTime.TABLE_COLUMN_THEATER_ID
import woowacourse.app.data.CgvContract.MovieTime.TABLE_COLUMN_TIMES
import woowacourse.app.data.CgvContract.MovieTime.TABLE_NAME
import woowacourse.app.data.CgvDbHelper

class MovieTimeDao(context: Context) : MovieTimeDataSource {
    private val cgvDb by lazy { CgvDbHelper(context).readableDatabase }

    override fun getMovieTimeEntities(theaterId: Long): List<MovieTimeEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val selection = "$TABLE_COLUMN_THEATER_ID == ?"
        val selectionArgs = arrayOf("$theaterId")
        val cursor = makeCursor(selection, selectionArgs, orderBy)
        val movieTimes = readMovieTimes(cursor)
        cursor.close()
        return movieTimes
    }

    override fun getMovieTimeEntity(theaterId: Long, movieId: Long): MovieTimeEntity? {
        val selection = "$TABLE_COLUMN_THEATER_ID == ? AND $TABLE_COLUMN_MOVIE_ID == ?"
        val selectionArgs = arrayOf("$theaterId", "$movieId")
        val cursor = makeCursor(selection, selectionArgs, null)
        val movieTime = readMovieTime(cursor)
        cursor.close()
        return movieTime
    }

    override fun addMovieTimeEntity(theaterId: Long, movieId: Long, times: String) {
        val data = ContentValues()
        data.put(TABLE_COLUMN_THEATER_ID, theaterId)
        data.put(TABLE_COLUMN_MOVIE_ID, movieId)
        data.put(TABLE_COLUMN_TIMES, times)
        cgvDb.insert(TABLE_NAME, null, data)
    }

    private fun readMovieTimes(cursor: Cursor): List<MovieTimeEntity> {
        val movieTimes = mutableListOf<MovieTimeEntity>()
        while (true) {
            val movieTime: MovieTimeEntity = readMovieTime(cursor) ?: break
            movieTimes.add(movieTime)
        }
        return movieTimes
    }

    private fun readMovieTime(cursor: Cursor): MovieTimeEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val theaterId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_THEATER_ID))
        val movieId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_MOVIE_ID))
        val times = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TIMES))
        return MovieTimeEntity(id, theaterId, movieId, times)
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
                TABLE_COLUMN_THEATER_ID,
                TABLE_COLUMN_MOVIE_ID,
                TABLE_COLUMN_TIMES,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }
}
