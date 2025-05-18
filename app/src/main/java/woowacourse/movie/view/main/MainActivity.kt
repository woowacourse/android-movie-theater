package woowacourse.movie.view.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.view.history.BookingHistoryFragment
import woowacourse.movie.view.movies.MovieListFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainContract.Presenter
    private lateinit var notificationHelper: NotificationPermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        presenter = MainPresenterFactory().initialize(this)
        notificationHelper = NotificationPermissionHelper(this, binding.root, presenter, this)

        if (savedInstanceState == null) {
            binding.navigation.selectedItemId = R.id.action_home
            notificationHelper.requestPermissionIfNeeded()
        }

        initView()
    }

    private fun initView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val historyFragment = BookingHistoryFragment()
        val movieListFragment = MovieListFragment()
        val settingFragment = SettingFragment()

        setNavigationListener(historyFragment, movieListFragment, settingFragment)
    }

    private fun setNavigationListener(
        historyFragment: BookingHistoryFragment,
        movieListFragment: MovieListFragment,
        settingFragment: SettingFragment,
    ) {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_history -> replaceFragment(historyFragment)
                R.id.action_home -> replaceFragment(movieListFragment)
                R.id.action_setting -> replaceFragment(settingFragment)
                else -> return@setOnItemSelectedListener false
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
