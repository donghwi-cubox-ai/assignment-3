# assingment-3
개발 과제 - 3

# 라이센스 관련
<img width="892" alt="Screenshot 2024-05-19 at 2 45 51 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/dd74c761-219a-450c-bf36-8f541687b572">


## 트러블 슈팅
🤔 왜 Spring Security를 사용하면 Controller를 타지 않을까??

loginProcessingUrl("/member/login")이 정의되어 있기 때문에 FormLogin일 경우
/member/login 으로 POST 요청이 들어올 때 Spring Security에서 가로채서 
UsernamePasswordAuthenticationFilter를 사용해 인증 과정을 자동으로 처리합니다.
<img width="919" alt="Screenshot 2024-05-19 at 3 22 23 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/c1d8579e-a268-4e1e-9b99-6d06c16c775f">

위 사진과 같이 컨트롤러를 주석처리해도 시큐리티 필터체인에서 가로채서 자동 실행시키기 때문에 정상적으로 로그인 처리가 됩니다.
또한 ContextHolder에서 유저 정보를 빼와서 mapper 또는 jpa에서 바로 정보를 조회할 수 있습니다.

<img width="926" alt="Screenshot 2024-05-19 at 3 08 26 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/9680800d-6392-4ef5-b8fc-da12b55d2d30">

** .defaultSuccessUrl("/main", true)
true 매개변수는 alwaysUse 옵션을 설정합니다. 이 설정은 사용자가 로그인에 성공했을 때, 지정된 url로 사용자를 리다이렉션 시킵니다.
true는 어떤 요청을 통해 로그인 페이지에 도달했는지에 상관없이 항상 main으로 리다이렉션하라는 설정입니다.

## 📌 비밀번호 90일이 자나면 만료되는

<img width="748" alt="Screenshot 2024-05-19 at 3 21 21 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/a3e27a2f-21e2-4a23-a1d2-e6976948fd23">

** .credentialsExpired 는 비밀번호 만료를 설정한다. false일 경우 비밀번호를 만료시키고 true일 경우 활성화됩니다.
따라서, Duration의 between을 사용하여 두 파라미터 간의 차가 90일을 넘을 경우 false를 반환하여 비밀번호를 만료시킵니다.

<img width="345" alt="Screenshot 2024-05-19 at 3 06 09 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/07da5e75-707f-453e-9319-470cfe41aa13">


### 비밀번호 역사
텍스트 비밀번호 -> SQL Injection -> SHA-256 -> Rainbow Tables(전체 해시값 저장 테이블) -> salt(소금) + bycrpt

<img width="883" alt="Screenshot 2024-05-19 at 2 53 35 PM" src="https://github.com/donghwi-cubox-ai/assignment-3/assets/166668961/601f86e2-0be3-4dd2-a770-9409ba950c7a">


위 사진처럼 {소금} 최근에는 소금을 쳐서 비밀번호를 단방향 암호화하여 저장함으로 전체 해시값을 저장하면서 채굴하는 레인보우 테이블을 방어할 수 있게 변경되었습니다.
⁉️ {bycrpt} 값이 함께 저장되지 않을 때 오류가 발생할 수 있으니 주의해야 합니다.
