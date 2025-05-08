package woowacourse.movie.feature.bookinghistory.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentBookingHistoryBinding
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract

class BookingHistoryFragment :
    Fragment(),
    BookingHistoryContract.View {
    private val binding: FragmentBookingHistoryBinding by lazy {
        DataBindingUtil.setContentView(
            requireActivity(),
            R.layout.fragment_booking_history,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_booking_history, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
    }
}
