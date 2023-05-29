package woowacourse.movie.data.theater

import woowacourse.movie.uimodel.TheaterModel
import woowacourse.movie.utils.MockData

class TheaterRepositoryImpl : TheaterRepository {
    override fun getData(): List<TheaterModel> {
        return MockData.getTheaterList()
    }
}
