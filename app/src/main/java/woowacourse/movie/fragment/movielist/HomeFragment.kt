package woowacourse.movie.fragment.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.woowacourse.domain.Theater
import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.R
import woowacourse.movie.activity.moviedetail.MovieDetailActivity
import woowacourse.movie.data.MovieMockData
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.fragment.movielist.adapter.TheaterRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.toPresentation

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = HomePresenter(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieRecyclerView(onClickMovie(), onClickAd())
    }

    private fun onClickMovie() = { position: Int ->
//        val intent =
//            MovieDetailActivity.getIntent(
//                requireContext(),
//                MovieMockData.movies10000[position].toPresentation()
//            )
//        this.startActivity(intent)
        val hasMovieTheater = MovieMockData.theaterData.filter { theater ->
            theater.schedules.any { it.movie == MovieMockData.movies10000[position] }
        }
        presenter.setTheaterRecyclerView(hasMovieTheater, onClickTheater(hasMovieTheater))
    }

    private fun onClickAd() = { item: AdUIModel ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        this.startActivity(intent)
    }

    private fun onClickTheater(theaters: List<Theater>) = { position: Int ->
        val theaterMovie = TheaterMovie(
            theaters[position].name,
            theaters[position].schedules.first { it.movie == MovieMockData.movies10000[0] }
        )

        val intent =
            MovieDetailActivity.getIntent(
                requireContext(),
                theaterMovie.toPresentation()
            )
        this.startActivity(intent)
    }

    override fun setMovieRecyclerView(recyclerViewAdapter: MovieRecyclerViewAdapter) {
        binding.recyclerviewMovieList.adapter = recyclerViewAdapter
    }

    override fun setTheaterRecyclerView(recyclerViewAdapter: TheaterRecyclerViewAdapter) {
        val bottomDialogFragment = BottomSheetTheater(recyclerViewAdapter)
        bottomDialogFragment.show(childFragmentManager, "Theater")
    }
}
