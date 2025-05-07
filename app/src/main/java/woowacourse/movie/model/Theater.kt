package woowacourse.movie.model

class Theater(
    val place: String,
    val screeningInfos: List<ScreeningInfo>,
) {
    init {
        require(place.isNotEmpty()) {
            ERROR_EMPTY_THEATER
        }
    }

    companion object {
        private const val ERROR_EMPTY_THEATER = "극장은 비어있을 수 없습니다"
    }
}
