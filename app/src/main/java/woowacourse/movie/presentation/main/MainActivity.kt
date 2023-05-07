package woowacourse.movie.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.presentation.bookedticketlist.BookedTicketsFragment
import woowacourse.movie.presentation.movielist.movie.MovieListFragment
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

        initBottomNavigation()
        requestNotificationPermission()
        setInitialFragment()
    }

    @SuppressLint("InlinedApi")
    private fun requestNotificationPermission() {
        if (!applicationContext.checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun setInitialFragment() {
        this.replace<MovieListFragment>(R.id.main_fragment_container)
    }

    private fun initBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigationMain)

        bottomNavigation.selectedItemId = R.id.action_home

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
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
