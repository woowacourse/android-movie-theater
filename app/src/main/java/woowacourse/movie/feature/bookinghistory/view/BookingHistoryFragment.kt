package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter
import woowacourse.movie.feature.bookinghistory.view.adapter.BookingHistoryAdapter.Handler
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private lateinit var bookingHistoryAdapter: BookingHistoryAdapter
    private lateinit var binding: FragmentBookingHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_booking_history)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
//        binding.adapter = bookingHistoryAdapter
    }

    private fun setupAdapterClickListener() =
        object : Handler {
            override fun onBookingHistoryClick(bookingInfo: BookingInfoUiModel) {
                BookingCompleteActivity.newIntent(requireContext(), bookingInfo)
            }
        }
}
