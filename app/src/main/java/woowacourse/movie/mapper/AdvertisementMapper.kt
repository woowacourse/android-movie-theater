package woowacourse.movie.mapper

import woowacourse.movie.data.AdvertisementItemViewData
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.mapper.ImageMapper.toDomain
import woowacourse.movie.mapper.ImageMapper.toView

object AdvertisementMapper : Mapper<Advertisement, AdvertisementItemViewData> {
    override fun Advertisement.toView(): AdvertisementItemViewData {
        return AdvertisementItemViewData(
            banner.toView()
        )
    }

    override fun AdvertisementItemViewData.toDomain(): Advertisement {
        return Advertisement(
            banner.toDomain()
        )
    }
}
