package com.cottongallery.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

  @Bean
  public AuditorAware<String> auditorProvider() {
      return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
              .map(auth -> auth.getName())
              .or(() -> Optional.of("system")); // 인증되지 않은 경우 기본값 반환
  }
}
