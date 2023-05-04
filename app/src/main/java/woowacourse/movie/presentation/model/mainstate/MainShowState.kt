package woowacourse.movie.presentation.model.mainstate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MainShowState : Parcelable {
    object History : MainShowState()
    object Home : MainShowState()
    object Setting : MainShowState()
}
