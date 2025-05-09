package woowacourse.movie.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieTheaterBinding
import woowacourse.movie.presentation.alarm.AlarmHelper
import woowacourse.movie.presentation.common.base.BaseActivity
import woowacourse.movie.presentation.history.ReservationHistoryFragment
import woowacourse.movie.presentation.home.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment

class MovieTheaterActivity : BaseActivity<ActivityMovieTheaterBinding>(R.layout.activity_movie_theater) {
    private var currentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlarmHelper.createNotificationChannel(this)
        setBottomNavigationItemClickListener()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
        } else {
            currentTag = savedInstanceState.getString(KEY_CURRENT_FRAGMENT_TAG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentTag?.let { outState.putString(KEY_CURRENT_FRAGMENT_TAG, it) }
    }

    private fun setBottomNavigationItemClickListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (isSameNavItem(item)) return@setOnItemSelectedListener false

            val fragmentClass =
                when (item.itemId) {
                    R.id.menu_home -> MoviesFragment::class.java
                    R.id.menu_history -> ReservationHistoryFragment::class.java
                    R.id.menu_setting -> SettingFragment::class.java
                    else -> return@setOnItemSelectedListener false
                }

            showFragment(fragmentClass)
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragment(fragmentClass: Class<out Fragment>) {
        val tag = fragmentClass.simpleName
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        val currentFragment = currentTag?.let { supportFragmentManager.findFragmentByTag(it) }

        supportFragmentManager.commit {
            currentFragment?.let { hide(it) }

            fragment?.let {
                show(it)
            } ?: add(R.id.fragment_container_view, fragmentClass, null, tag)
        }

        currentTag = tag
    }

    private fun isSameNavItem(item: MenuItem): Boolean = binding.bottomNavigation.selectedItemId == item.itemId

    companion object {
        private const val KEY_CURRENT_FRAGMENT_TAG = "current_fragment_tag"
    }
}
