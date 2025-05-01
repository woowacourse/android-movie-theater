package woowacourse.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BookingHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_history, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            param1: String,
            param2: String,
        ) = BookingHistoryFragment().apply {
            arguments =
                Bundle().apply {
                }
        }
    }
}
