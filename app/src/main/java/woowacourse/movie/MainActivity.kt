package woowacourse.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presentation.bookinglist.BookingListFragment
import woowacourse.movie.presentation.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment
import woowacourse.movie.ui.DataBindingBaseActivity

class MainActivity : DataBindingBaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreen(binding.root)
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_list -> switchFragment<BookingListFragment>()
                    R.id.action_home -> switchFragment<MoviesFragment>()
                    R.id.action_settings -> switchFragment<SettingFragment>()
                }
                true
            }
            selectedItemId = R.id.action_home
        }
    }

    private inline fun <reified T : Fragment> switchFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<T>(binding.mainContainer.id)
        }
    }
}
