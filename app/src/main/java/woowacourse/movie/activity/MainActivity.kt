package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import woowacourse.movie.R
import woowacourse.movie.fragment.BookHistoryFragment
import woowacourse.movie.fragment.HomeFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bnv_main).setOnItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.fl_main, BookHistoryFragment()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_main, BookHistoryFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_main, HomeFragment()).commitAllowingStateLoss()
                return true
            }
            R.id.page_setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_main, SettingFragment()).commitAllowingStateLoss()
                return true
            }
        }

        return false
    }
}
