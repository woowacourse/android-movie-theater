package woowacourse.movie.presentation.view.home.movies.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.presentation.extension.getParcelableCompat
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel

class TheaterBottomSheetDialogFragment(
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : BottomSheetDialogFragment() {
    private var mBinding: FragmentTheaterBottomSheetDialogBinding? = null
    private val binding get() = mBinding!!

    private val theaterAdapter: TheatersAdapter by lazy {
        TheatersAdapter {
            navigateToReservationScreen(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater_bottom_sheet_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val theaters = arguments.getParcelableCompat<TheatersUiModel>("theaters")
        binding.rvTheater.adapter = theaterAdapter
        binding.rvTheater.layoutManager = LinearLayoutManager(requireContext())
        theaterAdapter.submitList(theaters.theaters.map { TheaterUiModel(it.key, it.value) })
    }

    private fun navigateToReservationScreen(theater: TheaterUiModel) {
        onClickTheater(theater)
        dismiss()
    }

    companion object {
        private const val BUNDLE_KEY_THEATERS = "theaters"

        fun newInstance(
            theaters: TheatersUiModel,
            onClickTheater: (TheaterUiModel) -> Unit,
        ): TheaterBottomSheetDialogFragment {
            val fragment = TheaterBottomSheetDialogFragment(onClickTheater)
            fragment.apply {
                arguments = bundleOf(BUNDLE_KEY_THEATERS to theaters)
            }

            return fragment
        }
    }
}
