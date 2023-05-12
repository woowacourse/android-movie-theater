package woowacourse.movie.repository

import woowacourse.movie.model.AdModel

interface AdRepository {
    fun getAll(): List<AdModel>
}
