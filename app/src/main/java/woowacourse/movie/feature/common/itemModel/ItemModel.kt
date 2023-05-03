package woowacourse.movie.feature.common.itemModel

interface ItemModel {
    val viewType: ViewType
    val onClick: (position: Int) -> Unit
}
