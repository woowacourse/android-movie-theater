package woowacourse.movie.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import woowacourse.movie.R
import woowacourse.movie.fragment.setting.SettingFragment
import woowacourse.movie.fragment.bookhistory.BookHistoryFragment
import woowacourse.movie.fragment.movielist.HomeFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private val bookHistoryFragment: Fragment by lazy { BookHistoryFragment() }
    private val homeFragment: Fragment by lazy { HomeFragment() }
    private val settingFragment: Fragment by lazy { SettingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.navigation_main).setOnItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.framelayout_main, bookHistoryFragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_book_history -> return onChangedFragment(bookHistoryFragment)
            R.id.page_home -> return onChangedFragment(homeFragment)
            R.id.page_setting -> return onChangedFragment(settingFragment)
        }
        return false
    }

    private fun onChangedFragment(item: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout_main, item).commitAllowingStateLoss()
        return true
    }
}
