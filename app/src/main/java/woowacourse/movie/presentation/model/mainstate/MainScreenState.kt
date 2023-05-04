package woowacourse.movie.presentation.model.mainstate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainScreenState(
    val latestScreen: MainShowState = MainShowState.Home,
    val isShownHistory: Boolean = false,
) : Parcelable
