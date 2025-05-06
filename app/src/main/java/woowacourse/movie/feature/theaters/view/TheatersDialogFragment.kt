package woowacourse.movie.feature.theaters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.DialogFragmentTheatersBinding
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.view.adapter.TheaterAdapter

class TheatersDialogFragment : BottomSheetDialogFragment() {
    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var binding: DialogFragmentTheatersBinding
    private var screenings: List<ScreeningUiModel> = emptyList()
    var navigateToBookingDetail: ((ScreeningUiModel) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenings = requireArguments().getParcelableArrayList(ARG_SCREENINGS) ?: emptyList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_theaters, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        theaterAdapter =
            TheaterAdapter(screenings) { screening ->
                navigateToBookingDetail?.invoke(screening)
            }

        binding.theaterAdapter = theaterAdapter
    }

    companion object {
        private const val ARG_SCREENINGS = "ARG_SCREENINGS"
        const val TAG = "SCREENS_DIALOG_FRAGMENT"

        fun newInstance(screenings: List<ScreeningUiModel>): TheatersDialogFragment =
            TheatersDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelableArrayList(ARG_SCREENINGS, ArrayList(screenings))
                    }
            }
    }
}
