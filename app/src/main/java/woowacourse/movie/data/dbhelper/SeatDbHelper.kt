package woowacourse.movie.data.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.data.model.SeatEntity

class SeatDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${SeatEntity.TABLE_NAME} (" +
                "  ${SeatEntity.RESERVATION_ID_COLUMN} INTEGER," +
                "  ${SeatEntity.ROW_COLUMN} INTEGER," +
                "  ${SeatEntity.COLUMN_COLUMN} INTEGER" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${SeatEntity.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "SEAT_DB"
    }
}
