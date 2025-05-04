package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.movie.ScreeningPeriod
import woowacourse.movie.presentation.common.extension.toDateTimeFormatter
import java.time.LocalDate

@Parcelize
data class ScreeningPeriodUiModel(
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Parcelable {
    fun formatedStartDate(formatPattern: String): String = startDate.format(formatPattern.toDateTimeFormatter())

    fun formatedEndDate(formatPattern: String): String = endDate.format(formatPattern.toDateTimeFormatter())
}

fun ScreeningPeriod.toUiModel() = ScreeningPeriodUiModel(startDate, endDate)

fun ScreeningPeriodUiModel.toDomain() = ScreeningPeriod(startDate, endDate)
