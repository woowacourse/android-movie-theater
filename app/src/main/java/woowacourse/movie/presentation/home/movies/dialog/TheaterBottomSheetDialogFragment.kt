package woowacourse.movie.presentation.home.movies.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheaterUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.home.reservation.ReservationActivity

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterBottomSheetDialogContract.View {
    private var mBinding: FragmentTheaterBottomSheetDialogBinding? = null
    private val binding get() = mBinding!!
    private val presenter: TheaterBottomSheetDialogPresenter by lazy {
        TheaterBottomSheetDialogPresenter(
            this,
        )
    }

    private val theaterAdapter: TheatersAdapter by lazy {
        TheatersAdapter {
            presenter.presentTheaterItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_theater_bottom_sheet_dialog,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val theaters = arguments.getParcelableCompat<TheatersUiModel>(BUNDLE_KEY_THEATERS)
        val movie = arguments.getParcelableCompat<MovieUiModel>(BUNDLE_KEY_MOVIE)
        presenter.fetch(theaters, movie)
    }

    override fun showTheaters(theaters: TheatersUiModel) {
        binding.rvTheater.adapter = theaterAdapter
        theaterAdapter.submitList(theaters.theaters.map { TheaterUiModel(it.key, it.value) })
    }

    override fun showDetail(
        movie: MovieUiModel,
        theater: TheaterUiModel,
    ) {
        val intent =
            woowacourse.movie.presentation.home.reservation.ReservationActivity
                .newIntent(requireContext(), movie, theater)
        startActivity(intent)
        dismiss()
    }

    companion object {
        private const val BUNDLE_KEY_THEATERS = "theaters"
        private const val BUNDLE_KEY_MOVIE = "movie"

        fun newInstance(
            theaters: TheatersUiModel,
            movie: MovieUiModel,
        ): TheaterBottomSheetDialogFragment {
            val fragment = TheaterBottomSheetDialogFragment()
            fragment.apply {
                arguments = bundleOf(BUNDLE_KEY_THEATERS to theaters, BUNDLE_KEY_MOVIE to movie)
            }

            return fragment
        }
    }
}
