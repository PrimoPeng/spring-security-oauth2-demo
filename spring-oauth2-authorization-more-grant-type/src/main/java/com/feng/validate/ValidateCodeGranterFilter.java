package com.feng.validate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeGranterFilter extends OncePerRequestFilter {

    private final @NonNull ValidateCodeProcessorHolder validateCodeProcessorHolder;
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.name());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            String grantType = getGrantType(request);
            if ("sms".equalsIgnoreCase(grantType) || "email".equalsIgnoreCase(grantType)) {
                try {
                    log.info("请求需要验证！验证请求：" + request.getRequestURI() + " 验证类型：" + grantType);
                    validateCodeProcessorHolder.findValidateCodeProcessor(grantType)
                            .validate(new ServletWebRequest(request, response));
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getGrantType(HttpServletRequest request) {
        return request.getParameter("grant_type");
    }
}
