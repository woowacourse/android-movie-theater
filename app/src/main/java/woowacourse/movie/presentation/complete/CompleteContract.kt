package woowacourse.movie.presentation.complete

import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel

interface CompleteContract {
    interface View {
        val presenter: Presenter
        val ticketModel: TicketModel
    }

    interface Presenter {
        fun requireMovieModel(): MovieModel
    }
}
