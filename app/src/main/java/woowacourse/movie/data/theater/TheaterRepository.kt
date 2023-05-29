package woowacourse.movie.data.theater

import woowacourse.movie.uimodel.TheaterModel

interface TheaterRepository {
    fun getData(): List<TheaterModel>
}
