package woowacourse.movie.ui.model

sealed interface MovieFeedUiModel {
    data class MovieItem(val movie: MovieUiModel) : MovieFeedUiModel

    data class AdvertisementItem(val imageId: Int) : MovieFeedUiModel
}
