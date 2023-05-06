package woowacourse.movie.data.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.data.model.ReservationEntity

class ReservationDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ReservationEntity.TABLE_NAME} (" +
                "  ${ReservationEntity.ID_COLUMN} INTEGER primary key autoincrement," +
                "  ${ReservationEntity.THEATER_NAME_COLUMN} varchar(50)," +
                "  ${ReservationEntity.MOVIE_ID_COLUMN} INTEGER," +
                "  ${ReservationEntity.SCREENING_DATETIME_COLUMN} varchar(50)," +
                "  ${ReservationEntity.FEE_COLUMN} INTEGER" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ReservationEntity.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "RESERVATION_DB"
    }
}
