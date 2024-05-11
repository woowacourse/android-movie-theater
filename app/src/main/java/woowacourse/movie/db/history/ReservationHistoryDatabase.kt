package woowacourse.movie.db.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.movie.model.ticket.ReservationTicket

@Database(
    entities = [
        ReservationTicket::class,
    ],
    version = 2,
)
@TypeConverters(ReservationHistoryConverters::class)
abstract class ReservationHistoryDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationHistoryDao

    companion object {
        private var instance: ReservationHistoryDatabase? = null
        const val RESERVATION_TICKET_DB_NAME = "reservationTicket"
        private val migration_1_2 =
            object : Migration(1, 2) {
                override fun migrate(db: SupportSQLiteDatabase) {
                    db.execSQL("ALTER TABLE reservationTicket RENAME TO temp_reservationTicket")
                    db.execSQL(
                        "CREATE TABLE IF NOT EXISTS `reservationTicket` " +
                            "(ticketId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, movieId INTEGER NOT NULL, " +
                            "theaterId INTEGER NOT NULL, movieTitle TEXT NOT NULL, theaterName TEXT NOT NULL, " +
                            "seatSelection TEXT NOT NULL, screeningDateTime TEXT NOT NULL, amount INTEGER NOT NULL)",
                    )
                    db.execSQL(
                        "INSERT INTO reservationTicket " +
                            "(ticketId, movieId, theaterId, movieTitle, theaterName, seatSelection, screeningDateTime, amount) " +
                            "SELECT ticketId, movieId, theaterId, movieTitle, theaterName, seats, screeningDateTime, amount " +
                            "FROM temp_reservationTicket",
                    )
                    db.execSQL("DROP TABLE temp_reservationTicket")
                }
            }

        @Synchronized
        fun getInstance(context: Context): ReservationHistoryDatabase {
            return instance
                ?: synchronized(ReservationHistoryDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        ReservationHistoryDatabase::class.java,
                        RESERVATION_TICKET_DB_NAME,
                    ).addMigrations(migration_1_2).build()
                }
        }
    }
}
