package woowacourse.movie.domain.mock

import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.domain.advertismentPolicy.MovieAdvertisementPolicy

object AdvertisementPolicyMock {
    fun createAdvertisementPolicy(): AdvertisementPolicy = MovieAdvertisementPolicy(3, 1)
}
