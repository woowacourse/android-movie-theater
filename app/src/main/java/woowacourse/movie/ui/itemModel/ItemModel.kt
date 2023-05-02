package woowacourse.movie.ui.itemModel

interface ItemModel {
    val viewType: ViewType
    val onClick: (position: Int) -> Unit
}
