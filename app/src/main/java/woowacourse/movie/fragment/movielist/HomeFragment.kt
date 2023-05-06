package woowacourse.movie.fragment.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.activity.moviedetail.MovieDetailActivity
import woowacourse.movie.data.MovieMockData
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.fragment.movielist.adapter.MovieRecyclerViewAdapter
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.toPresentation

class HomeFragment : Fragment(), HomeContract.View {
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = HomePresenter(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setMovieRecyclerView(onClickMovie(), onClickAd())
    }

    private fun onClickAd() = { item: AdUIModel ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        this.startActivity(intent)
    }

    private fun onClickMovie() = { position: Int ->
        val intent =
            MovieDetailActivity.getIntent(requireContext(), MovieMockData.movies10000[position].toPresentation())
        this.startActivity(intent)
    }

    override fun setMovieRecyclerView(recyclerViewAdapter: MovieRecyclerViewAdapter) {
        binding.recyclerviewMovieList.adapter = recyclerViewAdapter
    }
}
