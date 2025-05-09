package woowacourse.movie.view.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMoviesBinding
import woowacourse.movie.view.main.home.MoviesFragment
import woowacourse.movie.view.main.reservationlist.ReservationListFragment
import woowacourse.movie.view.main.setting.SettingFragment

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupBinding()
        setupWindowInsets()
        initFragment(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBinding() {
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.activityMoviesRootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(MoviesFragment(), FRAGMENT_MOVIES)
        }
    }

    private fun setupBottomNavigation() {
        binding.activityMoviesBottomNavigation.selectedItemId = R.id.fragment_movies
        binding.activityMoviesBottomNavigation.setOnItemSelectedListener { menuItem ->
            val selectedFragment =
                when (menuItem.itemId) {
                    R.id.fragment_movies -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_MOVIES)
                            ?: MoviesFragment().also { addFragment(it, FRAGMENT_MOVIES) }
                    }

                    R.id.fragment_list -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_RESERVATION_LIST)
                            ?: ReservationListFragment().also {
                                addFragment(
                                    it,
                                    FRAGMENT_RESERVATION_LIST,
                                )
                            }
                    }

                    R.id.fragment_setting -> {
                        supportFragmentManager.findFragmentByTag(FRAGMENT_SETTING)
                            ?: SettingFragment().also { addFragment(it, FRAGMENT_SETTING) }
                    }

                    else -> throw IllegalArgumentException(ERROR_INVALID_FRAGMENT)
                }
            showFragmentWithHideElse(selectedFragment)
            true
        }
    }

    private fun addFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            Log.d("MoviesActivity", "addFragment: $tag")
            add(R.id.activity_movies_fragment_container, fragment, tag)
        }
    }

    private fun showFragmentWithHideElse(fragment: Fragment) {
        Log.d("MoviesActivity", "showFragmentWithHideElse Enter")
        val isAlreadyVisible =
            supportFragmentManager.fragments
                .any { it == fragment && it.isVisible }
        if (isAlreadyVisible) return

        supportFragmentManager.beginTransaction().apply {
            supportFragmentManager.fragments.forEach {
                hide(it)
            }
            Log.d("MoviesActivity", "showFragment: ${getFragmentTag(fragment)}")
            show(fragment)
            commit()
        }
    }

    private fun getFragmentTag(fragment: Fragment): String =
        when (fragment) {
            is MoviesFragment -> FRAGMENT_MOVIES
            is ReservationListFragment -> FRAGMENT_RESERVATION_LIST
            is SettingFragment -> FRAGMENT_SETTING
            else -> throw IllegalArgumentException(ERROR_INVALID_FRAGMENT)
        }

    companion object {
        private const val ERROR_INVALID_FRAGMENT = "알 수 없는 프래그먼트 입니다"
        private const val FRAGMENT_MOVIES = "movies"
        private const val FRAGMENT_RESERVATION_LIST = "reservation_list"
        private const val FRAGMENT_SETTING = "setting"
    }
}
