package woowacourse.movie.entity

import woowacourse.movie.R
import woowacourse.movie.model.AdModel
import woowacourse.movie.repository.AdRepository

class Ads : AdRepository {
    private val items: List<AdModel> =
        List(1) { AdModel(R.drawable.ad, "https://woowacourse.github.io/") }

    override fun getAll(): List<AdModel> = items.toList()
}
