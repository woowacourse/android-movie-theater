package woowacourse.movie.view.mapper

import woowacourse.movie.domain.price.Price

fun Price.toUiModel(): Int {
    return price
}
