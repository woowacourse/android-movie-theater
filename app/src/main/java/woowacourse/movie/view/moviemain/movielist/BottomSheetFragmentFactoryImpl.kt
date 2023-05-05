package woowacourse.movie.view.moviemain.movielist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import woowacourse.movie.view.model.MovieUiModel

class BottomSheetFragmentFactoryImpl(private val movie: MovieUiModel) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            TheaterBottomSheetFragment::class.java.name -> TheaterBottomSheetFragment(movie)
            else -> super.instantiate(classLoader, className)
        }
    }
}
