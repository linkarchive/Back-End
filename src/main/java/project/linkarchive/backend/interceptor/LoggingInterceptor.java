package project.linkarchive.backend.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();

        request.setAttribute(
                "startTime", startTime
        );

        logger.info(
                "Method::" + request.getMethod() +
                "Request URL::" + request.getRequestURL().toString() +
                ":: Client IP=" + request.getRemoteAddr() +
                ":: User Agent=" + request.getHeader("User-Agent") +
                ":: Start Time=" + System.currentTimeMillis()
        );

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");

        logger.info(
                "Method::" + request.getMethod() +
                "Request URL::" + request.getRequestURL().toString() +
                ":: Status Code=" + response.getStatus() +
                ":: End Time=" + System.currentTimeMillis() +
                ":: Time Taken=" + (System.currentTimeMillis() - startTime) + "ms"
        );
    }

}