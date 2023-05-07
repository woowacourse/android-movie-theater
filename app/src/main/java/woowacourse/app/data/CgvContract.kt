package woowacourse.app.data

import android.provider.BaseColumns

object CgvContract : BaseColumns {

    object Seat : BaseColumns {
        const val TABLE_NAME = "seat"
        const val TABLE_COLUMN_RESERVATION_ID = "reservationId"
        const val TABLE_COLUMN_RANK = "rank"
        const val TABLE_COLUMN_ROW = "row"
        const val TABLE_COLUMN_COLUMN = "column"

        const val CREATE_SEAT_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_RESERVATION_ID INTEGER," +
            "  $TABLE_COLUMN_RANK TEXT," +
            "  $TABLE_COLUMN_ROW INTEGER," +
            "  $TABLE_COLUMN_COLUMN INTEGER," +
            "  FOREIGN KEY($TABLE_COLUMN_RESERVATION_ID) REFERENCES ${Reservation.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");"
        const val DELETE_SEAT_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Reservation : BaseColumns {
        const val TABLE_NAME = "reservation"
        const val TABLE_COLUMN_MOVIE_ID = "movieId"
        const val TABLE_COLUMN_BOOKED_DATE_TIME = "bookedDateTime"
        const val TABLE_COLUMN_PAYMENT_TYPE = "paymentType"

        const val CREATE_RESERVATION_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_MOVIE_ID TEXT," +
            "  $TABLE_COLUMN_BOOKED_DATE_TIME INTEGER," +
            "  $TABLE_COLUMN_PAYMENT_TYPE INTEGER," +
            "  FOREIGN KEY($TABLE_COLUMN_MOVIE_ID) REFERENCES ${Movie.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");"
        const val DELETE_RESERVATION_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Movie : BaseColumns {
        const val TABLE_NAME = "movie"
        const val TABLE_COLUMN_TITLE = "title"
        const val TABLE_COLUMN_START_DATE = "startDate"
        const val TABLE_COLUMN_END_DATE = "endDate"
        const val TABLE_COLUMN_RUNNING_TIME = "runningTime"
        const val TABLE_COLUMN_DESCRIPTION = "description"

        const val CREATE_MOVIE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_TITLE TEXT," +
            "  $TABLE_COLUMN_START_DATE TEXT," +
            "  $TABLE_COLUMN_END_DATE TEXT," +
            "  $TABLE_COLUMN_RUNNING_TIME INTEGER," +
            "  $TABLE_COLUMN_DESCRIPTION TEXT" +
            ");"
        const val DELETE_MOVIE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Advertisement : BaseColumns {
        const val TABLE_NAME = "advertisement"
        const val TABLE_COLUMN_LINK = "link"

        const val CREATE_ADVERTISEMENT_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_LINK TEXT" +
            ");"
        const val DELETE_ADVERTISEMENT_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
