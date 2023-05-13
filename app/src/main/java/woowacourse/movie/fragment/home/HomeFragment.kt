package woowacourse.movie.fragment.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.Ad
import woowacourse.movie.BottomSheetTheaterPicker
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.movie.Movie
import woowacourse.movie.movielist.MovieRecyclerViewAdapter

class HomeFragment : Fragment(), HomeContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override lateinit var presenter: HomeContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(this)
        presenter.initMovieList()
    }

    override fun showTheaterPicker(): (Int) -> Unit = {
        val bottomSheetTheaterPicker = BottomSheetTheaterPicker(it)
        bottomSheetTheaterPicker.show(parentFragmentManager, bottomSheetTheaterPicker.tag)
    }

    override fun startAdDetailPage(): (ad: Ad) -> Unit = { ad: Ad ->
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ad.url)))
    }

    override fun setMovieList(
        movies: List<Movie>,
        ad: Ad,
        movieOnItemClicked: (Int) -> Unit,
        adOnItemClicked: (Ad) -> Unit
    ) {
        val movieRecyclerViewAdapter = MovieRecyclerViewAdapter(
            movies,
            ad,
            movieOnItemClicked,
            adOnItemClicked
        )
        binding.rvMovieList.adapter = movieRecyclerViewAdapter
        movieRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
