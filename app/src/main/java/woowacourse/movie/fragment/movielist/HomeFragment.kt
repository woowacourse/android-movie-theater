package woowacourse.movie.fragment.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.R
import woowacourse.movie.activity.moviedetail.MovieDetailActivity
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.MovieUIModel
import woowacourse.movie.model.toPresentation

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        presenter = HomePresenter(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieRecyclerView()
    }

    private fun onClickMovie() = { position: Int ->
        presenter.setTheaterRecyclerView(position)
    }

    private fun onClickAd() = { item: AdUIModel ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        this.startActivity(intent)
    }

    override fun setMovieRecyclerView(movies: List<MovieUIModel>, ad: AdUIModel) {
        binding.recyclerviewMovieList.adapter = MovieRecyclerViewAdapter(
            movies,
            ad,
            onClickMovie(),
            onClickAd(),
        )
    }

    override fun setTheaterRecyclerView(theaters: List<TheaterMovie>) {
        val bottomDialogFragment = BottomSheetTheater(theaters, onClickTheater(theaters))
        bottomDialogFragment.show(childFragmentManager, "Theater")
    }

    private fun onClickTheater(theaterMovies: List<TheaterMovie>) = { position: Int ->
        val intent =
            MovieDetailActivity.getIntent(
                requireContext(),
                theaterMovies[position].toPresentation(),
            )
        this.startActivity(intent)
    }
}
