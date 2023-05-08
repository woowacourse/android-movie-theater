package woowacourse.movie.entity

import woowacourse.movie.R
import woowacourse.movie.model.AdModel

class Ads {
    private val items: List<AdModel> =
        List(1) { AdModel(R.drawable.ad, "https://woowacourse.github.io/") }

    fun getAll(): List<AdModel> = items.toList()
}
