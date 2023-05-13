package woowacourse.movie.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.database.DBController
import woowacourse.movie.database.TicketDataDBHelper
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.history.model.BookingHistoryUIModel
import woowacourse.movie.history.view.HistoryFragment
import woowacourse.movie.movielist.HomeFragment
import woowacourse.movie.setting.SettingFragment
import woowacourse.movie.utils.Toaster

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dBController: DBController

    private val homeFragment = HomeFragment()
    private val settingFragment = SettingFragment()
    private val historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dBController = DBController(TicketDataDBHelper(this).writableDatabase)
        dBController.findAllDB()
        setContentView(binding.root)
        requestNotificationPermission()
        binding.navigationBar?.let(::onNavigationBarClickListener)
    }

    private fun onNavigationBarClickListener(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { item ->
            val screen = when (item.itemId) {
                R.id.home -> Screen.MovieList
                R.id.history -> Screen.History
                R.id.setting -> Screen.Setting
                else -> throw IllegalArgumentException()
            }
            setScreen(screen)
            true
        }
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun setScreen(screen: Screen) {
        when (screen) {
            Screen.MovieList -> {
                replaceFragment(homeFragment)
            }

            Screen.History -> {
                replaceFragment(historyFragment)
            }

            Screen.Setting -> {
                replaceFragment(settingFragment)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toaster.showToast(this@MainActivity, DENIED_PERMISSION_MESSAGE)
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                Toaster.showToast(this@MainActivity, LOWER_VERSION_MESSAGE)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isCheck: Boolean ->
        if (isCheck) Toaster.showToast(this@MainActivity, PERMIT_PERMISSION_MESSAGE)
        else Toaster.showToast(this@MainActivity, DENIED_PERMISSION_MESSAGE)
    }

    override fun onDestroy() {
        super.onDestroy()
        BookingHistoryUIModel.deleteAll()
        dBController.closeDB()
    }

    companion object {
        const val SETTING_PREFERENCE_KEY = "setting"

        private const val DENIED_PERMISSION_MESSAGE = "알림 권한이 차단되었습니다"
        private const val PERMIT_PERMISSION_MESSAGE = "알림 권한이 허용되었습니다"
        private const val LOWER_VERSION_MESSAGE = "버전이 낮아 권한이 자동으로 허용되었습니다"
    }
}
