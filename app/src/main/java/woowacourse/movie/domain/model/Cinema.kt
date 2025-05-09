package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cinema(
    val id: Int,
    val name: String,
) : Parcelable
