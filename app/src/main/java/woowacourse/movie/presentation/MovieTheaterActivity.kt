package woowacourse.movie.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTheaterBinding
import woowacourse.movie.presentation.common.base.BaseActivity
import woowacourse.movie.presentation.history.ReservationHistoryFragment
import woowacourse.movie.presentation.home.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment

class MovieTheaterActivity : BaseActivity<ActivityMovieTheaterBinding>(R.layout.activity_movie_theater) {
    private var currentFragment: Fragment? = null

    private val moviesFragment by lazy {
        findOrCreateFragment(MoviesFragment::class.java.name, ::MoviesFragment)
    }
    private val historyFragment by lazy {
        findOrCreateFragment(
            ReservationHistoryFragment::class.java.name,
            ::ReservationHistoryFragment,
        )
    }
    private val settingFragment by lazy {
        findOrCreateFragment(SettingFragment::class.java.name, ::SettingFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomNavigationItemClickListener()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
            return
        }

        saveCurrentFragmentTag(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentFragment?.tag?.let { outState.putString(KEY_CURRENT_FRAGMENT_TAG, it) }
    }

    private fun setBottomNavigationItemClickListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (isSameNavItem(item)) return@setOnItemSelectedListener false

            when (item.itemId) {
                R.id.menu_home -> showFragment(moviesFragment, MoviesFragment::class.java.name)
                R.id.menu_history -> showFragment(historyFragment, ReservationHistoryFragment::class.java.name)
                R.id.menu_setting -> showFragment(settingFragment, SettingFragment::class.java.name)
            }

            true
        }
    }

    private fun showFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit {
            currentFragment?.let { hide(it) }

            if (findFragmentByTag(tag) == null) {
                add(R.id.fragment_container_view, fragment, tag)
            } else {
                show(fragment)
            }
        }

        currentFragment = fragment
    }

    private fun saveCurrentFragmentTag(savedInstanceState: Bundle) {
        val savedTag = savedInstanceState.getString(KEY_CURRENT_FRAGMENT_TAG)
        currentFragment = savedTag?.let { findFragmentByTag(it) }
    }

    private fun findOrCreateFragment(
        tag: String,
        fragmentCreator: () -> Fragment,
    ): Fragment = findFragmentByTag(tag) ?: fragmentCreator()

    private fun findFragmentByTag(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun isSameNavItem(item: MenuItem): Boolean = binding.bottomNavigation.selectedItemId == item.itemId

    companion object {
        private const val KEY_CURRENT_FRAGMENT_TAG = "current_fragment_tag"
    }
}
