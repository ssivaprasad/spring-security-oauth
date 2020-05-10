package com.ssp.apps.oauth.server.servrice;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ssp.apps.oauth.server.entity.User;
import com.ssp.apps.oauth.server.entity.UserPrincipal;
import com.ssp.apps.oauth.server.repository.UserRepository;


@Service("userDetailsService")
public class UserDetailsServriceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        UserPrincipal userPrincipal = optionalUser.map(user -> new UserPrincipal(user))
                .orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

        validateUserDetails(userPrincipal);
        return userPrincipal;
    }

    private void validateUserDetails(UserPrincipal userPrincipal) {
        new AccountStatusUserDetailsChecker().check(userPrincipal);
    }

}
