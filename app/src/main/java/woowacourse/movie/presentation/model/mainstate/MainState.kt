package woowacourse.movie.presentation.model.mainstate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainState(
    val latestScreen: MainScreenState = MainScreenState.Home,
    val isShownHistory: Boolean = false,
) : Parcelable
