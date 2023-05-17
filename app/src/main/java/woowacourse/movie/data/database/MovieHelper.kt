package woowacourse.movie.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.movie.data.model.MovieBookingEntity

class MovieHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STONE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun readBookingData(): List<MovieBookingEntity> {
        val db = this.readableDatabase

        val cursor: Cursor = db.query(
            MovieContract.Booking.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val bookings = mutableListOf<MovieBookingEntity>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(MovieContract.Booking.MOVIE_TITLE))
                val date = getString(getColumnIndexOrThrow(MovieContract.Booking.DATE))
                val time = getString(getColumnIndexOrThrow(MovieContract.Booking.TIME))
                val personCount =
                    getInt(getColumnIndexOrThrow(MovieContract.Booking.PERSON_COUNT))
                val seatNames = getString(getColumnIndexOrThrow(MovieContract.Booking.SEAT_NAMES))
                val totalPrice =
                    getInt(getColumnIndexOrThrow(MovieContract.Booking.TOTAL_PRICE))

                bookings.add(
                    MovieBookingEntity(
                        title,
                        date,
                        time,
                        personCount,
                        seatNames,
                        totalPrice,
                        id,
                    )
                )
            }
        }
        return bookings
    }

    fun writeMovie(booking: MovieBookingEntity) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(MovieContract.Booking.MOVIE_TITLE, booking.title)
            put(MovieContract.Booking.DATE, booking.date)
            put(MovieContract.Booking.TIME, booking.time)
            put(MovieContract.Booking.PERSON_COUNT, booking.personCount)
            put(MovieContract.Booking.SEAT_NAMES, booking.seatNames)
            put(MovieContract.Booking.TOTAL_PRICE, booking.totalPrice)
        }
        db?.insert(MovieContract.Booking.TABLE_NAME, null, values)
    }

    fun clearBoard() {
        val db = this.writableDatabase
        db.delete(MovieContract.Booking.TABLE_NAME, null, null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "booking.db"

        private const val SQL_CREATE_STONE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${MovieContract.Booking.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MovieContract.Booking.MOVIE_TITLE} TEXT," +
                    "${MovieContract.Booking.DATE} TEXT," +
                    "${MovieContract.Booking.TIME} TEXT," +
                    "${MovieContract.Booking.PERSON_COUNT} INTEGER," +
                    "${MovieContract.Booking.SEAT_NAMES} TEXT," +
                    "${MovieContract.Booking.TOTAL_PRICE} INTEGER)"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${MovieContract.Booking.TABLE_NAME}"
    }
}