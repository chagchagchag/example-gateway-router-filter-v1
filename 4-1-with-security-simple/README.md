## prototype - Webflux Spring Security + Reactive Gateway + Jwt

Spring Webflux Gateway 내에서 JWT 인증을 모두 수행하는 경우의 예제를 정리하는 프로젝트입니다. MSA 기반이 아닌 싱글인스턴스 기반으로 계정 서비스를 운영할 경우에 대한 예제이며, 가급적이면 Webflux Gateway, Security, JWT 의 필수 요소 들 중 필요한 부분만 한눈에 볼 수 있도록 하기 위해 복잡한 R2DBC 설정, 테이블 설계 내용들은 모두 배제했습니다.<br/>

고도화나 리팩토링 없이 어느 정도는 하드코딩으로 원리와 개념만 보이도록 아주 원시적인 코드로 작성중입니다.<br/>



**사용자 계정**<br/>

이번 프로젝트에서는 아래의 사용자들을 Map 으로 입력해둔후 맞는지 판단하는 인증을 거칩니다. 

- id : `user_a`, password : `AAAAA`
- id : `user_b`, password : `BBBBB`

이 내용은 SecurityConfig 내에 정의한 UserDetailsService Bean 을 정의하는 메서드를 확인해보시면 됩니다.<br/>

<br/>



## SecurityConfig, UserDetailsService, PasswordEncoder

편의상 아래의 요소들은 별도의 컴포넌트에 따로 구현하지 않고 SecurityConfig 설정 파일에 Bean 으로 등록합니다.

UserDetailsService

- id : `user_a`, password : `AAAAA`
- id : `user_b`, password : `BBBBB`

<br/>

PasswordEncoder

- BcryptEncoder

<br/>



## JwtAuthenticationManager::authenticate(Authentication)

JwtAuthenticationManager 는 ReactiveAuthenticationManager 를 구현(implements)하는 클래스이며 이번 예제에서 직접 작성하게 될 클래스이며 Bean 으로는 등록하지 않고 SecurityConfig 내에서 내부적으로 1회 객체 생성을 해서 httpSecurityFilterChain 내에 등록합니다.<br/>

JwtAuthenticationManager 는 authenticate(Authentication) 라는 메서드를 구현했는데, 이 authenticate(Authentication) 메서드로 인증을 수행할 수 있는데, 직접 작성해서 등록한 JwtAuthenticationManager 에서는 UserDetailsService,PasswordEncoder 를 주입받아서 아래의 역할을 수행합니다.

- UserDetailsService
  - 사용자가 존재하는지 조회하고 그 결과를 UserDetails 객체로 리턴합니다.
- PasswordEncoder
  - matches() 메서드를 통해 Request 로 전달받은 password 와 UserDetailsService 에서의 조회결과로 전달받은 UserDetails 내의 password 가 일치하는지 체크합니다.
  - Password가 올바르다면 아래의 절차를 거칩니다.
- UsernamePasswordAuthenticationToken
  - UserDetails 객체를 UsernamePasswordAuthenticationToken 으로 변환해주어 AuthenticationToken 을 리턴합니다.

<br/>



JwtAuthenticationManager 의 authenticate(Authentication) 메서드는 Authentication 객체를 받아서 인증을 한다는 사실에 주목해주시기 바랍니다. 보통 UserDetailsService 로 모든 인증을 다 하드코딩하게 되면 Authentication 객체를 직접 만들고 하는 과정을 거치게 되지만, 이번 예제는 JwtAuthenticationManager 의 authenticate(Authentication) 메서드 내에서 Authentication 객체만 인자값으로 필요한 일만 담당합니다.<br/>





구현 중이에요!!! 빨리 만들어볼께요 흑흑...







