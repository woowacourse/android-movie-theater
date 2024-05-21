package woowacourse.movie.data.repository

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.domain.model.Image

interface MovieRepository {
    fun findById(id: Int): MovieData

    fun imageSrc(id: Int): Image<Any>
}
