package dev.appkr.starter.appuser;

import dev.appkr.starter.appuser.service.JWTService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JWTService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    String username = null; String accessToken = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      accessToken = authHeader.substring("Bearer ".length());
      username = jwtService.extractUsername(accessToken);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtService.isValidToken(accessToken, userDetails)) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(), null, userDetails.getAuthorities());
        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);
      }
    }

    filterChain.doFilter(request, response);
  }
}