package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Theater(
    val name: String = "",
) : Parcelable {
    companion object {
        val theater0 = Theater("CGV명동")
        val theater1 = Theater("CGV동대문")
        val theater2 = Theater("CGV청담씨네시티")
        val theater3 = Theater("CGV명동역 씨네라이브러리")
        val theater4 = Theater("CINE de CHEF 용산아이파크몰")
    }
}
