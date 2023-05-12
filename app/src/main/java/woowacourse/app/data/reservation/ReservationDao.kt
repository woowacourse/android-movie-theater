package woowacourse.app.data.reservation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.app.data.CgvContract.Reservation.TABLE_COLUMN_BOOKED_DATE_TIME
import woowacourse.app.data.CgvContract.Reservation.TABLE_COLUMN_MOVIE_ID
import woowacourse.app.data.CgvContract.Reservation.TABLE_COLUMN_PAYMENT_TYPE
import woowacourse.app.data.CgvContract.Reservation.TABLE_NAME
import woowacourse.app.data.CgvDbHelper
import java.time.LocalDateTime

class ReservationDao(context: Context) : ReservationDataSource {
    private val cgvDb by lazy { CgvDbHelper.getInstance(context).readableDatabase }

    override fun getReservationEntities(): List<ReservationEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val cursor = makeCursor(null, null, orderBy)
        val reservations = readReservations(cursor)
        cursor.close()
        return reservations
    }

    override fun getReservationEntity(id: Long): ReservationEntity? {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$id")
        val cursor = makeCursor(selection, selectionArgs, null)
        val reservation = readReservation(cursor)
        cursor.close()
        return reservation
    }

    override fun makeReservation(
        movieId: Long,
        bookedDateTime: LocalDateTime,
        paymentType: Int,
    ): ReservationEntity {
        val data = ContentValues()
        data.put(TABLE_COLUMN_MOVIE_ID, movieId)
        data.put(TABLE_COLUMN_BOOKED_DATE_TIME, bookedDateTime.toString())
        data.put(TABLE_COLUMN_PAYMENT_TYPE, paymentType)
        val reservationId = cgvDb.insert(TABLE_NAME, null, data)
        return getReservationEntity(reservationId)!!
    }

    private fun readReservations(cursor: Cursor): List<ReservationEntity> {
        val reservations = mutableListOf<ReservationEntity>()
        while (true) {
            val reservation: ReservationEntity = readReservation(cursor) ?: break
            reservations.add(reservation)
        }
        return reservations
    }

    private fun readReservation(cursor: Cursor): ReservationEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val movieId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_MOVIE_ID))
        val bookedDateTime = LocalDateTime.parse(
            cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_BOOKED_DATE_TIME)),
        )
        val paymentType = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PAYMENT_TYPE))
        return ReservationEntity(id, movieId, bookedDateTime, paymentType)
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
                TABLE_COLUMN_MOVIE_ID,
                TABLE_COLUMN_BOOKED_DATE_TIME,
                TABLE_COLUMN_PAYMENT_TYPE,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }
}
