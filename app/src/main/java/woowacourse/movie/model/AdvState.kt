package woowacourse.movie.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdvState(
    val imageUri: Uri,
    val advDescription: String
) : Parcelable
