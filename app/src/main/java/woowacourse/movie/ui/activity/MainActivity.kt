package woowacourse.movie.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.FragmentType
import woowacourse.movie.ui.fragment.movielist.HomeFragment
import woowacourse.movie.ui.fragment.reservationlist.ReservationListFragment
import woowacourse.movie.ui.fragment.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.main_bottom_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemId = savedInstanceState?.getInt(KEY_INSTANCE_ITEM_ID) ?: R.id.bottom_item_home
        initFragment(itemId)

        bottomNavigationView.setOnItemSelectedListener {
            val type = getFragmentType(it.itemId)
            changeFragment(type)
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INSTANCE_ITEM_ID, bottomNavigationView.selectedItemId)
    }

    private fun initFragment(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
        changeFragment(getFragmentType(itemId))
    }

    private fun getFragmentType(itemId: Int): FragmentType = when (itemId) {
        R.id.bottom_item_list -> FragmentType.RESERVATION_LIST
        R.id.bottom_item_home -> FragmentType.HOME
        R.id.bottom_item_settings -> FragmentType.SETTING
        else -> throw IllegalArgumentException(getString(R.string.error_not_existing_item_id))
    }

    private fun changeFragment(currentType: FragmentType) {
        val transaction = supportFragmentManager.beginTransaction()

        val fragment =
            supportFragmentManager.findFragmentByTag(currentType.tag) ?: createFragment(currentType).apply {
                transaction.add(R.id.main_fragment_container_view, this, currentType.tag)
            }
        transaction.show(fragment)

        FragmentType.values()
            .filterNot { it == currentType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let { transaction.hide(it) }
            }

        transaction.commitAllowingStateLoss()
    }

    private fun createFragment(currentType: FragmentType): Fragment {
        return when (currentType) {
            FragmentType.RESERVATION_LIST -> ReservationListFragment()
            FragmentType.HOME -> HomeFragment()
            FragmentType.SETTING -> SettingsFragment()
        }
    }

    companion object {
        private const val KEY_INSTANCE_ITEM_ID = "item_id"
    }
}
