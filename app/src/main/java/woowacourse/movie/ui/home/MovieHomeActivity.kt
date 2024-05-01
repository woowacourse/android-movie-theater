package woowacourse.movie.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.ui.base.BaseActivity
import woowacourse.movie.ui.home.adapter.MovieContentAdapter

class MovieHomeActivity : BaseActivity<MovieHomeContract.Presenter>(), MovieHomeContract.View {
    private val movieContentList: RecyclerView by lazy { findViewById(R.id.movie_content_list) }

    override fun initializePresenter() = MovieHomePresenter(this, MovieContentsImpl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_home)

        presenter.loadMovieContents()
    }

    override fun showMovieContents(movieContents: List<MovieContent>) {
        movieContentList.adapter =
            MovieContentAdapter(movieContents) { view, id ->
                val fragment = TheaterSelectionBottomSheetFragment()
                val bundle = Bundle()
                bundle.putLong(MovieHomeKey.ID, id)
                fragment.arguments = bundle
                fragment.show(
                    supportFragmentManager.apply {
                        setFragmentResult(MovieHomeKey.FRAGMENT_REQUEST_KEY, bundle)
                    },
                    fragment.tag,
                )
            }
    }
}
