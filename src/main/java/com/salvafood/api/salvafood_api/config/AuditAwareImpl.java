package com.salvafood.api.salvafood_api.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Obtenemos la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificamos si la autenticación es nula, si no está autenticada,
        // o si es una instancia de un token de usuario anónimo.
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            // Para operaciones que ocurren sin un usuario logueado (como la creación inicial de roles),
            // devolvemos un valor predeterminado.
            return Optional.of("SYSTEM");
        }

        return Optional.of(authentication.getName());
    }
}