package com.kamikarow.hairCareProject.exposition.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * This class is guard rail that take advantage of OncePerRequestFilter capacities offered by Spring
 * Activated everytime user send a request and handle it (whatever it can be).
 *
 * @Component used to make the class available as a managed been for spring
 * @RequiredArgsConstructor create constructor using all final private fields stated
 */


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterService extends OncePerRequestFilter {

    /**
     *  ¹ Check token
     *  ² UserDetailService check if user already exist in DB
     *  ³ Validation processed
     */

    /**
     * ² Inject service to extract email from token
     */
    private final JwtService jwtService;

    /**
     * loadUserByUsername of UserDetailsService Interface has been redifined in ApplicationConfig
     */
    private final UserDetailsService userDetailsService;

    /**
     * DoFilterInternal method
     *
     * @param request
     * @param response
     * @param filterChain Add @NonNull to params
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        /**
         * ¹ token is stored in the Authorization inside of the header of HttpServletRequest
         *   if token not corresponding to expectation, process is interrupting (return)
         *   Otherwise token is extracted without Bearer key word
         */
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtToken;
        jwtToken = authHeader.substring(7);
        //try .split(" ")[1].trim();


        /** ² userDetailService take username as argument,
         *     username is stored in token and not directly accessible
         *     therefore we need to pass by JWT token service -> JwtService.class
         */
        final String userEmail;
        userEmail = jwtService.extractUsername(jwtToken);

        /**
         * ³ First, we check if username/email is not null and performed SecurityContextHolder.getContext().getAuthentication() to check his not already authenticate
         *   Secondly we check if user is in the database and if token is valid. If return true :
         *   token of type UsernamePasswordAuthenticationToken is created
         *   Details about request are added to UsernamePasswordAuthenticationToken token
         *   Security context is updated
         *   Request is send to dispatcher
         *   FilterChain is used to executed instructions and continue process
         */
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
