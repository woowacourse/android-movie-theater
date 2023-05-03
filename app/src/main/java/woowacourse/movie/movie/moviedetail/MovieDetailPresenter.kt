package woowacourse.movie.movie.moviedetail

import android.util.Log
import domain.movieinfo.Movie
import domain.screeningschedule.ReservationDate
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.movie.mapToMovie

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {

    override fun initActivity(movieDto: MovieDto) {
        Log.d("test", "initActivity 진입")
        val movie = movieDto.mapToMovie()
        view.showMovieInfo(movie.moviePoster, movie.title, movie.description)
        view.showMovieDateInfo(getMovieDate(movie), getMovieTime(movie))
        view.showNumberOfPeople()
        view.onClickBookBtnListener(movieDto)
        view.setDateSpinner(getIntervalDays(movie))
    }

    override fun getMovieDate(movie: Movie): String {
        Log.d("test", "getMovieDate 진입")
        return view.formatMovieRunningDate(movie.startDate, movie.endDate)
    }

    override fun getMovieTime(movie: Movie): String {
        Log.d("test", "getMovieTime 진입")
        return view.formatMovieRunningTime(movie.runningTime)
    }

    override fun decreaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun increaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun getIntervalDays(movie: Movie): List<String> {
        Log.d("test", "getIntervalDays 진입")
        return ReservationDate(movie.startDate, movie.endDate).getIntervalDays()
    }
}
