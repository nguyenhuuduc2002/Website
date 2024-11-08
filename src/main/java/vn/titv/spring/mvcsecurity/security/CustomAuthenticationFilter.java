//package vn.titv.spring.mvcsecurity.security;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String role = request.getParameter("role");
//
//        // Xác thực username và password
//        Authentication authentication = getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//        // Kiểm tra vai trò
//        boolean hasRole = authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals(role));
//
//        if (!hasRole) {
//            throw new AuthenticationException("Sai vai trò") {};
//        }
//
//        return authentication;
//    }
//}
