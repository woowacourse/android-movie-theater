package woowacourse.movie.presentation.activities.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.fragments.history.HistoryFragment
import woowacourse.movie.presentation.activities.main.fragments.home.HomeFragment
import woowacourse.movie.presentation.activities.main.fragments.setting.SettingFragment
import woowacourse.movie.presentation.extensions.checkPermissions
import woowacourse.movie.presentation.extensions.showToast

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) showToast(getString(R.string.permission_allowed))
    }

    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }

    private lateinit var homeFragment: HomeFragment
    private lateinit var settingFragment: SettingFragment
    private lateinit var historyFragment: HistoryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermission()

        homeFragment = getFragment(TAG_HOME)
        historyFragment = getFragment(TAG_HISTORY)
        settingFragment = getFragment(TAG_SETTING)

        initFragments()
        initListener()
    }

    private fun initListener() {
        bottomNavigationView.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.history -> {
                    changeShowFragment<HistoryFragment>()
                }
                R.id.home -> {
                    changeShowFragment<HomeFragment>()
                }
                R.id.setting -> {
                    changeShowFragment<SettingFragment>()
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private inline fun <reified T : Fragment> changeShowFragment() {
        supportFragmentManager.commit {
            supportFragmentManager.fragments.forEach {
                if (it is T) {
                    show(it)
                } else {
                    hide(it)
                }
            }
        }
    }

    private fun initFragments() {
        supportFragmentManager.commit {
            add(R.id.fragment_container_view, homeFragment, TAG_HOME)
            add(R.id.fragment_container_view, historyFragment, TAG_HISTORY)
            add(R.id.fragment_container_view, settingFragment, TAG_SETTING)
            hide(historyFragment)
            hide(settingFragment)
        }
        bottomNavigationView.selectedItemId = R.id.home
    }

    private inline fun <reified T : Fragment> getFragment(tag: String): T {
        return supportFragmentManager.findFragmentByTag(tag) as? T
            ?: T::class.java.getDeclaredConstructor().newInstance()
    }

    private fun requestNotificationPermission() {
        if (checkPermissions(Manifest.permission.POST_NOTIFICATIONS)) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    companion object {
        private const val TAG_HOME = "HOME"
        private const val TAG_HISTORY = "HISTORY"
        private const val TAG_SETTING = "SETTING"
    }
}
