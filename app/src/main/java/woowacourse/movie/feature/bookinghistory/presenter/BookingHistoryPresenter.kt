package woowacourse.movie.feature.bookinghistory.presenter

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.data.BookingHistoryDetailsDatabase
import woowacourse.movie.data.toUiModel
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryPresenter(
    private val context: Context,
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {
    init {
        prepareBookingHistory()
    }

    override fun prepareBookingHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = BookingHistoryDetailsDatabase.Companion.getDatabase(context)
            val list = db.bookingHistoryDetailsDao().getAll()

            withContext(Dispatchers.Main) {
                view.showBookingHistory(list.map { it.toUiModel() })
            }
        }
    }

    override fun selectBookingHistory(bookingHistory: BookingInfoUiModel) {
        view.navigateToBookingDetail(bookingHistory)
    }
}
