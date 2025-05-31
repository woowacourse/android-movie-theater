import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.TicketBundleEntity
import woowacourse.movie.data.entity.TicketEntity

@Database(
    entities = [TicketBundleEntity::class, TicketEntity::class],
    version = 2,
)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: ReservationDatabase? = null

        fun getInstance(context: Context): ReservationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        ReservationDatabase::class.java,
                        "reservation.db",
                    ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
