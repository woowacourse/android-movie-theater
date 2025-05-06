package woowacourse.movie.feature.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.feature.home.contract.HomeContract
import woowacourse.movie.feature.home.presenter.HomePresenter
import woowacourse.movie.feature.home.view.adapter.ContentItem
import woowacourse.movie.feature.home.view.adapter.ContentsAdapter
import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel
import woowacourse.movie.feature.theaters.view.TheatersDialogFragment

class HomeFragment :
    Fragment(),
    HomeContract.View {
    private lateinit var binding: FragmentHomeBinding
    private val contentsAdapter by lazy { ContentsAdapter(setupAdapterClickListeners()) }
    private val presenter: HomeContract.Presenter by lazy { HomePresenter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        presenter.prepareContents()
    }

    override fun showContents(contents: List<ContentUiModel>) {
        contentsAdapter.submitList(contents.map { ContentItem.from(it) })
        binding.moviesAdapter = contentsAdapter
    }

    override fun showTheaters(screenings: List<ScreeningUiModel>) {
        TheatersDialogFragment
            .newInstance(screenings)
            .show(childFragmentManager, TheatersDialogFragment.TAG)
    }

    private fun setupAdapterClickListeners(): ContentsAdapter.Handler =
        object : ContentsAdapter.Handler {
            override fun onBookingClick(movie: MovieUiModel) {
                presenter.selectMovieForBooking(movie)
            }
        }
}
