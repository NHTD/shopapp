    package com.example.shopapp.services.imp;

    import com.example.shopapp.dtos.request.AuthenticationRequest;
    import com.example.shopapp.dtos.response.AuthenticationResponse;
    import com.example.shopapp.exception.ShopAppModelsNotFoundException;
    import com.example.shopapp.models.User;
    import com.example.shopapp.repositories.UserRepository;
    import com.example.shopapp.security.JwtProvider;
    import com.example.shopapp.security.UserDetailServiceImp;
    import com.example.shopapp.services.AuthenticationService;
    import com.example.shopapp.utils.LocalizationUtils;
    import com.example.shopapp.utils.MessageKeys;
    import jakarta.servlet.http.HttpServletRequest;
    import lombok.AccessLevel;
    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import org.springframework.context.MessageSource;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.web.servlet.LocaleResolver;

    import java.util.Collections;
    import java.util.Locale;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public class AuthenticationServiceImp implements AuthenticationService {

        JwtProvider jwtProvider;
        UserDetailServiceImp userDetailService;
        PasswordEncoder passwordEncoder;
        UserRepository userRepository;

        LocalizationUtils localizationUtils;

        @Override
        public AuthenticationResponse authenticate(AuthenticationRequest request) {
            Optional<User> optionalUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
            if(!optionalUser.isPresent()){
                throw new ShopAppModelsNotFoundException("User with phone number {} does not exist", request.getPhoneNumber());
            }

            User user = optionalUser.get();

            if(user.getFacebookAccountId()==0 && user.getGoogleAccountId()==0){
                if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                    throw new ShopAppModelsNotFoundException(
                            "Wrong phone number ({}) or password ({})", request.getPhoneNumber(), request.getPassword()
                    );
                }
            }

            String phoneNumber = request.getPhoneNumber();
            String password = request.getPassword();

            Authentication authentication = authenticate(phoneNumber, password);

            String jwt = jwtProvider.generateToken(authentication);

            return AuthenticationResponse.builder()
                    .token(jwt)
                    .message(localizationUtils.getLocalizeMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .build();
        }

        private Authentication authenticate(String phoneNumber, String password){
            UserDetails userDetails = userDetailService.loadUserByUsername(phoneNumber);

            if(userDetails==null){
                throw new BadCredentialsException("Invalid username");
            }
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("Invalid password");
            }

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
    }
