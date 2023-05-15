package woowacourse.movie.presentation.view.main

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.SharedPreferenceUtil
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.permission.NotificationPermission
import woowacourse.movie.presentation.view.main.booklist.BookListFragment
import woowacourse.movie.presentation.view.main.home.MovieListFragment
import woowacourse.movie.presentation.view.main.setting.SettingFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private val presenter: MainContract.Presenter by lazy {
        MainPresenter(
            view = this,
            sharedPreferenceUtil = SharedPreferenceUtil(this)
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        presenter.setNotificationAlarmSetting(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setDefaultPermission()
        requestNotificationPermission()

        initBottomNavigation()
        setSelectEvent()
    }

    private fun setDefaultPermission() {
        presenter.setDefaultNotificationAlarmSetting()
    }

    private fun requestNotificationPermission() {
        val isGranted = NotificationPermission(this).isGranted()
        presenter.checkNotificationPermission(isGranted)
    }

    override fun updateNotificationGrantedView(isGranted: Boolean) {
        if (isGranted) {
            return
        }

        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS).not()) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initBottomNavigation() {
        changeFragment(MovieListFragment())
        binding.bottomNavigation.selectedItemId = R.id.action_home
    }

    private fun setSelectEvent() {
        binding.bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener getBottomNavigationClickSuccess(it)
        }
    }

    private fun getBottomNavigationClickSuccess(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.action_book_list -> changeFragment(BookListFragment())
            R.id.action_home -> changeFragment(MovieListFragment())
            R.id.action_settings -> changeFragment(SettingFragment())
            else -> return false
        }
        return true
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).commit()
    }
}
