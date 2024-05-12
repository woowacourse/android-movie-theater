package woowacourse.movie.data

import woowacourse.movie.model.Advertisement

object AdvertisementRepository {
    fun advertisements(): List<Advertisement> = List(10) { Advertisement() }
}
