package woowacourse.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.app.ui.main.reservation.BookingHistoryFragment
import woowacourse.app.ui.main.setting.SettingFragment
import woowacourse.movie.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.main_bottom_navi)
        navigation.selectedItemId = R.id.action_menu_home
        navigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.action_booking_history ->
                    return@setOnItemSelectedListener replaceFragment(BookingHistoryFragment())
                R.id.action_menu_home ->
                    return@setOnItemSelectedListener replaceFragment(woowacourse.app.ui.main.home.Fragment())
                R.id.action_menu_setting ->
                    return@setOnItemSelectedListener replaceFragment(SettingFragment())
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment): Boolean {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, fragment)
        }
        return true
    }
}
