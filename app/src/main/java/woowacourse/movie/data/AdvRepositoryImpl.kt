package woowacourse.movie.data

import com.example.domain.model.Adv
import com.example.domain.repository.AdvRepository
import com.example.domain.repository.dataSource.advDataSources

class AdvRepositoryImpl : AdvRepository {
    private val advs: List<Adv> = advDataSources.toList()

    override fun allAdv(): List<Adv> = advs.toList()
}
