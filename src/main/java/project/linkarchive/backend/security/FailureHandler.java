package project.linkarchive.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FailureHandler implements AuthenticationEntryPoint {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        response.setStatus(401);
        PrintWriter writer = response.getWriter();
        writer.print("로그인이 필요합니다.");
        // FIXME: JSON 형식으로 에러 메시지 수정
    }
}
