package woowacourse.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.view.main.booklist.BookListFragment
import woowacourse.movie.presentation.view.main.home.MovieListFragment
import woowacourse.movie.presentation.view.main.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_book_list -> changeFragment(BookListFragment())
                R.id.action_home -> changeFragment(MovieListFragment())
                R.id.action_settings -> changeFragment(SettingFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment).commit()
    }
}
