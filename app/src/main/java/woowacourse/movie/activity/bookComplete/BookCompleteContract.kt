package woowacourse.movie.activity.bookComplete

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import woowacourse.movie.model.MovieBookingSeatInfoUIModel

interface BookCompleteContract {

    interface View {
        var presenter: Presenter
        fun initView(movieBookingSeatInfo: MovieBookingSeatInfoUIModel)
        fun displayToastIfDummyData()
    }

    interface Presenter {
        val movieBookingSeatInfo: MovieBookingSeatInfo
        fun initView()
        fun hasDummyData()
    }
}
