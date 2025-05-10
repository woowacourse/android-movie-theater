package woowacourse.movie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMovieBinding
import woowacourse.movie.main.MainContract
import woowacourse.movie.movie.MovieFragment
import woowacourse.movie.reservation.ReservationListFragment
import woowacourse.movie.setting.SettingFragment

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter
    private lateinit var binding: ActivityMovieBinding
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        setUpUi()

        val permissionHandler = MoviePermissionHandler(this)
        presenter = MainPresenter(this, permissionHandler)

        sharedPreference = getSharedPreferences("settings", MODE_PRIVATE)
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.menu_home
            supportFragmentManager.commit {
                add(R.id.fragment_view, MovieFragment())
            }
        }

        initBottomNav()
        presenter.requestExactAlarmPermission()
        presenter.requestSettingAlarmPermission()
        createNotificationChannel()
    }

    private fun setUpUi() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    private fun initBottomNav() {
        val homeFragment = MovieFragment()
        val settingFragment = SettingFragment()
        val reservationListFragment = ReservationListFragment()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    replaceFragment(homeFragment)
                }

                R.id.menu_setting -> {
                    replaceFragment(settingFragment)
                }

                R.id.menu_reserve_list -> {
                    replaceFragment(reservationListFragment)
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_view, fragment)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (!isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
                ) {
                    showSettingAlarmDialog()
                }
            }
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel =
                NotificationChannel(
                    ALARM_CHANNEL_ID,
                    ALARM_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT,
                )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun shouldShowNotificationRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun requestNotificationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun showSettingAlarmDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_alarm_request)
            .setMessage(R.string.permission_alarm_request_sub_info)
            .setPositiveButton(R.string.permission_exact_alarm_allow) { _, _ ->
                moveToAppSetting()
            }
            .setNegativeButton(R.string.permission_exact_alarm_not_allow) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun moveToAppSetting() {
        val intent =
            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun showExactAlarmDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_exact_alarm_main_info)
            .setMessage(R.string.permission_exact_alarm_sub_info)
            .setPositiveButton(R.string.permission_exact_alarm_allow) { _, _ ->
                val intent =
                    Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        .setData("package:$packageName".toUri())
                startActivity(intent)
            }
            .setNegativeButton(R.string.dig_btn_negative_message) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    companion object {
        const val ALARM_CHANNEL_ID = "reservation_channel_id"
        private const val ALARM_NAME = "Movie"
    }
}
