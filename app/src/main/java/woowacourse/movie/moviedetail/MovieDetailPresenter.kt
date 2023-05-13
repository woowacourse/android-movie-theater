package woowacourse.movie.moviedetail

import domain.TicketCount
import domain.movieinfo.Movie
import domain.movieinfo.MovieTheater
import domain.screeningschedule.ReservationDate
import woowacourse.movie.dto.movie.MovieDto
import woowacourse.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.mapper.movie.mapToMovie
import woowacourse.movie.mapper.movie.mapToMovieDto
import woowacourse.movie.mapper.movie.mapToMovieTheater
import woowacourse.movie.mapper.movie.mapToMovieTheaterDto

class MovieDetailPresenter(private val view: MovieDetailContract.View) :
    MovieDetailContract.Presenter {
    lateinit var movie: Movie
    lateinit var theater: MovieTheater

    override var movieTicket = TicketCount()

    override fun getData(movieDto: MovieDto, theaterDto: MovieTheaterDto) {
        movie = movieDto.mapToMovie()
        theater = theaterDto.mapToMovieTheater()
    }

    override fun updateMovieInfo() {
        view.showMovieInfo(movie.mapToMovieDto())
    }

    override fun subTicketCount() {
        if (movieTicket.numberOfPeople >= 2){
            movieTicket.decrease()
            view.showTicketCount(movieTicket.numberOfPeople)
        } else {
            view.showTicketCount(movieTicket.numberOfPeople)
        }
    }

    override fun plusTicketCount() {
        movieTicket.increase()
        view.showTicketCount(movieTicket.numberOfPeople)
    }

    override fun setBookingButton(){
        view.onClickBookBtnListener(movie.mapToMovieDto(), theater.mapToMovieTheaterDto())
    }

    override fun setSpinner(){
        view.setDateSpinner(getIntervalDays())
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
