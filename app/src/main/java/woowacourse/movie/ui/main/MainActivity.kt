package woowacourse.movie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import woowacourse.movie.R
import woowacourse.movie.ui.fragment.reservationList.ReservationListFragment

class MainActivity : AppCompatActivity() {
    private val rv: FragmentContainerView by lazy { findViewById(R.id.container) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(rv.id, ReservationListFragment())
            .commit()
    }

    companion object {
        internal const val KEY_MOVIE = "key_movie"
        internal const val KEY_ADV = "key_adb"
    }
}
