package woowacourse.movie.data

import com.example.domain.model.TheaterScreeningInfo
import com.example.domain.repository.TheaterRepository

class TheaterRepositoryImpl : TheaterRepository {
    private val theaterScreeningInfos: List<TheaterScreeningInfo> =
        theaterMovieScreeningTimesDataSources.toList()

    override fun getAllTheaters(): List<TheaterScreeningInfo> {
        return theaterScreeningInfos.toList()
    }
}
