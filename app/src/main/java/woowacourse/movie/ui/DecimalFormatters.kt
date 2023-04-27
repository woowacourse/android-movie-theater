package woowacourse.movie.ui

import java.text.DecimalFormat
import woowacourse.movie.model.MoneyState

object DecimalFormatters {
    fun convertToMoneyFormat(moneyState: MoneyState): String = DECIMAL_FORMATTER.format(
        moneyState.price
    )

    private val DECIMAL_FORMATTER = DecimalFormat("#,###")
}
