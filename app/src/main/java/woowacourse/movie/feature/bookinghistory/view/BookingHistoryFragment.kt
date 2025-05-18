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
import woowacourse.movie.di.RepositoryInjector.provideBookingHistoryRepository
import woowacourse.movie.domain.repository.BookingHistoryRepository
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.presenter.BookingHistoryPresenter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.NavigateType

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private lateinit var binding: FragmentBookingHistoryBinding
    private val repository: BookingHistoryRepository by lazy {
        provideBookingHistoryRepository(requireContext().applicationContext)
    }
    private val presenter: BookingHistoryContract.Presenter by lazy {
        BookingHistoryPresenter(
            this,
            repository,
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

    override fun onResume() {
        super.onResume()
        presenter.prepareBookingHistory()
    }

    override fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>) {
        requireActivity().runOnUiThread {
            bookingHistoryAdapter.submitList(bookingHistory)
            binding.rvBookingHistory.adapter = bookingHistoryAdapter

            val divider =
                DividerItemDecoration(
                    binding.rvBookingHistory.context,
                    LinearLayoutManager.VERTICAL,
                )
            binding.rvBookingHistory.addItemDecoration(divider)
        }
    }

    override fun navigateToBookingDetail(bookingHistory: BookingInfoUiModel) {
        val intent =
            BookingCompleteActivity.newIntent(
                requireContext(),
                bookingHistory,
                NavigateType.NAVIGATE_TO_PREVIOUS,
            )
        startActivity(intent)
    }
}
