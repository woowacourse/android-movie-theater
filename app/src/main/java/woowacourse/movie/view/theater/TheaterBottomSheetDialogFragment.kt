package woowacourse.movie.view.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.domain.model.TheaterUIModel
import woowacourse.movie.view.Extras
import woowacourse.movie.view.compatParcelable
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.reservation.detail.ReservationDetailActivity

class TheaterBottomSheetDialogFragment :
    BottomSheetDialogFragment(),
    TheaterContract.View {
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private val presenter: TheaterPresenter by lazy {
        val movie =
            requireArguments().compatParcelable<MovieUiModel>(Extras.MovieData.MOVIE_KEY)
                ?: error(ERROR_ARGUMENT)
        TheaterPresenter(this, movie)
    }
    private val theaterAdapter: TheaterAdapter by lazy {
        TheaterAdapter(
            object : TheaterClickListener {
                override fun onTheaterClick(theaterUIModel: TheaterUIModel) {
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
        setupTheaterAdapter()
        presenter.fetchTheaters()
    }

    override fun showTheaters(theaters: List<TheaterUIModel>) {
        theaterAdapter.submitList(theaters)
    }

    override fun navigateToReservation(theaterUIModel: TheaterUIModel) {
        val intent =
            Intent(requireContext(), ReservationDetailActivity::class.java).apply {
                putExtra(Extras.TheaterData.THEATER_UI_MODEL_KEY, theaterUIModel)
            }
        startActivity(intent)
        dismiss()
    }

    private fun setupTheaterAdapter() {
        binding.rvTheater.adapter = theaterAdapter
    }

    private fun onTheaterClicked(theaterUIModel: TheaterUIModel) {
        if (theaterUIModel.timeSlotCount == 0) {
            Toast
                .makeText(
                    requireContext(),
                    getString(R.string.bottom_sheet_dialog_error_empty_showing_movie),
                    Toast.LENGTH_SHORT,
                ).show()
        } else {
            presenter.theaterSelected(theaterUIModel)
        }
    }

    companion object {
        fun newInstance(movie: MovieUiModel): TheaterBottomSheetDialogFragment =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(Extras.MovieData.MOVIE_KEY, movie)
                    }
            }

        private const val ERROR_ARGUMENT = "arguments가 없습니다."
    }
}
