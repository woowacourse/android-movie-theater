package woowacourse.movie.view.reservelist

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.util.CustomLiveData
import woowacourse.movie.util.Observer
import kotlin.concurrent.thread

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val repository: TicketRepository,
) : ReservationListContract.Presenter {
    private val customLiveData = CustomLiveData<List<Ticket>>()

    init {
        val observer =
            object : Observer<List<Ticket>> {
                override fun update(data: List<Ticket>) {
                    view.showReservationList(data)
                }
            }
        customLiveData.subscribe(observer)
    }

    override fun loadData() {
        thread {
            repository.findAll()
                .onSuccess {
                    customLiveData.put(it)
                }
        }
    }
}
