package woowacourse.movie.feature.bookinghistory

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.movie.data.BookingHistoryDetails
import woowacourse.movie.data.BookingHistoryDetailsDatabase
import woowacourse.movie.data.toUiModel

class BookingHistoryPresenter(
    private val context: Context,
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {
    override fun prepareBookingHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = BookingHistoryDetailsDatabase.getDatabase(context)
            val list = db.bookingHistoryDetailsDao().getAll()

            withContext(Dispatchers.Main) {
                view.showBookingHistory(list.map { it.toUiModel() })
            }
        }
    }

    override fun selectBookingHistory(bookingHistory: BookingHistoryDetails) {
        view.navigateToBookingDetail(bookingHistory.toUiModel())
    }
}
