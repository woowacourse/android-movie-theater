package woowacourse.movie.view.movie.theater

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDao
import woowacourse.movie.model.Theater
import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.view.Extras
import woowacourse.movie.view.compatParcelable
import woowacourse.movie.view.movie.TheaterClickListener
import woowacourse.movie.view.reservation.reservation.ReservationActivity

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie =
            requireArguments().compatParcelable(Extras.MovieData.MOVIE_KEY) ?: error(ERROR_ARGUMENT)
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
        binding.fragmentBottomSheet = this
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupTheaterAdapter()
    }

    private fun setupTheaterAdapter() {
        val recyclerView = binding.rvTheater
        theaterAdapter =
            TheaterAdapter(
                object : TheaterClickListener {
                    override fun onTheaterClick(theaterUIModel: TheaterUIModel) {
                        navigateToReservation(theaterUIModel)
                    }
                },
                movie,
            )
        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(
            MovieDao().getTheaterNames().map { Theater(it, MovieDao().getMovies(it)) },
        )
    }

    private fun navigateToReservation(theaterUIModel: TheaterUIModel) {
        val intent =
            Intent(requireContext(), ReservationActivity::class.java).apply {
                putExtra(Extras.TheaterData.THEATER_UI_MODEL_KEY, theaterUIModel)
            }
        startActivity(intent)
        dismiss()
    }

    companion object {
        fun newInstance(movie: Movie): TheaterBottomSheetDialogFragment =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(Extras.MovieData.MOVIE_KEY, movie)
                    }
            }

        private const val ERROR_ARGUMENT = "arguments가 없습니다."
    }
}
