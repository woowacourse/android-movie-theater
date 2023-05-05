package woowacourse.movie.activity.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.switchmaterial.SwitchMaterial
import woowacourse.movie.BundleKeys.IS_CAN_PUSH_CHECKED
import woowacourse.movie.BundleKeys.REQUEST_NOTIFICATION_PERMISSION
import woowacourse.movie.DataRepository
import woowacourse.movie.PermissionManager
import woowacourse.movie.PermissionManagerImpl
import woowacourse.movie.R
import woowacourse.movie.SharedPreferenceUtil
import woowacourse.movie.fragment.bookhistory.BookHistoryFragment
import woowacourse.movie.fragment.home.HomeFragment
import woowacourse.movie.fragment.setting.SettingFragment
import woowacourse.movie.fragment.setting.SettingFragmentFactory

class MainActivity :
    AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener,
    MainContract.View {

    override lateinit var presenter: MainContract.Presenter
    private val fragments = mutableMapOf<Int, Fragment>()
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            progressIsGrantedNotificationPermission()
        )
    private val permissionManager: PermissionManager =
        PermissionManagerImpl(this, requestPermissionLauncher)
    private val sharedPreferenceRepository: DataRepository by lazy {
        SharedPreferenceUtil(this)
    }
    lateinit var settingFragmentFactory: SettingFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        settingFragmentFactory = SettingFragmentFactory(requestPermissionLauncher)
        supportFragmentManager.fragmentFactory = settingFragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, sharedPreferenceRepository)

        requestNotificationPermission()
        findViewById<BottomNavigationView>(R.id.bnv_main).setOnItemSelectedListener(this)
        supportFragmentManager.commit {
            add(R.id.fl_main, fragments.getOrPut(R.id.page_book_history) { BookHistoryFragment() })
        }
        setRequestPermissionListener()
    }

    private fun setRequestPermissionListener() {
        supportFragmentManager.setFragmentResultListener(
            REQUEST_NOTIFICATION_PERMISSION, this
        ) { _, bundle ->
            if (bundle.getBoolean(IS_CAN_PUSH_CHECKED)) requestPermissionLauncher.launch(
                POST_NOTIFICATIONS
            )
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionManager.requestPermission(
                permission = POST_NOTIFICATIONS,
                ifDeniedDescription = this.getString(R.string.if_permission_is_denied_cant_use_notification_service)
            )
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> {
                replaceFragment(fragments.getOrPut(R.id.page_book_history) { BookHistoryFragment() })
                return true
            }
            R.id.page_home -> {
                replaceFragment(fragments.getOrPut(R.id.page_home) { HomeFragment() })
                return true
            }
            R.id.page_setting -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    SettingFragment::class.java.name
                )
                replaceFragment(fragment)
                return true
            }
        }
        return false
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fl_main, fragment)
        }
    }

    private fun progressIsGrantedNotificationPermission() = { isGranted: Boolean ->
        Toast.makeText(
            this,
            if (isGranted) getString(R.string.notification_permission_is_granted) else
                getString(R.string.notification_permission_is_denied),
            Toast.LENGTH_SHORT
        ).show()
        presenter.saveSwitchData(isGranted)
    }

    override fun updatePushAlarmSwitch(wantChecked: Boolean) {
        supportFragmentManager.findFragmentById(R.id.fl_main)?.view?.findViewById<SwitchMaterial>(
            R.id.sw_setting_can_push
        )?.isChecked = wantChecked
    }
}
