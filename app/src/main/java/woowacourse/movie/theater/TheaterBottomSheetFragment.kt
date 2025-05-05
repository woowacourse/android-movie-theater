package woowacourse.movie.theater

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.booking.detail.BookingDetailActivity
import woowacourse.movie.databinding.FragmentTheaterBinding
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterBottomSheetFragment : BottomSheetDialogFragment(), TheaterContract.View {
    private val presenter = TheaterPresenter(this)
    private lateinit var binding: FragmentTheaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val movie = initMovie()
        val theaters = initTheaters()
        presenter.initialize(movie, theaters)
    }

    private fun initTheaters(): ArrayList<TheaterUiModel> {
        val theaters: ArrayList<TheaterUiModel>? = arguments?.getParcelableArrayList(KEY_THEATERS)
        if (theaters == null) dismiss()

        return theaters!!
    }

    private fun initMovie(): MovieUiModel {
        val movie: MovieUiModel? = arguments?.getParcelable(KEY_MOVIE)
        if (movie == null) dismiss()

        return movie!!
    }

    override fun showTheaters(theaters: List<TheaterUiModel>) {
        binding.rvTheater.adapter =
            TheaterAdapter(theaters) { selected ->
                presenter.clickTheater(selected)
            }
    }

    override fun navigateToBookingDetail(
        theater: TheaterUiModel,
        movie: MovieUiModel,
    ) {
        val intent = BookingDetailActivity.createIntent(requireContext(), theater, movie)
        startActivity(intent)
        dismiss()
    }

    companion object {
        private const val KEY_THEATERS = "theatersData"
        private const val KEY_MOVIE = "theatersMovieData"

        fun create(
            movie: MovieUiModel,
            theaters: ArrayList<TheaterUiModel>,
        ): TheaterBottomSheetFragment {
            return TheaterBottomSheetFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(KEY_MOVIE, movie)
                        putParcelableArrayList(KEY_THEATERS, theaters)
                    }
            }
        }
    }
}
