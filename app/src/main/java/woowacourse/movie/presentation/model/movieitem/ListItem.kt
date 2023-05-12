package woowacourse.movie.presentation.model.movieitem

import android.os.Parcelable

interface ListItem : Parcelable {
    fun isAd(): Boolean
}
