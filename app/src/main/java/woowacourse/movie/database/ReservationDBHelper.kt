package woowacourse.movie.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReservationDBHelper(context: Context) : SQLiteOpenHelper(context, "reservation.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${ReservationTableNames.TABLE_NAME} (" +
                "  ${ReservationTableNames.TABLE_COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  ${ReservationTableNames.TABLE_COLUMN_DATE} varchar(255) not null," +
                "  ${ReservationTableNames.TABLE_COLUMN_COUNT} int not null," +
                "  ${ReservationTableNames.TABLE_COLUMN_SEATS} varchar(255)," +
                "  ${ReservationTableNames.TABLE_COLUMN_PRICE} int not null" +
                ");"
        )

        db?.execSQL(
            "CREATE TABLE ${MovieTableNames.TABLE_NAME} (" +
                "  ${MovieTableNames.TABLE_COLUMN_POSTER} int not null," +
                "  ${MovieTableNames.TABLE_COLUMN_TITLE} varchar(255) not null," +
                "  ${MovieTableNames.TABLE_COLUMN_START_DATE} varchar(255)," +
                "  ${MovieTableNames.TABLE_COLUMN_END_DATE} varchar(255)," +
                "  ${MovieTableNames.TABLE_COLUMN_RUNNING_TIME} int not null," +
                "  ${MovieTableNames.TABLE_COLUMN_DESCRIPTION} varchar(255) not null," +
                "  ${MovieTableNames.TABLE_COLUMN_RESERVATION_ID} INTEGER not null," +
                "  FOREIGN KEY(${MovieTableNames.TABLE_COLUMN_RESERVATION_ID}) REFERENCES ${ReservationTableNames.TABLE_NAME}(${ReservationTableNames.TABLE_COLUMN_ID})" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${ReservationTableNames.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${MovieTableNames.TABLE_NAME}")
        onCreate(db)
    }
}
