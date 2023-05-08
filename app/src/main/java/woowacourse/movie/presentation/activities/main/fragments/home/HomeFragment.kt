package woowacourse.movie.presentation.activities.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.presentation.activities.main.fragments.theaterPicker.TheaterPickerDialog
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomePresenter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        initMovieListAdapter()
    }

    private fun initMovieListAdapter() {
        val movieListAdapter = MovieListAdapter(
            adTypes = Ad.provideDummy(),
            onItemClick = { item ->
                when (item) {
                    is Movie -> presenter.onMovieClicked(item)
                    is Ad -> presenter.onAdClicked(item)
                    is Reservation -> {}
                    is Theater -> {}
                }
            },
        ).also { it.appendAll(Movie.provideDummy()) }

        binding.moviesRv.adapter = movieListAdapter
        binding.moviesRv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(DOWN_DIRECTION) && dy > 0) {
                    movieListAdapter.appendAll(Movie.provideDummy())
                }
            }
        })
    }

    override fun showTheaterList(movie: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(movie)
        theaterPickerDialog.show(parentFragmentManager, TheaterPickerDialog.TAG)
    }

    override fun moveAdWebPage(ads: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ads.url))
        startActivity(intent)
    }

    companion object {
        private const val DOWN_DIRECTION = 1
    }
}
