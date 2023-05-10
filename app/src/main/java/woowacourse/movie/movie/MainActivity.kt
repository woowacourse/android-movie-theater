package woowacourse.movie.movie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.movie.database.DBController
import woowacourse.movie.movie.database.TicketDataDBHelper
import woowacourse.movie.movie.dto.BookingHistoryDto
import woowacourse.movie.movie.history.HistoryFragment
import woowacourse.movie.movie.history.HistoryFragment.Companion.TAG_HISTORY_FRAGMENT
import woowacourse.movie.movie.movielist.HomeFragment
import woowacourse.movie.movie.movielist.HomeFragment.Companion.TAG_HOME_FRAGMENT
import woowacourse.movie.movie.setting.SettingFragment
import woowacourse.movie.movie.setting.SettingFragment.Companion.TAG_SETTING_FRAGMENT
import woowacourse.movie.movie.utils.Toaster

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dBController: DBController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dBController = DBController(TicketDataDBHelper(this).writableDatabase)
        dBController.findAllDB()
        setContentView(binding.root)
        requestNotificationPermission()
        SettingPreference.initSharedPreferences(applicationContext)
        setFragment(TAG_HOME_FRAGMENT, HomeFragment())
        binding.navigationBar?.let(::onNavigationBarClickListener)
    }

    private fun onNavigationBarClickListener(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> setFragment(TAG_HOME_FRAGMENT, HomeFragment())
                R.id.history -> setFragment(TAG_HISTORY_FRAGMENT, HistoryFragment())
                R.id.setting -> setFragment(TAG_SETTING_FRAGMENT, SettingFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val ft = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.fragment_container_view, fragment, tag)
        }
        val home = manager.findFragmentByTag(TAG_HOME_FRAGMENT)
        val history = manager.findFragmentByTag(TAG_HISTORY_FRAGMENT)
        val setting = manager.findFragmentByTag(TAG_SETTING_FRAGMENT)

        hideFragment(ft, home, history, setting)

        if (tag == TAG_HOME_FRAGMENT) if (home != null) ft.show(home)
        if (tag == TAG_HISTORY_FRAGMENT) if (history != null) ft.show(history)
        if (tag == TAG_SETTING_FRAGMENT) if (setting != null) ft.show(setting)
        ft.commitAllowingStateLoss()
    }

    private fun hideFragment(ft: FragmentTransaction, home: Fragment?, history: Fragment?, setting: Fragment?) {
        if (home != null) ft.hide(home)
        if (history != null) ft.hide(history)
        if (setting != null) ft.hide(setting)
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
        BookingHistoryDto.deleteAll()
        dBController.closeDB()
    }

    companion object {
        const val SETTING_PREFERENCE_KEY = "setting"

        private const val DENIED_PERMISSION_MESSAGE = "알림 권한이 차단되었습니다"
        private const val PERMIT_PERMISSION_MESSAGE = "알림 권한이 허용되었습니다"
        private const val LOWER_VERSION_MESSAGE = "버전이 낮아 권한이 자동으로 허용되었습니다"
    }
}
