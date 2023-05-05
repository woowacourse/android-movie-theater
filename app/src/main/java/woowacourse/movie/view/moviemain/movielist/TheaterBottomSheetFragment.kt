package woowacourse.movie.view.moviemain.movielist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.reservation.ReservationActivity

class TheaterBottomSheetFragment(private val movie: MovieUiModel) : BottomSheetDialogFragment(R.layout.theater_select_bottom_sheet) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theatersView = view.findViewById<RecyclerView>(R.id.recycler_theaters)
        theatersView.adapter = TheaterListAdapter(movie.schedule) {
            val intent = ReservationActivity.newIntent(requireContext(), movie, it)
            startActivity(intent)
        }
    }
    companion object {
        const val TAG_THEATER = "THEATER"
    }
}
