package woowacourse.movie.data.entity

import woowacourse.movie.R
import woowacourse.movie.ui.model.AdModel

class Ads {
    private val items: List<AdModel> =
        List(2) { AdModel(R.drawable.ad, "https://woowacourse.github.io/") }

    fun getAll(): List<AdModel> = items.toList()
}
