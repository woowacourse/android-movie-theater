package woowacourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.reservationlist.ReservationListFragment
import woowacourse.movie.screeningmovie.ScreeningMovieFragment
import woowacourse.movie.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnvMain.selectedItemId = R.id.menu_home
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: navigateTo<ScreeningMovieFragment>()
        setNavigation()
    }

    private fun setNavigation() {
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> navigateTo<ScreeningMovieFragment>()
                R.id.menu_setting -> navigateTo<SettingFragment>()

                R.id.menu_reservation_list -> navigateTo<ReservationListFragment>()

                else -> navigateTo<ScreeningMovieFragment>()
            }
            return@setOnItemSelectedListener true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.simpleName)
        }
    }
}
