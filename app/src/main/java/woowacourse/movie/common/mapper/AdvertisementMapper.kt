package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.ImageMapper.toDomain
import woowacourse.movie.common.mapper.ImageMapper.toView
import woowacourse.movie.common.model.AdvertisementItemViewData
import woowacourse.movie.domain.Advertisement

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
