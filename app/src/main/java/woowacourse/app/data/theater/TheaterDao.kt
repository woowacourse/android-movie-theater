package woowacourse.app.data.theater

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_A
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_B
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_COLUMN_SIZE
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_MOVIE_IDS
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_ROW_SIZE
import woowacourse.app.data.CgvContract.Theater.TABLE_COLUMN_S
import woowacourse.app.data.CgvContract.Theater.TABLE_NAME
import woowacourse.app.data.CgvDbHelper
import woowacourse.domain.theater.SeatStructure

class TheaterDao(context: Context) : TheaterDataSource {
    private val cgvDb by lazy { CgvDbHelper(context).readableDatabase }

    override fun getTheaterEntities(): List<TheaterEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val cursor = makeCursor(null, null, orderBy)
        val theaters = readTheaters(cursor)
        cursor.close()
        return theaters
    }

    override fun getTheaterEntity(theaterId: Long): TheaterEntity? {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$theaterId")
        val cursor = makeCursor(selection, selectionArgs, null)
        val theater = readTheater(cursor)
        cursor.close()
        return theater
    }

    override fun addTheaterEntity(movieIds: List<Long>, seatStructure: SeatStructure): Long {
        val data = ContentValues()
        data.put(TABLE_COLUMN_MOVIE_IDS, movieIds.joinToString(","))
        data.put(TABLE_COLUMN_ROW_SIZE, seatStructure.rowSize)
        data.put(TABLE_COLUMN_COLUMN_SIZE, seatStructure.columnSize)
        data.put(TABLE_COLUMN_S, seatStructure.sRankRange.toRangeString())
        data.put(TABLE_COLUMN_A, seatStructure.aRankRange.toRangeString())
        data.put(TABLE_COLUMN_B, seatStructure.bRankRange.toRangeString())
        Log.d("극장", "TheaterDao의 AddTheaterEntity 들어옴")
        return cgvDb.insert(TABLE_NAME, null, data)
    }

    private fun List<IntRange>.toRangeString(): String {
        return this.joinToString(",") {
            val start = it.first.toString()
            val end = it.last.toString()
            "$start-$end"
        }
    }

    private fun readTheaters(cursor: Cursor): List<TheaterEntity> {
        val theaters = mutableListOf<TheaterEntity>()
        while (true) {
            val theater: TheaterEntity = readTheater(cursor) ?: break
            theaters.add(theater)
        }
        return theaters
    }

    private fun readTheater(cursor: Cursor): TheaterEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val movieIds = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_MOVIE_IDS))
        val rowSize = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ROW_SIZE))
        val columnSize = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_COLUMN_SIZE))
        val sRankRange = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_S))
        val aRankRange = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_A))
        val bRankRange = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_B))
        return TheaterEntity(id, movieIds, rowSize, columnSize, sRankRange, aRankRange, bRankRange)
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
                TABLE_COLUMN_MOVIE_IDS,
                TABLE_COLUMN_ROW_SIZE,
                TABLE_COLUMN_COLUMN_SIZE,
                TABLE_COLUMN_S,
                TABLE_COLUMN_A,
                TABLE_COLUMN_B,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }
}
