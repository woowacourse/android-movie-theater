package woowacourse.movie.view.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.view.Extras
import woowacourse.movie.view.compatParcelable
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUiModel
import woowacourse.movie.view.model.TheatersUiModel
import woowacourse.movie.view.reservation.detail.ReservationDetailActivity

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterContract.View {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private val presenter: TheaterPresenter by lazy { TheaterPresenter(this) }
    private val theaterAdapter: TheaterAdapter by lazy {
        TheaterAdapter(
            object : TheaterClickListener {
                override fun onTheaterClick(theaterUIModel: TheaterUiModel) {
                    onTheaterClicked(theaterUIModel)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentTheaterBottomSheetDialogBinding.inflate(
                inflater,
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
        setupTheaterAdapter()

        val movie =
            requireArguments().compatParcelable<MovieUiModel>(Extras.MovieData.MOVIE_KEY)
                ?: error(ERROR_ARGUMENT)
        val theaterInfo =
            requireArguments().compatParcelable<TheatersUiModel>(Extras.TheaterData.THEATER_UI_MODEL_KEY)
                ?: error(ERROR_ARGUMENT)

        presenter.fetchTheaters(movie, theaterInfo)
    }

    override fun showTheaters(theaters: List<TheaterUiModel>) {
        theaterAdapter.submitList(theaters)
    }

    override fun showEmptySlotMessage() {
        showToast(getString(R.string.bottom_sheet_dialog_error_empty_showing_movie))
    }

    override fun navigateToReservation(
        movie: MovieUiModel,
        theater: TheaterUiModel,
    ) {
        val intent =
            Intent(requireContext(), ReservationDetailActivity::class.java).apply {
                putExtra(Extras.MovieData.MOVIE_KEY, movie)
                putExtra(Extras.TheaterData.THEATER_UI_MODEL_KEY, theater)
            }
        startActivity(intent)
        dismiss()
    }

    private fun setupTheaterAdapter() {
        binding.rvTheater.adapter = theaterAdapter
    }

    private fun onTheaterClicked(theater: TheaterUiModel) {
        presenter.theaterSelected(theater)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(
            movie: MovieUiModel,
            theater: TheatersUiModel,
        ): TheaterBottomSheetDialogFragment =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(Extras.MovieData.MOVIE_KEY, movie)
                        putParcelable(Extras.TheaterData.THEATER_UI_MODEL_KEY, theater)
                    }
            }

        private const val ERROR_ARGUMENT = "arguments가 없습니다."
    }
}
