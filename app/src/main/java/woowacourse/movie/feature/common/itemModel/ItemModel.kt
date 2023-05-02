package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.feature.common.ViewType

interface ItemModel {
    val viewType: ViewType
    val onClick: (position: Int) -> Unit
}
