package woowacourse.movie.presentation.ui.screen

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityScreenBinding
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.ui.detail.DetailActivity
import woowacourse.movie.presentation.ui.screen.ScreenContract.View
import woowacourse.movie.presentation.ui.screen.adapter.ScreenRecyclerViewAdapter
import woowacourse.movie.presentation.ui.screen.bottom.BottomTheatersFragment

class ScreenActivity : BaseActivity<ActivityScreenBinding>(), View {
    override val layoutResourceId: Int
        get() = R.layout.activity_screen
    override val presenter: ScreenPresenter by lazy { ScreenPresenter(this, DummyScreens()) }

    private val adapter: ScreenRecyclerViewAdapter by lazy { ScreenRecyclerViewAdapter(presenter) }
    private val rvScreen: RecyclerView by lazy { findViewById(R.id.rv_screen) }
    private lateinit var dialog: BottomTheatersFragment

    override fun initStartView() {
        binding.presenter = presenter
        initAdapter()
    }

    private fun initAdapter() {
        rvScreen.adapter = adapter
    }

    override fun showScreens(screens: List<ScreenView>) {
        adapter.updateScreens(screens)
    }

    override fun showBottomTheater(
        theaterCounts: List<TheaterCount>,
        movieId: Int,
    ) {
        dialog = BottomTheatersFragment(theaterCounts, presenter, movieId)
        dialog.show(this.supportFragmentManager, null)
    }

    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        dialog.dismiss()
        DetailActivity.startActivity(this, movieId, theaterId)
    }
}
