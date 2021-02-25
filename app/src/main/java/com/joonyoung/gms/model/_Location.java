package com.joonyoung.gms.model;


/** [코드 / 주소 / 여부] 필드들로 이루어진 데이터를 객체화할 Model 클래스
 * - Model 클래스라 부르는 이유?
 *   - 프론트를 개발할 때 사용되는 가장 기본적인 '디자인 패턴'인 MVC패턴의 M에서 유래하였다.
 *   - Model이란, field를 갖는 데이터를 객체화하는 것이다. 모델에 대한 직접적인 조작은 Controller 클래스가 하여, View 클래스에
 *     영향을 준다.
 */
public class _Location {
    /** 클래스의 멤버변수(=프로퍼티, 속성)들이다.
     *  일반적으로는 아래처럼, 변수 자체는 'private' 접근제어자를 이용해서 함부로 접근하지 못하게 한다.
     *  private 변수는 이 클래스 안에서만 아무 제약 없이 접근할 수 있다.
     */
    private String code;
    private String location;
    private Boolean isExist;

    /** 아래는 클래스 객체에 대한 getter들을 '정의'한 것이다.
     *  - getter란 '가져오다'의 get에서 유래하였다. 즉, (객체로부터) 가져와주는 함수란 뜻.
     *  - getter는 이 클래스가 아니라 외부의 다른 클래스 객체가 호출해야하므로, public 접근제어자를 이용한다.
     *  - 값을 반환해야(가져다줘야)하므로, 반환할 '자료형'을 함수 이름 앞에 적어야한다.
     */
    public Boolean getExist() {
        return isExist;
    }
    public String getCode(){
        return code;
    }
    public String getLocation(){
        return location;
    }
    /** 아래는 클래스 객체에 대한 setter들을 '정의'한 것이다.
     *  - setter란 '설정하다'의 set에서 유래하였다. 즉, (객체에) 값을 설정하는 함수란 뜻.
     *  - setter는 이 클래스가 아니라 외부의 다른 클래스 객체가 호출해야하므로, public 접근제어자를 이용한다.
     *  - 값을 설정하고, 반환을 하지 않으므로 void 를 함수 이름 앞에 적어야한다.
     *  - 대신에 파라미터를 적어야한다.
     */
    public void setCode(String code){
        this.code = code;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setIsExist(Boolean isExist){
        this.isExist = isExist;
    }
}
