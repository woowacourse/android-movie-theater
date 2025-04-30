package woowacourse.movie.movie

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.Theaters
import woowacourse.movie.moviebooking.MovieBookingActivity

private const val ARG_PARAM1 = "movie"

class TheaterBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val movie: Movie =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(ARG_PARAM1, Movie::class.java) ?: throw IllegalArgumentException()
            } else {
                arguments?.getParcelable(ARG_PARAM1) ?: throw IllegalArgumentException()
            }

        val view = inflater.inflate(R.layout.fragment_theater_bottom_sheet_dialog, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.therters)
        val adapter = TheaterListAdapter(Theaters.theaters, movie) { theater -> navigateToTheater(theater, movie) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    private fun navigateToTheater(
        theater: Theater,
        movie: Movie,
    ) {
        val intent = MovieBookingActivity.movieBookingIntent(requireContext(), movie, theater)
        startActivity(intent)
    }
}
