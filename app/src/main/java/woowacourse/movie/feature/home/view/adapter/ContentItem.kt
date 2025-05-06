package woowacourse.movie.feature.home.view.adapter

import woowacourse.movie.feature.model.AdvertisementUiModel
import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel

sealed class ContentItem(
    contentItemViewType: ContentItemViewType,
) {
    val viewType: ContentItemViewType = contentItemViewType
    abstract val id: Long

    data class Movie(
        override val id: Long,
        val value: MovieUiModel,
    ) : ContentItem(ContentItemViewType.MOVIE)

    data class Advertisement(
        override val id: Long,
        val value: AdvertisementUiModel,
    ) : ContentItem(ContentItemViewType.ADVERTISEMENT)

    companion object {
        fun from(content: ContentUiModel): ContentItem =
            when (content) {
                is MovieUiModel -> Movie(content.id, content)
                is AdvertisementUiModel -> Advertisement(content.id, content)
            }
    }
}
