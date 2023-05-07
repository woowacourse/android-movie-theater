package woowacourse.movie.presentation.view.main.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentBookListBinding
import woowacourse.movie.presentation.model.ReservationResult
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class BookListFragment : Fragment(), BookingListContract.View {
    private lateinit var binding: FragmentBookListBinding
    private val presenter: BookingListContract.Presenter by lazy {
        BookingListPresenter(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getReservationList()
    }

    override fun showReservationListView(bookings: List<ReservationResult>) {
        binding.rvBookList.adapter =
            BookingListAdapter(bookings) {
                val intent = BookCompleteActivity.getIntent(requireContext())
                    .putExtra(BookCompleteActivity.RESERVATION_ID_INTENT_KEY, it)
                requireContext().startActivity(intent)
            }
    }
}
