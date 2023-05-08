package woowacourse.app.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.app.data.CgvContract.Advertisement.CREATE_ADVERTISEMENT_TABLE
import woowacourse.app.data.CgvContract.Advertisement.DELETE_ADVERTISEMENT_TABLE
import woowacourse.app.data.CgvContract.Movie.CREATE_MOVIE_TABLE
import woowacourse.app.data.CgvContract.Movie.DELETE_MOVIE_TABLE
import woowacourse.app.data.CgvContract.MovieTime.CREATE_MOVIE_TIME_TABLE
import woowacourse.app.data.CgvContract.MovieTime.DELETE_MOVIE_TIME_TABLE
import woowacourse.app.data.CgvContract.Reservation.CREATE_RESERVATION_TABLE
import woowacourse.app.data.CgvContract.Reservation.DELETE_RESERVATION_TABLE
import woowacourse.app.data.CgvContract.Seat.CREATE_SEAT_TABLE
import woowacourse.app.data.CgvContract.Seat.DELETE_SEAT_TABLE
import woowacourse.app.data.CgvContract.Theater.CREATE_THEATER_TABLE
import woowacourse.app.data.CgvContract.Theater.DELETE_THEATER_TABLE

class CgvDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(PERMIT_FOREIGN_KEY)
        db.execSQL(CREATE_ADVERTISEMENT_TABLE)
        db.execSQL(CREATE_MOVIE_TABLE)
        db.execSQL(CREATE_RESERVATION_TABLE)
        db.execSQL(CREATE_SEAT_TABLE)
        db.execSQL(CREATE_THEATER_TABLE)
        db.execSQL(CREATE_MOVIE_TIME_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_ADVERTISEMENT_TABLE)
        db.execSQL(DELETE_MOVIE_TABLE)
        db.execSQL(DELETE_RESERVATION_TABLE)
        db.execSQL(DELETE_SEAT_TABLE)
        db.execSQL(DELETE_THEATER_TABLE)
        db.execSQL(DELETE_MOVIE_TIME_TABLE)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "cgv database"
        private const val PERMIT_FOREIGN_KEY = "PRAGMA foreign_keys=ON"
    }
}
