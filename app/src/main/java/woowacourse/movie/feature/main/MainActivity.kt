package woowacourse.movie.feature.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.feature.common.OnDataUpdate
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.feature.movieList.MovieListFragment
import woowacourse.movie.feature.reservationList.ReservationListFragment
import woowacourse.movie.feature.setting.SettingFragment
import woowacourse.movie.util.requestPermissions

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding

    private lateinit var movieListFragment: MovieListFragment
    private lateinit var reservationListFragment: ReservationListFragment
    private lateinit var settingFragment: SettingFragment

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenter(this)

        movieListFragment = getFragment(MOVIE_LIST_TAG)
        reservationListFragment = getFragment(RESERVATION_LIST_TAG)
        settingFragment = getFragment(SETTING_TAG)

        if (savedInstanceState == null) initFragments()

        initListener()
        requestPermissions(PERMISSIONS, requestPermissionLauncher::launch)
    }

    private inline fun <reified T : Fragment> getFragment(tag: String): T {
        return supportFragmentManager.findFragmentByTag(tag) as? T
            ?: T::class.java.getDeclaredConstructor().newInstance()
    }

    private fun initFragments() {
        supportFragmentManager.commit {
            add(binding.container.id, movieListFragment, MOVIE_LIST_TAG)
            add(binding.container.id, reservationListFragment, RESERVATION_LIST_TAG)
            add(binding.container.id, settingFragment, SETTING_TAG)
            hide(reservationListFragment)
            hide(settingFragment)
        }
        binding.bottomNavigationView.selectedItemId = R.id.movie_list_item
    }

    private fun initListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_list_item -> presenter.clickReservationTab()
                R.id.movie_list_item -> presenter.clickMovieTab()
                R.id.setting_item -> presenter.clickSettingTab()
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun showMoviePage() =
        changeShowFragment<MovieListFragment>()

    override fun showReservationPage() =
        changeShowFragment<ReservationListFragment>()

    override fun showSettingPage() =
        changeShowFragment<SettingFragment>()

    private inline fun <reified T : Fragment> changeShowFragment() {
        supportFragmentManager.commit {
            supportFragmentManager.fragments.forEach {
                if (it is T) {
                    show(it)
                    it.updateData()
                } else {
                    hide(it)
                }
            }
        }
    }

    private fun Fragment.updateData() {
        if (this is OnDataUpdate) onUpdateData()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        presenter.permissionApproveResult(isGranted)
    }

    override fun showPermissionApproveMessage() =
        Toaster.showToast(this, getString(R.string.alarm_notification_approve))

    override fun showPermissionRejectMessage() =
        Toaster.showToast(this, getString(R.string.alarm_notification_reject))

    companion object {
        private const val RESERVATION_LIST_TAG = "reservation_list_tag"
        private const val MOVIE_LIST_TAG = "movie_list_tag"
        private const val SETTING_TAG = "setting_tag"

        val PERMISSIONS = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }
}
