package woowacourse.movie.domain

import woowacourse.movie.domain.repository.AdvertisementRepository

class AdvertisementProvider {
    private val advertisementRepository: AdvertisementRepository = AdvertisementRepository()

    fun requestAdvertisements(): List<Advertisement> =
        advertisementRepository.requestAdvertisements()
}
