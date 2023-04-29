package woowacourse.movie.feature.main.itemModel

import woowacourse.movie.feature.main.ViewType

interface ItemModel {
    val viewType: ViewType
    val onClick: (position: Int) -> Unit
}
