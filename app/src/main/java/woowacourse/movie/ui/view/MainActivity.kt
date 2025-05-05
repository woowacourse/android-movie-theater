package woowacourse.movie.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.ui.contract.MainContract
import woowacourse.movie.ui.presenter.MainPresenter
import woowacourse.movie.ui.view.cinema.HomeFragment
import woowacourse.movie.ui.view.reservation.ReservationHistoryFragment
import woowacourse.movie.ui.view.setting.SettingFragment
import woowacourse.movie.ui.view.util.ErrorMessage

class MainActivity :
    AppCompatActivity(),
    MainContract.View {
    private val presenter: MainContract.Presenter = MainPresenter(this)
    private val homeFragment by lazy { HomeFragment() }
    private val reservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val settingFragment by lazy { SettingFragment() }

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
            val mainScreen: MainScreen =
                when (menuItem.itemId) {
                    R.id.item_menu_main_reservation_history -> MainScreen.RESERVATION_HISTORY
                    R.id.item_menu_main_home -> MainScreen.HOME
                    R.id.item_menu_main_setting -> MainScreen.SETTING
                    else -> error(ErrorMessage("itemId").noSuch())
                }
            presenter.presentScreen(mainScreen)
            true
        }

        if (savedInstanceState == null) {
            presenter.presentScreen(MainScreen.HOME)
            binding.bottomNavigationViewMain.selectedItemId = R.id.item_menu_main_home
        }
    }

    override fun updateScreen(mainScreen: MainScreen) {
        val fragment =
            when (mainScreen) {
                MainScreen.RESERVATION_HISTORY -> reservationHistoryFragment
                MainScreen.HOME -> homeFragment
                MainScreen.SETTING -> settingFragment
            }
        replaceWith(fragment)
    }

    private fun replaceWith(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_main, fragment)
        }
    }
}
