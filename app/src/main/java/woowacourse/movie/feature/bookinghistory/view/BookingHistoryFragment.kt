package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.presenter.BookingHistoryPresenter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
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
    private val bookingHistoryAdapter: BookingHistoryAdapter by lazy {
        BookingHistoryAdapter { bookingHistory ->
            presenter.selectBookingHistory(bookingHistory)
        }
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
        presenter
    }

    override fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>) {
        bookingHistoryAdapter.submitList(bookingHistory)
        binding.bookingHistoryAdapter = bookingHistoryAdapter

        val divider =
            DividerItemDecoration(binding.rvBookingHistory.context, LinearLayoutManager.VERTICAL)
        binding.rvBookingHistory.addItemDecoration(divider)
    }

    override fun navigateToBookingDetail(bookingHistory: BookingInfoUiModel) {
        val intent = BookingCompleteActivity.newIntent(requireContext(), bookingHistory)
        startActivity(intent)
    }
}
