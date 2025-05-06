package woowacourse.movie.view.home.model

sealed interface FeedUiModel {
    data class MovieUiModel(
        val id: Int,
        val title: String,
        val imgName: String,
        val startDate: String,
        val endDate: String,
        val runningTime: Int,
    ) : FeedUiModel

    data class AdUiModel(
        val imgResource: String,
    ) : FeedUiModel
}
