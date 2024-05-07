package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.Quantity
import woowacourse.movie.presentation.model.Quantity.Companion.MAX_COUNT
import woowacourse.movie.presentation.model.Quantity.Companion.MIN_COUNT

class ScreenDetailPresenter(
    private val view: ScreenDetailContract.View,
    private val repository: ScreenRepository,
) : ScreenDetailContract.Presenter {
    override fun loadScreenDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        repository.findScreen(movieId = movieId, theaterId = theaterId).onSuccess { screen ->
            val screenDetail =
                ScreenDetailUiModel(
                    screenId = screen.id,
                    theaterId = theaterId,
                    screen = screen,
                    selectableDates = screen.selectableDates,
                    selectedDate = screen.selectableDates.first(),
                    selectedTime = screen.selectableDates.first().getSelectableTimes().first(),
                )
            view.showScreenDetail(screenDetail)
        }.onFailure { e ->
            view.terminateOnError(e)
        }
    }

    override fun increaseQuantity(quantity: Quantity) {
        val newQuantity = quantity.increase(1)
        if (newQuantity.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMaxCountMessage(MAX_COUNT))
            return
        }
        view.updateTicketQuantity(newQuantity)
    }

    override fun decreaseQuantity(quantity: Quantity) {
        val newTicket = quantity.decrease(1)
        if (newTicket.isInvalidCount()) {
            view.showSnackBar(MessageType.TicketMinCountMessage(MIN_COUNT))
            return
        }
        view.updateTicketQuantity(newTicket)
    }
}
