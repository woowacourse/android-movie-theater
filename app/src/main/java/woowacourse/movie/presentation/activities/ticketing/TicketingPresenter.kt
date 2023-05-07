package woowacourse.movie.presentation.activities.ticketing

import woowacourse.movie.domain.model.ticket.DomainTicket
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.item.Movie

class TicketingPresenter(
    val view: TicketingContract.View,
    var movie: Movie,
) : TicketingContract.Presenter {
    private var movieTicket: DomainTicket = DomainTicket()

    override fun addTicketCount() {
        movieTicket += 1
        view.setTicketCountText(movieTicket.toPresentation())
    }

    override fun subTicketCount() {
        movieTicket -= 1
        view.setTicketCountText(movieTicket.toPresentation())
    }

    override fun setTicketCount() {
        view.setTicketCountText(movieTicket.toPresentation())
    }

    override fun moveToSeatPickerActivity(selectedDate: MovieDate?, selectedTime: MovieTime?) {
        if (selectedDate == null || selectedTime == null) {
            view.showToast("날짜와 시간을 선택해주세요!")
        }
        view.startSeatPickerActivity()
    }

    override fun getMovieTicket(): Ticket {
        return movieTicket.toPresentation()
    }

    override fun showMovieIntroduce() {
        view.setMovieData(movie)
    }

    override fun updateMovieTimes() {
        view.setMovieTimes()
    }
}
