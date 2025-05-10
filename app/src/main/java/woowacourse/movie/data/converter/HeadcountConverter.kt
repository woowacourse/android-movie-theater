package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.movie.Headcount

class HeadcountConverter {
    @TypeConverter
    fun fromHeadcount(headcount: Headcount?): Int? = headcount?.count

    @TypeConverter
    fun toHeadcount(data: Int?): Headcount? = data?.let { Headcount(it) }
}
