package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import java.time.LocalDate

class ScreeningPeriodConverter {
    @TypeConverter
    fun fromScreeningPeriod(period: ScreeningPeriod?): String? =
        period?.let {
            "${it.startDate}~${it.endDate}" // 예: "2025-05-01~2025-05-30"
        }

    @TypeConverter
    fun toScreeningPeriod(data: String?): ScreeningPeriod? {
        return data?.split("~")?.let {
            if (it.size != 2) return null
            val start = LocalDate.parse(it[0])
            val end = LocalDate.parse(it[1])
            ScreeningPeriod(start, end)
        }
    }
}
