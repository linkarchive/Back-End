package project.linkarchive.backend.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();

        request.setAttribute(
                "startTime", startTime
        );

        log.info(
                "Method=" + request.getMethod() +
                        ":: Request URL=" + request.getRequestURL().toString() +
                        ":: Client IP=" + request.getRemoteAddr() +
                        ":: User Agent=" + request.getHeader("User-Agent") +
                        ":: Start Time=" + System.currentTimeMillis()
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");

        log.info(
                "Method=" + request.getMethod() +
                        ":: Request URL=" + request.getRequestURL().toString() +
                        ":: Status Code=" + response.getStatus() +
                        ":: End Time=" + System.currentTimeMillis() +
                        ":: Time Taken=" + (System.currentTimeMillis() - startTime) + "ms"
        );
    }

}