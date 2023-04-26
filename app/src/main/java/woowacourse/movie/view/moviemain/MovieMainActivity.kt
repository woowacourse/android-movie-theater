package woowacourse.movie.view.moviemain

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.movielist.MovieListFragment
import woowacourse.movie.view.moviemain.reservationlist.ReservationListFragment
import woowacourse.movie.view.moviemain.setting.SettingFragment
import woowacourse.movie.view.seatselection.AlarmReceiver

class MovieMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_main)

        createChannel()

        requestNotificationPermission()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation_view)

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation_list -> {
                    replaceFragment(ReservationListFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    replaceFragment(MovieListFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_setting -> {
                    replaceFragment(SettingFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        navigation.selectedItemId = R.id.action_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun createChannel() {
        val name = "Reservation Notification"
        val channel = NotificationChannel(
            AlarmReceiver.CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Toast.makeText(this, "권한을 설정해주셔야 알람을 받으실 수 있습니다.", Toast.LENGTH_LONG).show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) Toast.makeText(this, "권한을 설정해주셨습니다!", Toast.LENGTH_LONG).show()
    }
}
