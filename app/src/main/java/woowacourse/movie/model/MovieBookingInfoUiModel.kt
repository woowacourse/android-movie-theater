package woowacourse.movie.model

import com.woowacourse.domain.movie.MovieBookingInfo
import java.io.Serializable

fun MovieBookingInfoUiModel.toDomain() = MovieBookingInfo(movieInfo.toDomain(), date, time, ticketCount)
fun MovieBookingInfo.toPresentation() = MovieBookingInfoUiModel(movieInfo.toPresentation(), date, time, ticketCount)

data class MovieBookingInfoUiModel(
    val movieInfo: MovieUIModel,
    val date: String,
    val time: String,
    val ticketCount: Int
) : Serializable {

    fun formatBookingTime(): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate $time"
    }

    fun formatBookHistoryDate(): String {
        val formattedDate: String = date.split("-").joinToString(".")
        return "$formattedDate | $time"
    }

    companion object {
        val dummyData = MovieBookingInfoUiModel(
            MovieUIModel.dummyData,
            "",
            "",
            0
        )
    }
}
