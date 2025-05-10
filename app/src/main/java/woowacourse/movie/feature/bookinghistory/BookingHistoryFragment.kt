package woowacourse.movie.feature.bookinghistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private lateinit var binding: FragmentBookingHistoryBinding
    private val presenter: BookingHistoryContract.Presenter by lazy {
        BookingHistoryPresenter(
            requireContext(),
            this,
        )
    }
    private val bookingHistoryAdapter: BookingHistoryAdapter by lazy { BookingHistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_booking_history, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.prepareBookingHistory()
    }

    override fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>) {
        bookingHistoryAdapter.submitList(bookingHistory)
        binding.bookingHistoryAdapter = bookingHistoryAdapter
    }

    override fun navigateToBookingDetail(bookingHistory: BookingInfoUiModel) {
        TODO("Not yet implemented")
    }
}
