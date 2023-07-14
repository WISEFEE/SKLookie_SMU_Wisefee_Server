# 1. RESTful API URL 권장 규칙
## 1-1. URL 패턴
- 명사 사용: URL은 리소스를 나타내므로 명사를 사용하는 것이 적절합니다. 예를 들어, /users, /products, /orders와 같이 리소스의 복수형 명사를 사용합니다.
- 동사 사용은 피하기: RESTful API에서는 URL 자체에 동사를 사용하지 않습니다. 대신 HTTP 메서드 (GET, POST, PUT, DELETE 등)를 사용하여 동작을 나타냅니다. 예를 들어, /users/1은 "ID가 1인 사용자를 조회"라는 동작을 표현합니다.
- 계층 구조: URL은 계층 구조를 반영할 수 있습니다. 예를 들어, /users/1/orders는 "ID가 1인 사용자의 주문 목록"을 나타냅니다. 계층 구조를 사용하여 리소스 간의 관계를 표현할 수 있습니다.
- 동사 대신 HTTP 메서드 사용: HTTP 메서드를 사용하여 동작을 나타냅니다. GET은 조회, POST는 생성, PUT은 업데이트, DELETE는 삭제를 의미합니다. 이를 통해 URL 자체가 의도를 명확하게 전달할 수 있습니다.
- 파라미터 사용: URL에 필요한 정보를 쿼리 문자열(query string) 또는 경로 변수(path variable)로 전달할 수 있습니다. 예를 들어, /users?name=John 또는 /users/{id}와 같은 형식으로 파라미터를 전달할 수 있습니다.
- 동사 대신 명사 활용: 동작 대신 리소스에 대한 동사를 사용합니다. 예를 들어, /users/1/orders는 "ID가 1인 사용자의 주문 목록"을 의미합니다.
- 슬래시 구분: 계층 구조를 표현할 때 슬래시(/)를 사용하여 구분합니다. 예를 들어, /users/1/orders와 같이 계층 구조를 명확하게 표현합니다.
- 단수와 복수형: 리소스의 단수와 복수형에 대해 일관성을 유지해야 합니다. 예를 들어, /user 또는 /users 중 하나를 선택하여 일관성을 유지합니다.
  * 와이즈피 프로젝트에서는 단수형으로 통일시킵니다.

## 1-2. 하나의 자원에 대해 하나의 엔드포인트를 가지도록 설계
- 특이경우를 제외하고는 URL 이름에 맞게 하나의 자원에 대한 정보를 제공하는 것을 원칙으로 한다.
- 예를 들어 프론트엔드에서 멤버 리스트와 가게 리스트가 동시에 필요한 경우라면 아래 두 가지의 API로 나누어 제공한다.
  - GET /member
  - GET /store

## 1-3. 매장용 API와 고객용 API의 엔드포인트 구분
- 매장용과 손님용, 공용 API를 분리해서 만드므로, 경로 접두사로 다음 규칙을 따른다.
- 패키지는 우선 shared, consumer, seller로 분리하여 작성한다.
  - 매장 - 가게 상세보기 조회 : GET /api/v1/seller/store/{id}
  - 손님 - 가게 상세보기 조회 : GET /api/v1/consumer/store/{id}
  - 공용 - 가게 상세보기 조회 : GET /api/v1/store/{id}

## 1-4. 예시
- GET /api/v1/member : 유저 리스트 조회
- GET /api/v1/member?order=desc : 유저 리스트 역순 조회
- GET /api/v1/member/{id} : 유저 한 명 조회
- POST /api/v1/member : 유저 추가
- PUT /api/v1/member/{id} : 유저 수정
- DELETE /api/v1/member/{id} : 유저 삭제

# 2. 입력과 출력
- 항상 일관된 API 스펙을 유지할 수 있도록 입력과 출력은 DTO 클래스를 이용한다.
- DTO 클래스는 입력은 ~RequestDto, 출력은 ~ResponseDto의 명칭을 사용한다.
- 항상 도메인 모델이 변경될 수 있음을 명심하며, 이와 상관없이 스펙을 유지할 수 있도록 한다.

# 3. 메서드 명명 권장사항
- get/find: 리소스를 조회하는 HTTP GET 요청을 처리하는 메서드에 사용됩니다. 예를 들어, getMemberById, findProductsByCategory 등이 있습니다.
- create/add/save: 새로운 리소스를 생성하거나 저장하는 HTTP POST 또는 PUT 요청을 처리하는 메서드에 사용됩니다. 예를 들어, createMember, addProduct, saveOrder 등이 있습니다.
- update/edit/modify: 기존 리소스를 업데이트하거나 수정하는 HTTP PUT 또는 PATCH 요청을 처리하는 메서드에 사용됩니다. 예를 들어, updateMember, editProduct, modifyOrderStatus 등이 있습니다.
- delete/remove: 리소스를 삭제하는 HTTP DELETE 요청을 처리하는 메서드에 사용됩니다. 예를 들어, deleteMember, removeProduct 등이 있습니다.
- validate/check: 데이터의 유효성을 검증하거나 체크하는 메서드에 사용됩니다. 예를 들어, validateEmail, checkAvailability 등이 있습니다.
- process/handle: 비즈니스 로직을 처리하거나 특정 작업을 수행하는 메서드에 사용됩니다. 예를 들어, processOrder, handlePayment 등이 있습니다.
- render/display: 데이터를 보여주거나 특정 뷰를 렌더링하여 반환하는 메서드에 사용됩니다. 예를 들어, renderHomePage, displayProductDetails 등이 있습니다.
- authenticate/login: 사용자 인증과 관련된 로직을 처리하는 메서드에 사용됩니다. 예를 들어, authenticateUser, loginUser 등이 있습니다.

# 4. 예제 소스 코드
```
    @ApiOperation(value = "해당 ID의 회원 조회")
    @GetMapping("/api/v1/member/{id}")
    public ResponseEntity<MemberResponseDto> Example(
            @ApiParam(value = "회원 PK", required = true) 
            @PathVariable("id") Long id
            
            @ApiParam(value = "정렬 순서 (asc 또는 desc)", defaultValue = "asc")
            @RequestParam(value = "exam", defaultValue = "exvalue") String example
    ){
        Member member = memberService.getMember(id);
        MemberResponseDto result = modelMapper.map(member, MemberResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
```
### 4-1. Swagger 문서 작성
- @ApiOperation : Swagger API 설명 작성
- @ApiParam : Swagger API의 Path/Query Parameter에 대한 설명

### 4-2. URL 규칙 작성
- @GetMapping : API URL 규칙 작성
- @PathVariable : /api/v1/member/3 -> 3에 해당하는 Path Parameter
- @RequestParam : /api/v1/member/3?exam=test -> ?exam=test에 해당하는 Query Parameter

### 4-3. 응답
- HttpStatusCode.OK와 함께 JSON 반환


### 4-4. DTO
- modelMapper 라이브러리를 이용하여 DTO <-> Entity의 변환을 했음
- DTO 클래스는 src/main/java/com.sklookiesmu.wisefee/dto 디렉터리에서 생성
```
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 추가 요청 DTO")
public class MemberRequestDto {
    @ApiModelProperty(value = "회원 닉네임", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "회원 비밀번호", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(value = "회원 이메일", required = true)
    @NotNull
    private String email;
}
```
- @ApiModel과 @ApiModelProperty는 Swagger상에 제공될 정보