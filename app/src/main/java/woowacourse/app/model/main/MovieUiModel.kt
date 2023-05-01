package woowacourse.app.model.main

import androidx.annotation.DrawableRes
import woowacourse.app.model.main.MovieMapper.toDomainModel
import woowacourse.app.ui.main.adapter.MainViewType
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.Ticket
import java.time.LocalDate
import java.time.LocalDateTime

data class MovieUiModel(
    override val id: Long,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningDates: List<LocalDate>,
    val runningTime: Int,
    val description: String,
    @DrawableRes val thumbnail: Int,
    @DrawableRes val poster: Int,
) : MainData() {
    override val mainViewType: MainViewType = MainViewType.CONTENT

    fun reserve(dateTime: LocalDateTime, seat: Seat): Ticket {
        return this.toDomainModel().reserve(dateTime, seat)
    }
}
