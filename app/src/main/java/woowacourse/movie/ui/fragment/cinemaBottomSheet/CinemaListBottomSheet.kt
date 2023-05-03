package woowacourse.movie.ui.fragment.cinemaBottomSheet

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import woowacourse.movie.R
import woowacourse.movie.data.CinemaRepository
import woowacourse.movie.model.CinemaState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.main.adapter.CinemaListAdapter
import woowacourse.movie.ui.main.itemModel.BottomSheetItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity

class CinemaListBottomSheet(
    private val movie: MovieState
) : BottomSheetDialogFragment(R.layout.fragment_cinema_bottom_sheet), CinemaListContract.View {
    val presenter: CinemaListContract.Presenter = CinemaListPresenter()

    var button: Button? = null

    var bottomSheetListView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetListView = view.findViewById(R.id.bottom_sheet_movie_list)

        Log.d("re4rk", "CinemaRepository.allCinema() = ${CinemaRepository.allCinema()}")
        val adapter = CinemaListAdapter(
            cinemas = CinemaRepository.allCinema().map { BottomSheetItemModel(it) }
        ) { navigateMovieDetail(movie) }

        bottomSheetListView?.adapter = adapter
    }

    override fun setAdapter(cinemas: List<CinemaState>) {
        val adapter = CinemaListAdapter(
            cinemas = cinemas.map { BottomSheetItemModel(it) }
        ) { navigateMovieDetail(movie) }
        bottomSheetListView?.adapter = adapter
    }

    private fun navigateMovieDetail(movie: MovieState) {
        MovieDetailActivity.startActivity(requireContext(), movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        button = null
    }

    companion object {
        const val TAG_CINEMA_LIST_BOTTOM_SHEET = "tag_cinema_list_bottom_sheet"
    }
}
