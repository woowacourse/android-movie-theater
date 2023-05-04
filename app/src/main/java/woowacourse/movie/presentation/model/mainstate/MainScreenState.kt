package woowacourse.movie.presentation.model.mainstate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class MainScreenState : Parcelable {
    object History : MainScreenState()
    object Home : MainScreenState()
    object Setting : MainScreenState()
}
