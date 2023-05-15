package woowacourse.app.presentation.model.seat

import androidx.annotation.DrawableRes
import woowacourse.movie.R

enum class SeatRankColor(@DrawableRes val colorId: Int) {
    S(R.color.green_400),
    A(R.color.blue_500),
    B(R.color.purple_400),
}
