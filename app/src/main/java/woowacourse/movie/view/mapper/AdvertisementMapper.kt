package woowacourse.movie.view.mapper

import woowacourse.movie.domain.Advertisement
import woowacourse.movie.view.data.AdvertisementItemViewData
import woowacourse.movie.view.mapper.ImageMapper.toDomain
import woowacourse.movie.view.mapper.ImageMapper.toView

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
