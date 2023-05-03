package woowacourse.data.advertisement

import woowacourse.domain.advertisement.Advertisement

object AdvertisementMapper {
    fun AdvertisementEntity.toAdvertisement(): Advertisement {
        return Advertisement(
            id = this.id,
            link = this.link,
        )
    }
}
