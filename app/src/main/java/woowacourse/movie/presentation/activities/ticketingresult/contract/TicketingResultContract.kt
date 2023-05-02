package woowacourse.movie.presentation.activities.ticketingresult.contract

interface TicketingResultContract {
    interface View {
        val presenter: Presenter
    }

    abstract class Presenter(protected val view: View)
}
