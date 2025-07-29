package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.exception.EntityNotFoundException;
import com.salvafood.api.salvafood_api.mapper.UserMapper;
import com.salvafood.api.salvafood_api.model.dto.*;
import com.salvafood.api.salvafood_api.model.entity.TokenResetPasswordEntity;
import com.salvafood.api.salvafood_api.model.entity.UserEntity;
import com.salvafood.api.salvafood_api.repository.RoleRepository;
import com.salvafood.api.salvafood_api.repository.TokenResetPasswordRepository;
import com.salvafood.api.salvafood_api.repository.UserRepository;
import com.salvafood.api.salvafood_api.service.UserService;
import com.salvafood.api.salvafood_api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenResetPasswordRepository tokenResetPasswordRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequest) {

        // Validación para crear usuarios ADMIN
        if (userRequest.getRoleId() == 1) { // Rol ADMIN
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Verificar si el usuario está autenticado
            if (authentication == null || !authentication.isAuthenticated() ||
                    "anonymousUser".equals(authentication.getName())) {
                throw new RuntimeException("Debes estar autenticado para crear un usuario administrador");
            }

            // Verificar si el usuario autenticado es ADMIN
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

            if (!isAdmin) {
                throw new RuntimeException("Solo los administradores pueden crear usuarios administradores");
            }
        }

        // Validación de email duplicado
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email ya esta registrado");
        }

        // Validación de teléfono duplicado
        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new RuntimeException("Numero de telefono ya esta registrado");
        }

        // Crear la entidad usuario
        UserEntity userEntity = UserMapper.toEntity(userRequest);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setActive(Boolean.TRUE);
        userEntity.setSalvaCoin(0.0);
        userEntity.setRole(roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el id: " + userRequest.getRoleId())));

        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto, Long id) {
        if(userRepository.existsByIdAndActive(id,Boolean.TRUE)){
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow( () -> new EntityNotFoundException("Usuario con id no fue encontrado") );

            userEntity.setName(userRequestDto.getName());
            userEntity.setEmail(userRequestDto.getEmail());
            userEntity.setPhoneNumber(userRequestDto.getPhoneNumber());

            UserEntity savedUser = userRepository.save(userEntity);
            return UserMapper.toDto(savedUser);
        }else{
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return UserMapper.toListDto(userRepository.findAllUsers()
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron usuarios")));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return UserMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro usuario con el id: " + id)));
    }

    @Override
    public void deleteUserById(Long id) {
        if(userRepository.existsByIdAndActive(id,Boolean.TRUE)){
            UserEntity userEntity = userRepository.findById(id).orElseThrow();
            userEntity.setActive(Boolean.FALSE);
            userRepository.save(userEntity);
        }else{
            throw new EntityNotFoundException("Usuario no encontrado con el id: " + id);

        }
    }

    @Override
    public String startForgotPassword(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el email: " + email));

        // Generar un token de restablecimiento de contraseña
        String resetToken = jwtUtil.generateResetToken(userEntity.getEmail());

        // Enviar el token por correo electrónico
        try{
            emailService.sendEmailResetPassword(userEntity.getEmail(),"Restablecer Contraseña", resetToken);
            TokenResetPasswordEntity tokenEntity = new TokenResetPasswordEntity();
            tokenEntity.setToken(resetToken);
            tokenEntity.setEmail(userEntity.getEmail());
            tokenEntity.setActive(Boolean.TRUE);
            tokenEntity.setCreatedAt(LocalDateTime.now());
            tokenEntity.setExpirationDate(LocalDateTime.now().plusMinutes(5)); // El token expira en 5min
            tokenResetPasswordRepository.save(tokenEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al enviar el correo electrónico: " + e.getMessage());
        }

        return "Se ha enviado un correo electrónico con las instrucciones para restablecer la contraseña.";
    }

    @Override
    public String resetPassword(ResetPasswordDto resetPasswordRequestDto) {
        if(tokenResetPasswordRepository.existsByTokenAndActive(resetPasswordRequestDto.getToken(), Boolean.TRUE)) {
            TokenResetPasswordEntity tokenEntity = tokenResetPasswordRepository.findByToken(resetPasswordRequestDto.getToken())
                    .orElseThrow(() -> new EntityNotFoundException("Token no encontrado o inactivo"));

            if (tokenEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("El token ha expirado");
            }

            UserEntity userEntity = userRepository.findByEmail(tokenEntity.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el email: " + tokenEntity.getEmail()));

            userEntity.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
            userRepository.save(userEntity);

            // Desactivar el token después de usarlo
            tokenEntity.setActive(Boolean.FALSE);
            tokenResetPasswordRepository.save(tokenEntity);

            return "Contraseña restablecida correctamente";
        } else {
            throw new RuntimeException("Token no válido o inactivo");

        }
    }

    @Override
    public LoginResponseDto authUser(LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

            if (authentication.isAuthenticated()) {
                UserEntity userEntity = userRepository.findByUsernameAndActive(loginRequestDto.getEmail(), Boolean.TRUE)
                        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado o inactivo"));

                String token = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRole().getName());
                return new LoginResponseDto(token, "Has iniciado sesión correctamente", UserMapper.toDto(userEntity));
            } else {
                throw new RuntimeException("Las credenciales son incorrectas o el usuario no está activo");
            }
        } catch (Exception e) {
            // Lanzar excepción en lugar de retornar error
            throw new RuntimeException("Error durante la autenticación: " + e.getMessage());
        }
    }

}
