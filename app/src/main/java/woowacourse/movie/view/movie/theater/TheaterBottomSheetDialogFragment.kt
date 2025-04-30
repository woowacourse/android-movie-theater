package woowacourse.movie.view.movie.theater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentTheaterBottomSheetDialogBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.view.movie.MovieClickListener
import woowacourse.movie.view.reservation.reservation.ReservationFragment

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var theaterAdapter: TheaterAdapter
    private lateinit var binding: FragmentTheaterBottomSheetDialogBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = requireArguments().getParcelable("movieKey")!!
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
                object : MovieClickListener {
                    override fun onReservationClick(movie: Movie) {
//                        navigateToReservation(movie)
                    }
                },
            )
        recyclerView.adapter = theaterAdapter
        theaterAdapter.submitList(
            listOf(
                Theater("선릉", emptyList()),
                Theater("강남", emptyList()),
                Theater("잠실", emptyList()),
            ),
        )
    }

    companion object {
        private const val MOVIE_KEY = "movieKey"

        fun newInstance(movie: Movie): TheaterBottomSheetDialogFragment =
            TheaterBottomSheetDialogFragment().apply {
                arguments =
                    Bundle().apply {
                        putParcelable(MOVIE_KEY, movie)
                    }
            }
    }
}
