package woowacourse.movie.model

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import java.io.Serializable

fun MovieBookingSeatInfoUIModel.toDomain() = MovieBookingSeatInfo(movieBookingInfo.toDomain(), seats, totalPrice)
fun MovieBookingSeatInfo.toPresentation() = MovieBookingSeatInfoUIModel(movieBookingInfo.toPresentation(), seats, totalPrice)

data class MovieBookingSeatInfoUIModel(
    val movieBookingInfo: MovieBookingInfoUiModel,
    val seats: List<String>,
    val totalPrice: String,
) : Serializable {
    companion object {
        val dummyData = MovieBookingSeatInfoUIModel(
            MovieBookingInfoUiModel.dummyData,
            emptyList(),
            ""
        )
    }
}
