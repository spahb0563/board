package com.example.board.config.auth;


import com.example.board.model.enumclass.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()//h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                    .authorizeHttpRequests() // URL별 권한 관리를 설정하는 옵션의 시작점, 이게 선언되야만 antMatchers옵션을 사용할 수 있음
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()// 권한 관리 대상을 지정하는 옵션
                    .antMatchers("/post/write").hasRole(UserRole.USER.name())
//                    .antMatchers("/api/v1/**").hasRole(UserRole.USER.name())// URL, HTTP 메소드별로 관리가 가능,
                    .antMatchers("/post/**/edit").hasRole(UserRole.USER.name())
                    // "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었음
                    // "/api/v1/**"주소를 가진 API는 USER권한을 가진 사람만 가능하도록 했음
//                    .anyRequest().   // authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용하게 함 즉 로그인 한 사용자들
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  //로그아웃 성공시 / 주소로 이동
                .and()
                    .oauth2Login()
                        .loginPage("/login")
                            .defaultSuccessUrl("/", true)
                                .userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                                    .userService(customOAuth2UserService);  //소셜 로그인 성공시 후속조치를 진행할 UserService 인터페이스의 구현체를 등록
    }
}
