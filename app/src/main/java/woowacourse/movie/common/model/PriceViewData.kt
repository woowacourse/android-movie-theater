package woowacourse.movie.common.model

import java.io.Serializable

data class PriceViewData(val value: Int = 0) : Serializable {
    companion object {
        const val PRICE_EXTRA_NAME = "price"
    }
}
