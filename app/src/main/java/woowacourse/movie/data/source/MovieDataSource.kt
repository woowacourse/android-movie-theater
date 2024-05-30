package woowacourse.movie.data.source

import woowacourse.movie.data.model.MovieData
import woowacourse.movie.data.model.MoviePosterData
import woowacourse.movie.domain.model.Image

interface MovieDataSource {
    fun findById(id: Int): MovieData

    fun imageSrc(id: Int): Image<Any>

    fun imageSrc2(id: Int): MoviePosterData
}
