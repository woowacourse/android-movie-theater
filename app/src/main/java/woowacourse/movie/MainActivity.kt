package woowacourse.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMovieBinding
import woowacourse.movie.movie.MovieFragment
import woowacourse.movie.reservation.TicketListFragment
import woowacourse.movie.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        setUpUi()

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
            supportFragmentManager.commit {
                add(R.id.fragment_view, MovieFragment(), TAG_MOVIE_FRAGMENT)
            }
        }

        initBottomNav()
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun initBottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> replaceFragment(TAG_MOVIE_FRAGMENT, MovieFragment::class.java)

                R.id.menu_setting -> replaceFragment(TAG_SETTING_FRAGMENT, SettingFragment::class.java)

                R.id.menu_reserve_list -> replaceFragment(TAG_RESERVATION_LIST_FRAGMENT, TicketListFragment::class.java)
            }
            true
        }
    }

    private fun replaceFragment(
        tag: String,
        fragment: Class<out Fragment>,
    ) {
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.commit {
            supportFragmentManager.fragments.forEach {
                hide(it)
            }

            if (existingFragment != null) {
                show(existingFragment)
            } else {
                add(R.id.fragment_view, fragment, null, tag)
            }
        }
    }

    companion object {
        private const val TAG_MOVIE_FRAGMENT = "tag_movie"
        private const val TAG_SETTING_FRAGMENT = "tag_setting"
        private const val TAG_RESERVATION_LIST_FRAGMENT = "tag_reservation"

        fun newIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
    }
}
