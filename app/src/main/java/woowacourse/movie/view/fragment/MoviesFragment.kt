package woowacourse.movie.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.contract.MoviesContract
import woowacourse.movie.databinding.FragmentMoviesBinding
import woowacourse.movie.view.activity.MovieReservationActivity
import woowacourse.movie.view.adapter.MovieAdapter
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.presenter.MoviesPresenter


class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesContract.View {
    override val presenter: MoviesContract.Presenter by lazy { MoviesPresenter(this) }
    private lateinit var binding: FragmentMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.updateMovieList()
    }

    override fun setAdapter(
        movieUiModels: List<MovieUiModel>,
        advertisementUiModel: AdvertisementUiModel
    ) {
        binding.mainMovieList.adapter = MovieAdapter(
            movieUiModels = movieUiModels,
            advertisementUiModel = advertisementUiModel,
            advertisementClickEvent = presenter::onAdvertisementItemClick,
            presenter::onMovieItemClick
        )
    }

    override fun startMovieReservationActivity(movieUiModel: MovieUiModel) {
        MovieReservationActivity.start(requireContext(), movieUiModel)
    }

    override fun startAdvertisementUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
