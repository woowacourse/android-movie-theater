package woowacourse.movie.presentation.activities.main.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.presentation.activities.main.fragments.theaterPicker.TheaterPickerDialog
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie

class HomeFragment : Fragment(), HomeContract.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    override lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = HomePresenter(this)
        presenter.loadData()
    }

    override fun showMovieList(movies: List<ListItem>) {
        val movieListAdapter = MovieListAdapter(
            items = movies.toMutableList(),
            adTypes = Ad.provideDummy(),
            onItemClick = { item -> presenter.moveNext(item) },
        )
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

    override fun moveTheaterList(movie: Movie) {
        val theaterPickerDialog = TheaterPickerDialog.getInstance(movie)
        theaterPickerDialog.show(parentFragmentManager, TheaterPickerDialog.TAG)
    }

    override fun moveAdWebPage(ads: Ad) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ads.url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DOWN_DIRECTION = 1
    }
}
