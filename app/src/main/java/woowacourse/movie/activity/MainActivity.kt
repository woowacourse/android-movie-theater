package woowacourse.movie.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.fragment.MoviesFragment
import woowacourse.movie.fragment.ReservationListFragment
import woowacourse.movie.fragment.SettingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.main_fragment, ReservationListFragment())
        }
    }
}
