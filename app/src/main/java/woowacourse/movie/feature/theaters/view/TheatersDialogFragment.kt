package woowacourse.movie.feature.theaters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.DialogFragmentTheatersBinding
import woowacourse.movie.feature.bookingdetail.view.BookingDetailActivity
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.view.adapter.TheaterAdapter
import woowacourse.movie.util.getExtras

class TheatersDialogFragment : BottomSheetDialogFragment() {
    private val theaterAdapter: TheaterAdapter by lazy { TheaterAdapter(screenings, ::navigateToBookingDetail) }
    private val screenings: List<ScreeningUiModel> by lazy { arguments?.getExtras(SCREENINGS_KEY) ?: emptyList() }
    private lateinit var binding: DialogFragmentTheatersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_theaters, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.theaterAdapter = theaterAdapter
    }

    private fun navigateToBookingDetail(screening: ScreeningUiModel) {
        val intent = BookingDetailActivity.newIntent(requireContext(), screening)
        startActivity(intent)
        dismiss()
    }

    companion object {
        const val TAG = "SCREENS_DIALOG_FRAGMENT"
        private const val SCREENINGS_KEY = "screenings"

        fun newInstance(screenings: List<ScreeningUiModel>): TheatersDialogFragment =
            TheatersDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelableArrayList(SCREENINGS_KEY, ArrayList(screenings))
                    }
            }
    }
}
