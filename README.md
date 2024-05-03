# android-movie-theater
# 기능 요구 사항
- 영화 선택 후 극장을 선택할 수 있다.
- 극장별로 상영하는 영화와 상영시간은 달라질 수 있다.
- DataBinding 적용
- 영화 예매 내역, 홈, 설정 화면으로 이동(navigate)할 수 있다.
- BottomNavigationView, Fragment 활용

# 정리

## 왜 Fragment의 onDestroyView() 메서드에서 binding을 null 처리해야 할까?

[공식문서](https://developer.android.com/topic/libraries/view-binding#fragments)를 보면 Fragment에서 Binding을
사용할 때 다음과 같은 내용을 주의하라고 작성되어 있다.

> Fragment의 onDestroyView() 메서드에서 바인딩 클래스 인스턴스에 대한 참조를 null 처리해야 합니다.
> 이를 통해 메모리 누수를 방지하고 Fragment의 생명주기와 바인딩 클래스 인스턴스의 생명주기를 일치시킬 수 있습니다.

공식문서에서는 Binding을 사용할 때, Activity에서는 null 처리를 하라고 하지 않으면서,
Fragment에서는 왜 null 처리를 하라고 할까? 라는 의문이 들어 개념을 정리하기 위해 글을 작성하게 되었다.

### 결론부터 말하자면,

Fragment의 onDestroyView() 메서드에서 binding을 null 처리하는 이유는 메모리 누수를 방지하기 위해서이고, Activity에서 binding을 null
처리하지 않는 이유는 메모리 누수가 발생하지 않기 때문이다.

### 왜 Activity애서는 메모리 누수가 발생하지 않아?

Activity는 Fragment와는 다르게 생명주기 관리가 자동으로 이루어지므로 우리는 별도로 binding을 null 처리할 필요가 없다.
즉, Activity는 기본적으로 onCreate()와 onDestroy() 메서드를 이용하여 화면과 관련된 초기화 및 해제를 수행하기 때문에 별도의 null 처리가 필요하지 않는다.

### 그럼 왜 Fragment에서는 메모리 누수가 발생하지?

Fragment의 생명주기를 살펴보면 onDestroyView() 메서드는 Fragment의 뷰가 소멸될 때 호출되고, onDestroy() 메서드는 Fragment가 소멸되기 직전에 호출된다. 마지막으로 onDetach() 메서드는 Fragment가 Activity와 연결이 끊어질 때 호출된다. 여기서 중요한 점은 onDestroyView()까지만 호출되는 경우에는 Fragment의 뷰는 소멸되지만 Fragment 자체는 소멸되지 않는다는 것이다. 이러한 경우에는 Fragment가 메모리에 계속 남아있게 되어 메모리 누수가 발생할 수 있다.

특히 Fragment에서 Databinding을 사용할 때는 onCreateView()에서 binding을 초기화하고 onViewCreated()에서 binding을 사용하여 뷰를 구성한다. 이로 인해 onCreateView()에서 초기화한 binding이 onDestroyView()에서 제거되지 않으면 Fragment가 종료될 때까지 binding이 메모리에 유지될 수 있다. 이는 메모리 누수를 초래할 수 있다.

### 그래서 어떻게 메모리 누수를 해결해?
이러한 문제를 해결하기 위해서는 onDestroyView()에서 binding을 null 처리하여 binding이 더 이상 필요하지 않을 때 메모리에서 해제되도록 해야 한다. 이렇게 하면 Fragment의 생명주기와 binding의 생명주기를 일치시켜 메모리 누수를 방지할 수 있다.


## MVP + DataBinding = ??? : MVPM
MVP 패턴과 MVVM 패턴의 가장 큰 차이점은 데이터 바인딩이다. 
MVVM 패턴은 데이터 바인딩 라이브러리를 이 용해 View 와 ViewModel을 바인딩하고, 이를 통해 니를 업데이트를 한다. 반면, MVP 패턴은 View와 Presenter 간 의 상호작용을 코드로 구현한다.
또한, MVP 패턴은 View와 Presenter를 분리함으로써 View를 가볍게 만들어 테스트하기 쉽게 만든다. 반면, MVVM 패턴은 View와 ViewModel 사이의 상호작용을 강력하게 바인딩하고, 이를 통해 니 업데이트를 수행합니다.
이 때문에 MVVM 패턴은 테스트하기 어려울 수 있다.
MVVM 패턴은 최근 안드로이드 개발에서 더 많이 사용되고 있으며, 구글에서도 권장하고 있다. 하지만, 개발자들은 상황에 따라 MVP 패턴을 선택하기도 한다. 이는 개발자의 개인적인 취향이나 프로젝트의 요구사항 등에 따라 달라 질 수 있다.
