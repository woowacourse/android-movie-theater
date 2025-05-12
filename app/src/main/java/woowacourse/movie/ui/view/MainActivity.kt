package woowacourse.movie.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.view.history.ReservationHistoryFragment
import woowacourse.movie.ui.view.screening.ScreeningFragment
import woowacourse.movie.ui.view.setting.SettingFragment
import woowacourse.movie.ui.view.util.ErrorMessage

class MainActivity :
    AppCompatActivity(),
    MainContract.View {
    private val presenter: MainContract.Presenter = MainPresenter(this)
    private val screeningFragment by lazy { ScreeningFragment() }
    private val reservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val settingFragment by lazy { SettingFragment() }
    private lateinit var binding: ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main,
            )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBottomNavigation()

        if (savedInstanceState == null) {
            initBottomNavigation()
        }
    }

    override fun onResume() {
        super.onResume()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLaunch()
        }
    }

    private fun requestPermissionLaunch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun initBottomNavigation() {
        presenter.presentScreen(MainScreen.HOME)
        binding.bottomNavigationViewMain.selectedItemId = R.id.item_menu_main_home
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationViewMain.setOnItemSelectedListener { menuItem ->
            val mainScreen: MainScreen =
                when (menuItem.itemId) {
                    R.id.item_menu_main_reservation_history -> MainScreen.RESERVATION_HISTORY
                    R.id.item_menu_main_home -> MainScreen.HOME
                    R.id.item_menu_main_setting -> MainScreen.SETTING
                    else -> error(ErrorMessage("itemId").noSuch())
                }
            presenter.presentScreen(mainScreen)
            true
        }
    }

    override fun updateScreen(mainScreen: MainScreen) {
        val fragment =
            when (mainScreen) {
                MainScreen.RESERVATION_HISTORY -> reservationHistoryFragment
                MainScreen.HOME -> screeningFragment
                MainScreen.SETTING -> settingFragment
            }
        replaceWith(fragment)
    }

    private fun replaceWith(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_main, fragment)
        }
    }
}
