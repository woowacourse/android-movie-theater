package woowacourse.movie.data.reservation

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.movie.data.reservation.ReservationContract.ReservationEntry
import woowacourse.movie.data.reservation.ReservationContract.ReservationSeatsEntry

class ReservationDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_RESERVATION_SEATS_ENTRIES)
        db?.execSQL(SQL_CREATE_RESERVATION_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_RESERVATION_SEATS_ENTRIES)
        db?.execSQL(SQL_DELETE_RESERVATION_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "Reservation.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_RESERVATION_ENTRIES =
            "CREATE TABLE ${ReservationEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${ReservationEntry.COLUMN_NAME_MOVIE_TITLE} TEXT," +
                    "${ReservationEntry.COLUMN_NAME_MOVIE_RUNNING_TIME} INTEGER," +
                    "${ReservationEntry.COLUMN_NAME_MOVIE_SUMMARY} TEXT," +
                    "${ReservationEntry.COLUMN_NAME_SCREENING_DATE_TIME} TEXT," +
                    "${ReservationEntry.COLUMN_NAME_THEATER_ID} INTEGER)"

        private const val SQL_CREATE_RESERVATION_SEATS_ENTRIES =
            "CREATE TABLE ${ReservationSeatsEntry.TABLE_NAME} (" +
                    "${ReservationSeatsEntry.COLUMN_NAME_RESERVATION_ID} INTEGER," +
                    "${ReservationSeatsEntry.COLUMN_NAME_ROW} INTEGER," +
                    "${ReservationSeatsEntry.COLUMN_NAME_COLUMN} INTEGER, " +
                    "PRIMARY KEY(${ReservationSeatsEntry.COLUMN_NAME_RESERVATION_ID}, ${ReservationSeatsEntry.COLUMN_NAME_ROW}, ${ReservationSeatsEntry.COLUMN_NAME_COLUMN}))"

        private const val SQL_DELETE_RESERVATION_ENTRIES =
            "DROP TABLE IF EXISTS ${ReservationEntry.TABLE_NAME}"

        private const val SQL_DELETE_RESERVATION_SEATS_ENTRIES =
            "DROP TABLE IF EXISTS ${ReservationSeatsEntry.TABLE_NAME}"

        private lateinit var dbHelper: ReservationDbHelper

        private lateinit var db: SQLiteDatabase

        private fun getInstance(context: Context): ReservationDbHelper {
            if (::dbHelper.isInitialized.not()) {
                dbHelper = ReservationDbHelper(context)
            }
            return dbHelper
        }

        fun getDbInstance(context: Context): SQLiteDatabase {
            if (::db.isInitialized.not()) {
                db = getInstance(context).writableDatabase
            }
            return db
        }
    }
}