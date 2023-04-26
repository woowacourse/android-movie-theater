package woowacourse.movie.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import woowacourse.movie.R
import woowacourse.movie.presentation.movielist.MovieListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("멧돼지","멧돼지")
        test()
        Log.d("멧돼지","멧돼지")

    }

    private fun test() {
        supportFragmentManager.commit {
            add(R.id.main_fragment_container, MovieListFragment())
        }
    }
}
