package woowacourse.movie.model.movie

class MovieTime {
    var value: Int = 0
        private set

    fun updateTime(newTime: Int) {
        value = newTime
    }
}
