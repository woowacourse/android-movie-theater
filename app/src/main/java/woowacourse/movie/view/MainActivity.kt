package woowacourse.movie.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.contract.MainContract
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.presenter.MainPresenter
import woowacourse.movie.view.cinema.HomeFragment
import woowacourse.movie.view.reservation.ReservationHistoryFragment
import woowacourse.movie.view.setting.SettingFragment
import woowacourse.movie.view.util.ErrorMessage

class MainActivity :
    AppCompatActivity(),
    MainContract.View {
    private val presenter: MainContract.Presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main,
            )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.main = this
        binding.bottomNavigationViewMain.setOnItemSelectedListener { menuItem ->
            val screenId: Int =
                when (menuItem.itemId) {
                    R.id.item_menu_main_reservation_history -> SCREEN_ID_RESERVATION_HISTORY
                    R.id.item_menu_main_home -> SCREEN_ID_HOME
                    R.id.item_menu_main_setting -> SCREEN_ID_SETTING
                    else -> error(ErrorMessage("itemId").noSuch())
                }
            presenter.presentScreen(screenId)
            true
        }

        if (savedInstanceState == null) {
            presenter.presentScreen(SCREEN_ID_HOME)
            binding.bottomNavigationViewMain.selectedItemId = R.id.item_menu_main_home
        }
    }

    override fun updateScreen(screenId: Int) {
        val fragment =
            when (screenId) {
                SCREEN_ID_RESERVATION_HISTORY -> ReservationHistoryFragment()
                SCREEN_ID_HOME -> HomeFragment()
                SCREEN_ID_SETTING -> SettingFragment()
                else -> error(ErrorMessage("screenId").noSuch())
            }
        replaceWith(fragment)
    }

    private fun replaceWith(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_main, fragment)
        }
    }

    companion object {
        const val SCREEN_ID_RESERVATION_HISTORY = 0
        const val SCREEN_ID_HOME = 1
        const val SCREEN_ID_SETTING = 2
    }
}
