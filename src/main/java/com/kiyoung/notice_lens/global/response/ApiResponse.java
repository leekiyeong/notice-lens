package com.kiyoung.notice_lens.global.response;

// record는 Java 16부터 생긴 문법
// 괄호 안에 필드를 적으면 생성자, getter, equals, toString을 자동으로 만들어줌.
// 생성자, getter, equals, hashCode, toString 자동 생성 (setter 없음 = 불변)
// getter 이름은 getData()가 아니라 data(), success(), message()
public record ApiResponse<T>(boolean success, T data, String message) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(true, data, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<T>(false, null, message);
    }
}
