package woowacourse.movie.data

import com.woowacourse.domain.ScreeningSchedule
import com.woowacourse.domain.Theater
import com.woowacourse.domain.movie.Movie
import woowacourse.movie.R
import java.time.LocalDate
import java.time.LocalTime

object MovieMockData {
    private val movies = listOf(
        Movie(
            R.drawable.harry_potter,
            "해리포터",
            123,
            "해리 포터(다니엘 래드클리프)는 위압적인 버논 이모부(리처드 그리피스 )와 냉담한 이모 페투니아 (피오나 쇼), 욕심 많고 버릇없는 사촌 두들리(해리 멜링 ) 밑에서 갖은 구박을 견디며 계단 밑 벽장에서 생활한다. 11살 생일을 며칠 앞둔 어느 날 해리에게 초록색 잉크로 쓰여진 한 통의 편지가 배달되지만 버논 이모부가 편지를 중간에서 가로챈다. 하지만 결국 해리의 생일을 축하하러 온 거인 혼혈 해그리드(로비 콜트레인)가 해리에게 편지를 전해주었는데, 그 편지는 바로 호그와트 입학 통지서였다. 그리고 거인 해그리드는 해리가 마법사라는 사실도 알려주었다.\n" +
                "\n" +
                "해리는 호그와트를 선택한다. 런던의 킹스 크로스 역에 있는 비밀의 9와 3/4 승강장에서 호그와트 급행열차를 탄 해리는 열차 안에서 같은 호그와트 마법학교 입학생인와 론 위즐리 (루퍼트 그린트), 헤르미온느 그레인저(엠마 왓슨)을 만나 친구가 된다. 이들과 함께 호그와트에 입학한 해리는, 놀라운 모험의 세계를 경험하며 갖가지 신기한 마법들을 배워 나간다. 또한 빗자루를 타고 공중을 날아다니며 경기하는 스릴 만점의 퀴디치 게임에서 스타로 탄생하게 된다. 그러던 어느 날 해리는 호그와트 지하실에 '마법사의 돌'이 비밀리에 보관되어 있다는 것을 알게되고, 해리의 부모님을 살해한 볼드모트(레이프 파인즈)가 그 돌을 노린다는 사실도 알게 된다. 해리는 볼드모트로부터 마법사의 돌과 호그와트 마법학교를 지키기 위해 필사의 노력을 해서 호그와트를 지킨다. 하지만 마법사의 돌이 깨져, 니콜라스 플라멜이 죽고 만다. 대신 여분의 약을 남겨뒀다. 그래서 바로 죽지는 않았다.",
            LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 5, 10),
        ),
        Movie(
            R.drawable.interstellar,
            "인터스텔라",
            169,
            "세계 각국의 정부와 경제가 완전히 붕괴된 미래가 다가온다. 지난 20세기에 범한 잘못이 전 세계적인 식량 부족을 불러왔고, NASA도 해체되었다. 이때 시공간에 불가사의한 틈이 열리고, 남은 자들에게는 이 곳을 탐험해 인류를 구해야 하는 임무가 지워진다. 사랑하는 가족들을 뒤로 한 채 인류라는 더 큰 가족을 위해, 그들은 이제 희망을 찾아 우주로 간다. 그리고 우린 답을 찾을 것이다. 늘 그랬듯이…",
            LocalDate.of(2023, 4, 4),
            LocalDate.of(2023, 7, 4),
        ),
        Movie(
            R.drawable.ironman,
            "아이언맨",
            150,
            "하이테크 슈퍼 히어로의 탄생 | 이제 업그레이드는 끝났다 천재적인 두뇌와 재능으로 세계 최강의 무기업체를 이끄는 CEO이자, 타고난 매력으로 셀러브리티 못지않은 화려한 삶을 살아가던 억만장자 토니 스타크. 아프가니스탄에서 자신이 개발한 신무기 발표를 성공리에 마치고 돌아가던 그는 게릴라군의 갑작스런 공격에 의해 가슴에 치명적인 부상을 입고 게릴라군에게 납치된다. 가까스로 목숨을 건진 그에게 게릴라군은 자신들을 위한 강력한 무기를 개발하라며 그를 위협한다. 그러나 그는 게릴라군을 위한 무기 대신, 탈출을 위한 무기가 장착된 철갑수트를 몰래 만드는 데 성공하고, 그의 첫 수트인 ‘Mark1’를 입고 탈출에 성공한다. 미국으로 돌아온 토니 스타크는 자신이 만든 무기가 많은 사람들의 생명을 위협하고, 세상을 엄청난 위험에 몰아넣고 있다는 사실을 깨닫고 무기사업에서 손 뗄 것을 선언한다. 그리고, Mark1을 토대로 최강의 하이테크 수트를 개발하는 데 자신의 천재적인 재능과 노력을 쏟아 붓기 시작한다. 탈출하는 당시 부서져버린 Mark1를 바탕으로 보다 업그레이드 된 수트 Mark2를 만들어낸 토니 스타크. 거기에 만족하지 않고, 숱한 시행착오와 실패 끝에 자신의 모든 능력과 현실에서 가능한 최강의 최첨단 과학 기술이 집적된 하이테크 수트 Mark3를 마침내 완성, 최강의 슈퍼히어로 ‘아이언맨’으로 거듭난다.",
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 7, 8),
        ),
        Movie(
            R.drawable.hansan,
            "한산",
            169,
            "나라의 운명을 바꿀 압도적 승리의 전투가 시작된다! 1592년 4월, 조선은 임진왜란 발발 후 단 15일 만에 왜군에 한양을 빼앗기며 절체절명의 위기에 놓인다. 조선을 단숨에 점령한 왜군은 명나라로 향하는 야망을 꿈꾸며 대규모 병역을 부산포로 집결시킨다. 한편, 이순신 장군은 연이은 전쟁의 패배와 선조마저 의주로 파천하며 수세에 몰린 상황에서도 조선을 구하기 위해 전술을 고민하며 출전을 준비한다. 하지만 앞선 전투에서 손상을 입은 거북선의 출정이 어려워지고, 거북선의 도면마저 왜군의 첩보에 의해 도난 당하게 되는데… 왜군은 연승에 힘입어 그 우세로 한산도 앞바다로 향하고, 이순신 장군은 조선의 운명을 가를 전투를 위해 필사의 전략을 준비한다. 1592년 여름, 음력 7월 8일 한산도 앞바다, 압도적인 승리가 필요한 조선의 운명을 건 지상 최고의 해전이 펼쳐진다.",
            LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 6, 28),
        ),
        Movie(
            R.drawable.tazza,
            "타짜",
            130,
            "인생을 건 한판 승부 | 큰거 한판에 인생은 예술이 된다! | 목숨을 걸 수 없다면, 배팅하지 마라! | 꽃들의 전쟁",
            LocalDate.of(2023, 4, 17),
            LocalDate.of(2023, 7, 28),
        )
    )
    val movies10000 = List(10000) { index -> movies[index % movies.size] }

    private val firstTheaterSchedule = listOf(
        ScreeningSchedule(movies[0], listOf(LocalTime.of(9, 0), LocalTime.of(15, 0))),
        ScreeningSchedule(movies[1], listOf(LocalTime.of(9, 0), LocalTime.of(15, 0), LocalTime.of(21, 0))),
        ScreeningSchedule(movies[2], listOf(LocalTime.of(11, 0), LocalTime.of(17, 0))),
    )

    private val secondTheaterSchedule = listOf(
        ScreeningSchedule(movies[3], listOf(LocalTime.of(10, 0), LocalTime.of(19, 0))),
        ScreeningSchedule(movies[4], listOf(LocalTime.of(9, 0), LocalTime.of(18, 0))),
        ScreeningSchedule(movies[0], listOf(LocalTime.of(9, 0), LocalTime.of(15, 0), LocalTime.of(22, 0))),
    )

    private val thirdTheaterSchedule = listOf(
        ScreeningSchedule(movies[0], listOf(LocalTime.of(9, 0), LocalTime.of(15, 0), LocalTime.of(22, 0))),
        ScreeningSchedule(movies[3], listOf(LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(20, 0))),
        ScreeningSchedule(movies[1], listOf(LocalTime.of(11, 0), LocalTime.of(13, 0))),
        ScreeningSchedule(movies[2], listOf(LocalTime.of(10, 0), LocalTime.of(15, 0))),
        ScreeningSchedule(movies[4], listOf(LocalTime.of(9, 0), LocalTime.of(16, 0), LocalTime.of(21, 0))),
    )

    val theaterData = listOf(
        Theater(
            "선릉 극장",
            firstTheaterSchedule
        ),
        Theater("잠실 극장", secondTheaterSchedule),
        Theater("강남 극장", thirdTheaterSchedule),
    )
}
