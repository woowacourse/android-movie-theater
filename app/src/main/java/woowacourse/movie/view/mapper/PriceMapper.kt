package woowacourse.movie.view.mapper

import woowacourse.movie.domain.price.Price
import java.text.DecimalFormat

fun Price.toUiModel(): String {
    return DecimalFormat("#,###").format(price)
}
