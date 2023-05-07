package woowacourse.movie.data

import androidx.annotation.DrawableRes
import woowacourse.movie.R

object AdvImageMapper {
    fun mapper(advId: Int): Int {
        return AdvPosterType.from(advId).imgRes
    }

    private enum class AdvPosterType(val advId: Int, @DrawableRes val imgRes: Int) {
        WOOTECO_ADV(0, R.drawable.adv_wooteco),
        EMPTY_ADV(-1, R.drawable.empty_image);

        companion object {
            fun from(advId: Int): AdvPosterType {
                return values().find { it.advId == advId } ?: EMPTY_ADV
            }
        }
    }
}
