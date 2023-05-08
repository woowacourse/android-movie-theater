package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import woowacourse.movie.R
import woowacourse.movie.data.MovieScheduleViewData
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.TheaterViewData
import woowacourse.movie.data.TheatersViewData
import woowacourse.movie.databinding.FragmentMovieListBinding
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter

class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMovieRecyclerView()
    }

    private fun makeMovieRecyclerView() {
        binding.movieListRecycler.adapter =
            MovieAdapter(::makeTheaterDialog)
    }

    private fun makeTheaterDialog(movieViewData: MovieViewData, theaters: TheatersViewData) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView =
            layoutInflater.inflate(R.layout.bottom_sheet_theater, binding.root as ViewGroup, false)
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
        val item = DataBindingUtil.inflate<ItemTheaterBinding>(
            layoutInflater,
            R.layout.item_theater,
            root,
            false
        )
        item.itemTheaterName.text = theater.name
        item.itemTheaterSchedule.text = getString(R.string.schedule_count, movieSchedule.times.size)
        item.root.setOnClickListener {
            startMovieReservationActivity(movieViewData, movieSchedule, theater.name)
        }
        return item.root
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
