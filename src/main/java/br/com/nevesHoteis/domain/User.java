package br.com.nevesHoteis.domain;

import br.com.nevesHoteis.domain.Dto.LoginDto;
import br.com.nevesHoteis.domain.Dto.UserDto;
import br.com.nevesHoteis.domain.Dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_entity")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    @Setter
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public User (LoginDto dto){
        this.login = dto.login();
        this.password = dto.password();
    }

    public User(UserUpdateDto dto) {
        this.password = dto.password();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void merge(User user) {
        this.password=user.getPassword();
    }
}
