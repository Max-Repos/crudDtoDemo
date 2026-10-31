package in.rajaram.userLogin.Service;

import in.rajaram.userLogin.Entity.UserAuthEntity;
import in.rajaram.userLogin.Repository.UserAuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthEntityService implements UserDetailsService {

    private final UserAuthEntityRepository userAuthEntityRepository;

    public UserDetails save(UserAuthEntity userAuthEntity) {
        return userAuthEntityRepository.save(userAuthEntity);
    }

    @Override
    public UserAuthEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
