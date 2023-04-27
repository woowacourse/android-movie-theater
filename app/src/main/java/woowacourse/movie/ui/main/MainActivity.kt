package woowacourse.movie.ui.main

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
import woowacourse.movie.ui.seat.NotiChannel
import woowacourse.movie.util.shortToast

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                shortToast("권한 요청을 거부하였습니다")
            } else {
                shortToast("권한 요청을 승인하였습니다")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
        requestNotificationPermission()
        createChannel()
    }

    private fun initNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.main_bottom_navi)
        navigation.selectedItemId = R.id.action_menu_home
        navigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_booking_history ->
                    return@setOnItemSelectedListener replaceFragment(BookingHistoryFragment())
                R.id.action_menu_home ->
                    return@setOnItemSelectedListener replaceFragment(HomeFragment())
                R.id.action_menu_setting ->
                    return@setOnItemSelectedListener replaceFragment(SettingFragment())
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, fragment)
        }
        return true
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun createChannel() {
        val mChannel = NotificationChannel(
            NotiChannel.BOOKING_ALARM.channelName,
            NotiChannel.BOOKING_ALARM.channelName,
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = NotiChannel.BOOKING_ALARM.channelDescription
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
}
