package woowacourse.movie.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.booking.detail.BookingDetailActivity
import woowacourse.movie.databinding.FragmentTheaterBottomSheetBinding
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterBottomSheetFragment : BottomSheetDialogFragment(), TheaterBottomSheetContract.View {
    private val presenter = TheaterBottomSheetPresenter(this)
    private var _binding: FragmentTheaterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_theater_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        presenter.initializeInfo(requireMovieOrDismiss(), requireTheatersOrDismiss())
    }

    override fun setUpTheaterList(theaters: List<TheaterUiModel>) {
        binding.rvTheater.adapter =
            TheaterAdapter(theaters) { theater ->
                presenter.selectTheater(theater)
            }
    }

    override fun startBookingDetail(
        movie: MovieUiModel,
        theater: TheaterUiModel,
    ) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            val intent = BookingDetailActivity.newIntent(requireActivity(), movie, theater)
            startActivity(intent)
            dismiss()
        }
    }

    private fun requireTheatersOrDismiss(): List<TheaterUiModel> {
        val theaters: List<TheaterUiModel>? = arguments?.getParcelableArrayList(KEY_THEATERS)
        return theaters ?: run {
            dismiss()
            throw IllegalArgumentException(ERROR_NOT_FOUND_DATA.format(KEY_THEATERS))
        }
    }

    private fun requireMovieOrDismiss(): MovieUiModel {
        return arguments?.getParcelable(KEY_MOVIE) ?: run {
            dismiss()
            throw IllegalArgumentException(ERROR_NOT_FOUND_DATA.format(KEY_MOVIE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ERROR_NOT_FOUND_DATA = "%s 데이터를 찾을 수 없습니다"
        private const val KEY_THEATERS = "THEATERS_DATA"
        private const val KEY_MOVIE = "MOVIE_DATA"

        fun newInstance(
            movie: MovieUiModel,
            theater: ArrayList<TheaterUiModel>,
        ): TheaterBottomSheetFragment =
            TheaterBottomSheetFragment().apply {
                arguments = newBundle(movie, theater)
            }

        private fun newBundle(
            movie: MovieUiModel,
            theater: ArrayList<TheaterUiModel>,
        ): Bundle =
            Bundle().apply {
                putParcelable(KEY_MOVIE, movie)
                putParcelableArrayList(KEY_THEATERS, theater)
            }
    }
}
