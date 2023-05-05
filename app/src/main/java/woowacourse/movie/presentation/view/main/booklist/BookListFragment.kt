package woowacourse.movie.presentation.view.main.booklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookListFragment : Fragment(R.layout.fragment_book_list), BookingListContract.View {
    private val presenter: BookingListContract.Presenter by lazy {
        BookingListPresenter(this, requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getReservationList()
    }

    override fun showReservationListView(bookings: List<ReservationResult>) {
        requireView().findViewById<RecyclerView>(R.id.rv_book_list).adapter =
            BookingListAdapter(bookings) {
                val intent = BookCompleteActivity.getIntent(requireContext())
                    .putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, it)
                requireContext().startActivity(intent)
            }
    }
}
