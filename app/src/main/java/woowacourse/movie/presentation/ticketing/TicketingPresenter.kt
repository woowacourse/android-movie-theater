package woowacourse.movie.presentation.ticketing

import woowacourse.movie.model.Count
import woowacourse.movie.model.Movie
import woowacourse.movie.repository.MovieRepository
import woowacourse.movie.repository.TheaterListRepository

class TicketingPresenter(
    private val ticketingContractView: TicketingContract.View,
    private val movieRepository: MovieRepository = MovieRepository(),
    private val theaterListRepository: TheaterListRepository,
) : TicketingContract.Presenter {
    private val count = Count()
    private lateinit var selectedMovie: Movie
    private var selectedTimePosition = 0
    private var theaterId = 0L

    override fun loadMovieData(
        movieId: Long,
        theaterId: Long,
    ) {
        this.theaterId = theaterId
        movieRepository.findMovieById(movieId)
            .onSuccess { movie ->
                selectedMovie = movie
                ticketingContractView.displayMovieDetail(movie)
                ticketingContractView.setUpDateSpinners(
                    movie.screeningDates.getDatesBetweenStartAndEnd(),
                )
                ticketingContractView.bindTicketCount(count)
                loadTimeList(theaterId)
            }
            .onFailure {
                ticketingContractView.showErrorMessage(it.message)
            }
    }

    private fun loadTimeList(theaterId: Long) {
        theaterListRepository.findTheaterOrNull(theaterId)?.let {
            val timeList = it.findScreenScheduleListWithMovieId(selectedMovie.id)
            ticketingContractView.setUpTimeSpinners(timeList, selectedTimePosition)
        }
    }

    override fun updateCount(savedCount: Int) {
        count.update(savedCount)
        ticketingContractView.bindTicketCount(count)
    }

    override fun updateSelectedTimePosition(savedTimePosition: Int) {
        selectedTimePosition = savedTimePosition
    }

    override fun decreaseCount() {
        count.decrease()
        ticketingContractView.updateTicketCount()
    }

    override fun increaseCount() {
        count.increase()
        ticketingContractView.updateTicketCount()
    }

    override fun navigate() {
        ticketingContractView.navigate(selectedMovie.id, count.value, theaterId)
    }
}
