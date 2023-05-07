package woowacourse.movie.movie.moviedetail

import domain.movieinfo.Movie
import domain.screeningschedule.ReservationDate
import woowacourse.movie.movie.dto.movie.MovieDto
import woowacourse.movie.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import woowacourse.movie.movie.mapper.movie.mapToMovie

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {
    lateinit var movie: Movie
    lateinit var theater: MovieTheaterDto

    override fun initActivity(movieDto: MovieDto, theater: MovieTheaterDto) {
        movie = movieDto.mapToMovie()
        this.theater = theater
        view.showMovieInfo(movie.moviePoster, movie.title, movie.description)
        view.showMovieDateInfo(getMovieDate(), getMovieTime())
        view.showNumberOfPeople()
        view.onClickBookBtnListener(movieDto, theater)
        view.setDateSpinner(getIntervalDays())
    }

    override fun getMovieDate(): String {
        return view.formatMovieRunningDate(movie.startDate, movie.endDate)
    }

    override fun getMovieTime(): String {
        return view.formatMovieRunningTime(movie.runningTime)
    }

    override fun decreaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun increaseCount(movieTicket: TicketCountDto): String {
        return movieTicket.numberOfPeople.toString()
    }

    override fun getIntervalDays(): List<String> {
        return ReservationDate(movie.startDate, movie.endDate).getIntervalDays()
    }
    override fun getTimes(): List<String> {
        val times = theater.time
        return times.asSequence()
            .map { it.toString() }
            .toList()
    }
}
