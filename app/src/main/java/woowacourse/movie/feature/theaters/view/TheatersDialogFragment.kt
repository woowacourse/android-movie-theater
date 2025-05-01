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

class TheatersDialogFragment(
    screenings: List<ScreeningUiModel>,
    navigateToBookingDetail: (ScreeningUiModel) -> Unit,
) : BottomSheetDialogFragment() {
    private val theaterAdapter: TheaterAdapter by lazy { TheaterAdapter(screenings, navigateToBookingDetail) }
    private lateinit var binding: DialogFragmentTheatersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
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

    companion object {
        const val TAG = "SCREENS_DIALOG_FRAGMENT"
    }
}
