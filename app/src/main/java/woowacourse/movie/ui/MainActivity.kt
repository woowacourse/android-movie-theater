package woowacourse.movie.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.dummy.DUMMY_ENTITY_MOVIES
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.history.view.BookingHistoryFragment
import woowacourse.movie.ui.movielist.view.MovieListFragment
import woowacourse.movie.ui.settings.view.SettingsFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        applyWindowInsets()

        thread {
            // TODO 테스트용 매번 테이블 삭제 코드
//            AppDatabase.getInstance(applicationContext).clearAllTables()
            AppDatabase.getInstance(applicationContext).movieDao().insertAll(*DUMMY_ENTITY_MOVIES)
        }

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            replaceFragmentContainer(MovieListFragment::class.java)
        }
        binding.navigation.selectedItemId = R.id.navigation_home
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragmentContainer(MovieListFragment::class.java)
                R.id.navigation_history -> replaceFragmentContainer(BookingHistoryFragment::class.java)
                R.id.navigation_settings -> replaceFragmentContainer(SettingsFragment::class.java)
                else -> false
            }
        }
    }

    private fun replaceFragmentContainer(fragment: Class<out Fragment>): Boolean {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_fragment_container_view, fragment, null)
        }
        return true
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
