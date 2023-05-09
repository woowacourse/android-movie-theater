package woowacourse.movie.view.moviemain.movielist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.reservation.ReservationActivity

class TheaterBottomSheetFragment : BottomSheetDialogFragment(R.layout.theater_select_bottom_sheet) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelableCompat<MovieUiModel>(KEY_MOVIE)
        val theatersView = view.findViewById<RecyclerView>(R.id.recycler_theaters)
        theatersView.adapter = movie?.schedule?.let { schedule ->
            TheaterListAdapter(schedule) { theaterName ->
                val intent = ReservationActivity.newIntent(requireContext(), movie, theaterName)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val TAG_THEATER = "THEATER"
        private const val KEY_MOVIE = "MOVIE"
        fun of(movie: MovieUiModel): TheaterBottomSheetFragment {
            val bundle = Bundle()
            bundle.putParcelable(KEY_MOVIE, movie)
            val fragment = TheaterBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
