package woowacourse.movie.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.model.BookingHistoryData

class BookingHistoryDBHelper(context: Context?) :
    SQLiteOpenHelper(context, BookHistoryContract.DATABASE_NAME, null, 1) {

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${BookHistoryContract.TABLE_NAME}(" +
                " ${BookHistoryContract.TABLE_COLUMN_TITLE} varchar(20), " +
                " ${BookHistoryContract.TABLE_COLUMN_DATE} varchar(10)," +
                " ${BookHistoryContract.TABLE_COLUMN_NUMBER_OF_PEOPLE} int," +
                " ${BookHistoryContract.TABLE_COLUMN_SEATS} varchar(100)," +
                " ${BookHistoryContract.TABLE_COLUMN_PRICE} varchar(10)" +
                ");"
        )
    }

    fun getData(): List<BookingHistoryData> {
        val cursor = db.rawQuery("SELECT * FROM ${BookHistoryContract.TABLE_NAME}", null)
        val data = mutableListOf<BookingHistoryData>()
        if (cursor.moveToFirst()) {
            do {
                data.add(getBookingHistoryData(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }

    private fun getBookingHistoryData(
        cursor: Cursor,
    ): BookingHistoryData {
        val title =
            cursor.getString(cursor.getColumnIndexOrThrow(BookHistoryContract.TABLE_COLUMN_TITLE))
        val date =
            cursor.getString(cursor.getColumnIndexOrThrow(BookHistoryContract.TABLE_COLUMN_DATE))
        val numberOfPeople =
            cursor.getInt(cursor.getColumnIndexOrThrow(BookHistoryContract.TABLE_COLUMN_NUMBER_OF_PEOPLE))
        val seats =
            cursor.getString(cursor.getColumnIndexOrThrow(BookHistoryContract.TABLE_COLUMN_SEATS))
        val price =
            cursor.getString(cursor.getColumnIndexOrThrow(BookHistoryContract.TABLE_COLUMN_PRICE))

        return BookingHistoryData(title, date, numberOfPeople, seats.split(","), price)
    }

    fun insertData(bookingHistoryData: BookingHistoryData) {
        val values = ContentValues().apply {
            put(BookHistoryContract.TABLE_COLUMN_TITLE, bookingHistoryData.title)
            put(BookHistoryContract.TABLE_COLUMN_DATE, bookingHistoryData.date)
            put(
                BookHistoryContract.TABLE_COLUMN_NUMBER_OF_PEOPLE,
                bookingHistoryData.numberOfPeople
            )
            put(BookHistoryContract.TABLE_COLUMN_SEATS, bookingHistoryData.seat.joinToString(","))
            put(BookHistoryContract.TABLE_COLUMN_PRICE, bookingHistoryData.price)
        }
        db.insert(BookHistoryContract.TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${BookHistoryContract.TABLE_NAME}")
        onCreate(db)
    }
}
