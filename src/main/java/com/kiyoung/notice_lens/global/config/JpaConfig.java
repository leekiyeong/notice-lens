package com.kiyoung.notice_lens.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 설정용 클래스라는 명시
@Configuration
// JPA Auditing 기능을 켠다. @CreatedDate랑 @LastModifiedDate를 자동으로 넣기 위함.
@EnableJpaAuditing
public class JpaConfig {
}
