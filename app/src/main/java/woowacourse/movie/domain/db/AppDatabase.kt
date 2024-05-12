package woowacourse.movie.domain.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.ProvidedTypeConverter
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.google.gson.Gson
import woowacourse.movie.domain.db.reservationdb.ReservationDao
import woowacourse.movie.domain.db.reservationdb.ReservationEntity
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [ReservationEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = AppDatabase.ReservationAutoMigration::class,
        ),
    ],
)
@TypeConverters(value = [StringListTypeConverter::class, LocalDateTimeTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    @RenameColumn(
        fromColumnName = "movie_name",
        toColumnName = "movie_title",
        tableName = "reservations",
    )
    class ReservationAutoMigration : AutoMigrationSpec

    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database",
                        ).addTypeConverter(StringListTypeConverter(Gson()))
                            .addTypeConverter(LocalDateTimeTypeConverter(Gson()))
                            .build()
                }
            }
            return instance
        }
    }
}

@ProvidedTypeConverter
class StringListTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun listToJson(value: List<Seat>): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Seat> {
        return gson.fromJson(value, Array<Seat>::class.java).toList()
    }
}

@ProvidedTypeConverter
class LocalDateTimeTypeConverter(private val gson: Gson) {
    @TypeConverter
    fun localDateTimeToString(localDateTime: LocalDateTime): String = localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))

    @TypeConverter
    fun stringToLocalDateTime(string: String): LocalDateTime = LocalDateTime.parse(string, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
}
