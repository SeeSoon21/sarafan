package letscode.sarafan.config;

import letscode.sarafan.domain.User;
import letscode.sarafan.repo.UserDetailsRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                //разрешаем все запросы по текущим адресам и их дочерним
                .antMatchers("/", "/login**" ,"/js/**", "/error**")
                .permitAll()
                //для всех остальных запросов -- авторизация
                .anyRequest()
                .authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    //сюда приходит только после клика на аккаунт в окне авторизации
    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailsRepo) {
        return map -> {
            String id = (String) map.get("sub");

            User user = userDetailsRepo.findById(id).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(id);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setGender((String) map.get("gender"));
                newUser.setLocale((String) map.get("locale"));
                newUser.setUserpic((String) map.get("picture"));
                System.out.println("registered user: " + newUser.toString());

                return newUser;
            });

            user.setLastVisit(LocalDateTime.now());
            System.out.println("registered user: " + user.getName());

            return userDetailsRepo.save(user);
        };
    }

}





