package woowacourse.movie.presentation.ui.main.home

import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.dummy.DummyScreens
import woowacourse.movie.domain.dummy.DummyTheater
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.base.BaseMvpBindingFragment
import woowacourse.movie.presentation.ui.detail.DetailActivity
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewAdapter
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheatersFragment

class HomeFragment : BaseMvpBindingFragment<FragmentHomeBinding>(), HomeContract.View {
    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    val presenter: HomePresenter by lazy { HomePresenter(this, DummyScreens, DummyTheater) }
    private val adapter: ScreenRecyclerViewAdapter by lazy { ScreenRecyclerViewAdapter(presenter) }
    private lateinit var dialog: BottomTheatersFragment

    override fun initStartView() {
        presenter.loadScreens()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvScreen.adapter = adapter
    }

    override fun showScreens(screens: List<ScreenView>) {
        adapter.updateScreens(screens)
    }

    override fun showBottomTheater(
        theaterCounts: List<TheaterCount>,
        movieId: Int,
    ) {
        dialog = BottomTheatersFragment(theaterCounts, presenter, movieId)
        dialog.show(this.parentFragmentManager, null)
    }

    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        dialog.dismiss()
        DetailActivity.startActivity(requireActivity(), movieId, theaterId)
    }

    companion object {
        val TAG = "HOME_FRAGMENT"
    }
}
