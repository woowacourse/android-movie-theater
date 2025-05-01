package woowacourse.movie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import woowacourse.movie.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        binding.navView.selectedItemId = R.id.navigation_home
        setFrag(R.id.navigation_home)

        binding.navView.setOnItemSelectedListener { item ->
            setFrag(item.itemId)
            true
        }
    }

    private fun setFrag(itemId: Int) {
        val fragment = when (itemId) {
            R.id.navigation_booking -> BookingFragment()
            R.id.navigation_home -> HomeFragment()
            R.id.navigation_settings -> SettingFragment()
            else -> throw IllegalStateException()
        }
        supportFragmentManager.commit {
            replace(R.id.main_frame, fragment)
        }
    }

}
