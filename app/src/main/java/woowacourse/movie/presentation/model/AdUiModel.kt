package woowacourse.movie.presentation.model

data class AdUiModel(
    override val name: String,
    val image: Int,
) : MovieListItem(ItemViewType.AD_ITEM)
