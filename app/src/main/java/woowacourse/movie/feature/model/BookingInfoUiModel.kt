package woowacourse.movie.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Theater

@Parcelize
data class BookingInfoUiModel(
    val movie: MovieUiModel = MovieUiModel(),
    val theater: Theater = Theater(),
    val date: MovieDateUiModel = MovieDateUiModel(),
    val movieTime: MovieTimeUiModel = MovieTimeUiModel(),
    val ticketCount: Int = 1,
    val totalPrice: Int = 0,
    val selectedSeats: Set<MovieSeatUiModel> = setOf<MovieSeatUiModel>(),
) : Parcelable
