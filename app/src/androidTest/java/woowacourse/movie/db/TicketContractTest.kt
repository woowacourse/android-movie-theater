package woowacourse.movie.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.createMovieTicketModel
import woowacourse.movie.data.db.DBHelper
import woowacourse.movie.data.db.TicketContract
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import java.time.LocalDateTime

class TicketContractTest {
    private lateinit var database: SQLiteDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dbHelper = DBHelper(context)
        database = dbHelper.writableDatabase
        dbHelper.onUpgrade(database, 1, 1)
    }

    @Test
    fun createRecord() {
        val ticket = createMovieTicketModel()
        val actual = TicketContract.createRecord(database, ticket)
        assertEquals(1, actual)
    }

    @Test
    fun readRecords() {
        val ticket = createMovieTicketModel()
        TicketContract.createRecord(database, ticket)

        val cursor = TicketContract.readRecords(database)
        cursor.moveToFirst()

        val actual = with(cursor) {
            createMovieTicketModel(
                title = getString(getColumnIndexOrThrow(TicketContract.COLUMN_TITLE)),
                time = TicketTimeModel(
                    LocalDateTime.parse(
                        getString(
                            getColumnIndexOrThrow(
                                TicketContract.COLUMN_DATE_TIME
                            )
                        )
                    )
                ),
                peopleCount = PeopleCountModel(getInt(getColumnIndexOrThrow(TicketContract.COLUMN_PEOPLE_COUNT))),
                price = PriceModel(getInt(getColumnIndexOrThrow(TicketContract.COLUMN_PRICE))),
                theaterName = getString(getColumnIndexOrThrow(TicketContract.COLUMN_THEATER_NAME)),
            )
        }
        assertEquals(ticket, actual)
    }
}
