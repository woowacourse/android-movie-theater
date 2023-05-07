package woowacourse.movie.data

import com.example.domain.model.TheaterScreeningInfo
import com.example.domain.repository.TheaterRepository
import com.example.domain.repository.dataSource.theaterMovieScreeningTimesDataSources

object TheaterRepositoryImpl : TheaterRepository {
    private val theaterScreeningInfos: List<TheaterScreeningInfo> =
        theaterMovieScreeningTimesDataSources.toList()

    override fun getAllTheaters(): List<TheaterScreeningInfo> {
        return theaterScreeningInfos.toList()
    }

//    override fun getScreeningMovieTheaters(movie: Movie): List<TheaterScreeningInfo> {
//        return theaterScreeningInfos.filter { theater -> movie in theater.screeningInfos.map { it.movie } }
//            .map {
//                it.copy(screeningInfos = it.screeningInfos.filter { movie == it.movie })
//            }
//    }
}
