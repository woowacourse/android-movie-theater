package woowacourse.movie.movie.moviedetail

import domain.movieinfo.Movie
import domain.screeningschedule.ReservationDate
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.movie.mapToMovie

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {

    override fun initActivity(movieDto: MovieDto) {
        val movie = movieDto.mapToMovie()
        view.showMovieInfo(movie.moviePoster, movie.title, movie.description)
        view.showMovieDateInfo(getMovieDate(movie), getMovieTime(movie))
        view.showNumberOfPeople()
        view.onClickBookBtnListener(movieDto)
        view.setDateSpinner(getIntervalDays(movie))
    }

    override fun getMovieDate(movie: Movie): String {
        return view.formatMovieRunningDate(movie.startDate, movie.endDate)
    }

    override fun getMovieTime(movie: Movie): String {
        return view.formatMovieRunningTime(movie.runningTime)
    }

    override fun decreaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun increaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun getIntervalDays(movie: Movie): List<String> {
        return ReservationDate(movie.startDate, movie.endDate).getIntervalDays()
    }
}
