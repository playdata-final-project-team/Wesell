package com.wesell.authenticationserver.filter;

import com.wesell.authenticationserver.domain.entity.AuthUser;
import com.wesell.authenticationserver.domain.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository.findByUuid(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(authUser.getRole().toString()));

        return UserDetailsImpl.builder()
                .uuid(authUser.getUuid())
                .authorities(authorities)
                .build();
    }
}
