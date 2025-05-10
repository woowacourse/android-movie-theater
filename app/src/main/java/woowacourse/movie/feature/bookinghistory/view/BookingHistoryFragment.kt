package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.domain.repository.BookingRepository
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.presenter.BookingHistoryPresenter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter.Handler
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private val bookingRepository: BookingRepository by lazy {
        (requireActivity().application as MovieApplication).bookingRepository
    }
    private val presenter: BookingHistoryContract.Presenter by lazy { BookingHistoryPresenter(this, bookingRepository) }
    private val bookingHistoryAdapter: BookingHistoryAdapter by lazy { BookingHistoryAdapter(setupAdapterClickListener()) }
    private lateinit var binding: FragmentBookingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getBookingHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking_history, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookingHistoryAdapter = bookingHistoryAdapter
    }

    override fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>) {
        bookingHistoryAdapter.submitList(bookingHistory)
    }

    override fun navigateToBookingComplete(bookingInfo: BookingInfoUiModel) {
        val intent = BookingCompleteActivity.newIntent(requireContext(), bookingInfo)
        startActivity(intent)
    }

    private fun setupAdapterClickListener() =
        object : Handler {
            override fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel) {
                presenter.selectBookingHistory(bookingInfo)
            }
        }
}
