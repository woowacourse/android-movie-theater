package woowacourse.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.room.Room
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.toEntity
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.reservationDetails.ReservationDetailsFragment
import woowacourse.movie.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db =
            Room
                .databaseBuilder(applicationContext, AppDatabase::class.java, "movie")
                .fallbackToDestructiveMigration()
                .build()

        val movieDao = db.reservationDao()

        Thread {
            val entities = Movie.values.map { it.toEntity() }
            entities.forEach { movieDao.insertMovie(it) }
        }.start()
        binding.mainBottomNavigationBar.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.bottom_navigation_reservation_details -> {
                    replaceFragment<ReservationDetailsFragment>()
                    true
                }

                R.id.bottom_navigation_home -> {
                    replaceFragment<HomeFragment>()
                    true
                }

                R.id.bottom_navigation_setting -> {
                    replaceFragment<SettingFragment>()
                    true
                }

                else -> false
            }
        }
    }

    private inline fun <reified T : Fragment> replaceFragment() {
        val tag: String = T::class.java.name
        val fragment =
            supportFragmentManager.findFragmentByTag(tag) as? T ?: T::class.java.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.fragments.forEach { hide(it) }
            if (fragment.isAdded) {
                show(fragment)
                return
            }
            add(binding.mainFragmentContainer.id, fragment, tag)
        }
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
    }
}
