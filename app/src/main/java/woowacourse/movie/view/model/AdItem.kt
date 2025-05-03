package woowacourse.movie.view.model

data class AdItem(
    override val name: String,
    val image: Int,
) : MainItem(ItemViewType.AD_ITEM)
