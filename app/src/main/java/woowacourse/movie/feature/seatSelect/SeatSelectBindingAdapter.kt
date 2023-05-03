package woowacourse.movie.feature.seatSelect

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.model.MoneyState
import woowacourse.movie.util.DecimalFormatters

@BindingAdapter("discountedMoney")
fun setDiscountedMoney(view: TextView, money: MoneyState) {
    view.text = view.context.getString(
        R.string.discount_money,
        DecimalFormatters.convertToMoneyFormat(money)
    )
}
