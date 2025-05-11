package woowacourse.movie.view

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.ReservationDatabase
import woowacourse.movie.contract.MainContract
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.data.reservation.LocalReservationData
import woowacourse.movie.data.reservation.ReservationData
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presenter.MainPresenter
import woowacourse.movie.view.cinema.HomeFragment
import woowacourse.movie.view.reservation.ReservationHistoryFragment
import woowacourse.movie.view.setting.SettingFragment
import woowacourse.movie.view.util.ErrorMessage

class MainActivity :
    AppCompatActivity(),
    MainContract.View,
    ReservationDataProvider,
    ApplicationSettingProvider {
    private val presenter: MainContract.Presenter = MainPresenter(this)
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val reservationData: ReservationData by lazy {
        val dao = ReservationDatabase.create(applicationContext).reservationDao()
        LocalReservationData(dao)
    }
    private val applicationSettings by lazy { ApplicationSettings(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestNotificationPermission()
        bindData()
        initViews(isFirstEntry(savedInstanceState))
        initEventListeners()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 권한 요청 거부한 경우
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                // 안드로이드 12 이하는 Notification에 관한 권한 필요 없음
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            applicationSettings.notificationEnabled = isGranted
        }

    private fun isFirstEntry(savedInstanceState: Bundle?): Boolean = savedInstanceState == null

    private fun bindData() {
        binding.main = this
    }

    private fun initViews(isFirstEntry: Boolean) {
        binding.bottomNavigationViewMain.selectedItemId = R.id.item_menu_main_home

        if (isFirstEntry) {
            presenter.presentScreen(SCREEN_ID_HOME)
        }
    }

    private fun initEventListeners() {
        initItemSelectedListener()
    }

    private fun initItemSelectedListener() {
        binding.bottomNavigationViewMain.setOnItemSelectedListener { menuItem ->
            val screenId: Int =
                when (menuItem.itemId) {
                    R.id.item_menu_main_reservation_history -> SCREEN_ID_RESERVATION_HISTORY
                    R.id.item_menu_main_home -> SCREEN_ID_HOME
                    R.id.item_menu_main_setting -> SCREEN_ID_SETTING
                    else -> error(ErrorMessage("itemId").noSuch())
                }
            presenter.presentScreen(screenId)
            true
        }
    }

    override fun updateScreen(screenId: Int) {
        val fragment =
            when (screenId) {
                SCREEN_ID_RESERVATION_HISTORY -> ReservationHistoryFragment()
                SCREEN_ID_HOME -> HomeFragment()
                SCREEN_ID_SETTING -> SettingFragment()
                else -> error(ErrorMessage("screenId").noSuch())
            }
        replaceWith(fragment)
    }

    private fun replaceWith(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_main, fragment)
        }
    }

    override fun provideReservationData(): ReservationData = reservationData

    override fun provideApplicationSetting(): ApplicationSettings = applicationSettings

    companion object {
        private const val SCREEN_ID_RESERVATION_HISTORY = 0
        private const val SCREEN_ID_HOME = 1
        private const val SCREEN_ID_SETTING = 2
    }
}

interface ReservationDataProvider {
    fun provideReservationData(): ReservationData
}

interface ApplicationSettingProvider {
    fun provideApplicationSetting(): ApplicationSettings
}
