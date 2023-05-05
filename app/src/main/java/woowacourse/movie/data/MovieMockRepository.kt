package woowacourse.movie.data

import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.Schedule
import woowacourse.movie.domain.repository.MovieRepository
import java.time.LocalDate
import java.time.LocalTime

object MovieMockRepository : MovieRepository {
    private val movies = listOf(
        Movie(
            "스즈메의 문단속",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            Minute(122),
            "“이 근처에 폐허 없니? 문을 찾고 있어” 규슈의 한적한 마을에 살고 있는 소녀 ‘스즈메’는 문을 찾아 여행 중인 청년 ‘소타’를 만난다. " +
                "그의 뒤를 쫓아 산속 폐허에서 발견한 낡은 문. ‘스즈메’가 무언가에 이끌리듯 문을 열자 마을에 재난의 위기가 닥쳐오고 가문 대대로 문 너머의 재난을 봉인하는 ‘소타’를 도와 간신히 문을 닫는다. " +
                "“닫아야만 하잖아요, 여기를!” 재난을 막았다는 안도감도 잠시, 수수께끼의 고양이 ‘다이진’이 나타나 ‘소타’를 의자로 바꿔 버리고 일본 각지의 폐허에 재난을 부르는 문이 열리기 시작하자 ‘스즈메’는 의자가 된 ‘소타’와 함께 재난을 막기 위한 여정에 나선다." +
                "“꿈이 아니었어” 규슈, 시코쿠, 고베, 도쿄 재난을 막기 위해 일본 전역을 돌며 필사적으로 문을 닫아가던 중 어릴 적 고향에 닿은 ‘스즈메’는 잊고 있던 진실과 마주하게 되는데…",
            Schedule(
                mapOf(
                    "정말아주아주아주아주아주아주아주긴극장이름" to listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
                    "선릉 극장" to listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
                    "잠실 극장" to listOf(LocalTime.of(9, 0)),
                    "강남 극장" to listOf(LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(16, 0))
                )
            ),
        ),
        Movie(
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 4, 15),
            Minute(152),
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            Schedule(
                mapOf(
                    "선릉 극장" to listOf(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
                    "잠실 극장" to listOf(LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(14, 0)),
                ),
            ),
        ),
        Movie(
            "스타워즈",
            LocalDate.of(2024, 4, 16),
            LocalDate.of(2024, 4, 30),
            Minute(152),
            "디즈니+ 오리지널 스타워즈 실사 드라마 만달로리안 시리즈의 세 번째 시즌. 본격적으로 만달로어인들이 결집하는 과정을 그리고 있는 시즌이다.",
            Schedule(
                mapOf(
                    "선릉 극장" to listOf(LocalTime.of(9, 0), LocalTime.of(11, 0), LocalTime.of(15, 0)),
                    "강남 극장" to listOf(LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(16, 0))
                ),
            ),
        ),
        Movie(
            "어벤져스: 엔드게임",
            LocalDate.of(2024, 5, 1),
            LocalDate.of(2024, 5, 30),
            Minute(152),
            "어벤져스 실사영화 시리즈의 4번째 작품이자, 마블 시네마틱 유니버스의 스물두번째 작품이며, 페이즈 3의 10번째 작품이자 지난 2008년, 아이언맨을 시작으로 장장 11년 동안 이어져 왔던 인피니티 사가의 최종편.",
            Schedule(
                mapOf(
                    "선릉 극장" to listOf(LocalTime.of(9, 0), LocalTime.of(11, 0), LocalTime.of(15, 0)),
                    "잠실 극장" to listOf(LocalTime.of(11, 0), LocalTime.of(14, 0)),
                ),
            ),
        ),
    )

    override fun findAll(): List<Movie> {
        return movies.toList()
    }
}
