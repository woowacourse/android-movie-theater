package woowacourse.movie.view.movie.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDao
import woowacourse.movie.model.Theater
import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.view.Extras
import woowacourse.movie.view.compatParcelable
import woowacourse.movie.view.movie.MoviesActivity
import woowacourse.movie.view.movie.TheaterClickListener
import woowacourse.movie.view.reservation.reservation.ReservationFragment

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie =
            requireArguments().compatParcelable(Extras.MovieData.MOVIE_KEY)
                ?: error("Movie argument is required")
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

        binding.rvTheater.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fcv_main, ReservationFragment())
                addToBackStack(null)
                dismiss()
            }
        }
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
        val bundle =
            bundleOf(
                Extras.TheaterData.THEATER_UI_MODEL_KEY to theaterUIModel,
            )
        parentFragmentManager.setFragmentResult("requestKey", bundle)

        (requireActivity() as? MoviesActivity)?.replaceFragment(ReservationFragment())
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
    }
}
