package woowacourse.movie.data.local.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converter {
    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? =
        value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime?): Long? = dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun fromPurchaseType(value: String?): PurchaseType? = value?.let { PurchaseType.valueOf(it) }

    @TypeConverter
    fun toPurchaseType(purchaseType: PurchaseType?): String? = purchaseType?.name

    @TypeConverter
    fun fromSeatSet(value: String?): Set<Seat> {
        if (value.isNullOrEmpty()) return emptySet()
        return value.split(";").mapNotNull { pair ->
            val parts = pair.split(",")
            if (parts.size == 2) {
                val row = parts[0].toIntOrNull()
                val col = parts[1].toIntOrNull()
                if (row != null && col != null) Seat.invoke(row, col) else null
            } else {
                null
            }
        }.toSet()
    }

    @TypeConverter
    fun toSeatSet(seats: Set<Seat>?): String? {
        return seats?.joinToString(";") { "${it.row.value},${it.column.value}" }
    }
}
