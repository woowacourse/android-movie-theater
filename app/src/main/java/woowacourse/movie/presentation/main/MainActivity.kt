package woowacourse.movie.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.broadcastreceiver.AlarmReceiver
import woowacourse.movie.presentation.bookedticketlist.BookedTicketsFragment
import woowacourse.movie.presentation.movielist.MovieListFragment
import woowacourse.movie.presentation.settings.SettingsFragment
import woowacourse.movie.presentation.util.checkPermissionTiramisu
import woowacourse.movie.presentation.util.replace

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitialFragment()
        initBottomNavigation()
        requestNotificationPermission()
        initAlarmReceiver()
    }

    @SuppressLint("InlinedApi")
    private fun requestNotificationPermission() {
        if (!applicationContext.checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun initAlarmReceiver() {
        val myReceiver = AlarmReceiver()
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
        }
        registerReceiver(myReceiver, filter)
    }

    private fun setInitialFragment() {
        this.supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MovieListFragment>(R.id.main_fragment_container)
        }
    }

    private fun initBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationMain)
        bottomNavigation.selectedItemId = R.id.action_home

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                bottomNavigation.selectedItemId -> {
                    return@setOnItemSelectedListener true
                }
                R.id.action_booked_tickets -> {
                    this.replace<BookedTicketsFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.action_home -> {
                    this.replace<MovieListFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.action_settings -> {
                    this.replace<SettingsFragment>(R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                else -> throw IllegalStateException(BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR)
            }
        }
    }

    companion object {
        private const val BOTTOM_NAVIGATION_WRONG_ITEM_ID_ERROR = "설정된 아이템 제외한 아이템 아이디가 전달되었습니다."
    }
}
