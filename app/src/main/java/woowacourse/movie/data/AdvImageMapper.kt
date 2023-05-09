package woowacourse.movie.data

import androidx.annotation.DrawableRes
import woowacourse.movie.R

object AdvImageMapper {
    fun mapper(id: Int): Int {
        return AdvPosterType.from(id).imgRes
    }

    private enum class AdvPosterType(val id: Int, @DrawableRes val imgRes: Int) {
        WOOTECO_ADV(0, R.drawable.adv_wooteco),
        EMPTY_ADV(-1, R.drawable.empty_image);

        companion object {
            fun from(id: Int): AdvPosterType {
                return values().find { it.id == id } ?: EMPTY_ADV
            }
        }
    }
}
