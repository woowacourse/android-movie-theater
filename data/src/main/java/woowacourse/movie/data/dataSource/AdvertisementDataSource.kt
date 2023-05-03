package woowacourse.movie.data.dataSource

import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.mock.AdvertisementMock

class AdvertisementDataSource : DataSource<Advertisement> {
    override val value: List<Advertisement>
        get() = data

    override fun add(t: Advertisement) {
        data.add(t)
    }

    companion object {
        private val data: MutableList<Advertisement> =
            AdvertisementMock.createAdvertisements().toMutableList()
    }
}
