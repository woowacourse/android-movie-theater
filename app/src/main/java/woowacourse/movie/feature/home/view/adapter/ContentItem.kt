package woowacourse.movie.feature.home.view.adapter

import woowacourse.movie.feature.model.AdvertisementUiModel
import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel

sealed class ContentItem(
    val viewType: ContentItemViewType,
) {
    abstract val id: Long

    data class Movie(
        val value: MovieUiModel,
    ) : ContentItem(ContentItemViewType.MOVIE) {
        override val id: Long get() = value.id
    }

    data class Advertisement(
        val value: AdvertisementUiModel,
    ) : ContentItem(ContentItemViewType.ADVERTISEMENT) {
        override val id: Long get() = value.id
    }

    companion object {
        fun from(content: ContentUiModel): ContentItem =
            when (content) {
                is MovieUiModel -> Movie(content)
                is AdvertisementUiModel -> Advertisement(content)
            }
    }
}
