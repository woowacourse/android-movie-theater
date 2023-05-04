package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import woowacourse.movie.R
import woowacourse.movie.data.MovieListViewType
import woowacourse.movie.data.MovieScheduleViewData
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.TheaterViewData
import woowacourse.movie.data.TheatersViewData
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMovieRecyclerView(view)
    }

    private fun makeMovieRecyclerView(view: View) {
        val movieRecyclerView = view.findViewById<RecyclerView>(R.id.movie_list_recycler)
        movieRecyclerView.adapter = MovieAdapter(::onClickItem).also { it.presenter.setMovieList() }
    }

    private fun onClickItem(movieViewData: MovieViewData, theatersViewData: TheatersViewData) {
        when (movieViewData.viewType) {
            MovieListViewType.MOVIE -> createTheaterDialog(movieViewData, theatersViewData)
            MovieListViewType.ADVERTISEMENT -> Unit
        }
    }

    private fun createTheaterDialog(movieViewData: MovieViewData, theaters: TheatersViewData) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_theater, null)
        theaters.value.forEach { theater ->
            val item = makeTheaterItem(movieViewData, theater, bottomSheetView as ViewGroup)
            bottomSheetView.addView(item)
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun makeTheaterItem(
        movieViewData: MovieViewData,
        theater: TheaterViewData,
        root: ViewGroup
    ): View? {
        val movieSchedule =
            theater.movieSchedules.find { it.movie.title == movieViewData.title } ?: return null
        val item = layoutInflater.inflate(R.layout.item_theater, root, false)
        item.findViewById<TextView>(R.id.item_theater_name).text = theater.name
        item.findViewById<TextView>(R.id.item_theater_schedule).text =
            getString(R.string.schedule_count, movieSchedule.times.size)
        item.setOnClickListener {
            startMovieReservationActivity(movieViewData, movieSchedule, theater.name)
        }
        return item
    }

    private fun startMovieReservationActivity(
        movieViewData: MovieViewData,
        movieScheduleViewData: MovieScheduleViewData,
        theaterName: String
    ) {
        MovieReservationActivity.from(
            requireContext(), movieViewData, movieScheduleViewData, theaterName
        ).run {
            startActivity(this)
        }
    }
}
