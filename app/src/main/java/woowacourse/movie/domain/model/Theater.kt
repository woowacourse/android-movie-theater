package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Theater(
    val name: String = "",
) : Parcelable {
    companion object {
        val theater0 = Theater("선릉")
        val theater1 = Theater("잠실")
        val theater2 = Theater("강남")
        val theater3 = Theater("성수")
        val theater4 = Theater("홍대")
    }
}
