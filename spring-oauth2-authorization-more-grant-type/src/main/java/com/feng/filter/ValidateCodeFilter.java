package com.feng.filter;

import com.feng.validate.ValidateCodeProcessorHolder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final @NonNull
    ValidateCodeProcessorHolder validateCodeProcessorHolder;
    private Map<String, String> urlMap = new HashMap<>();
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 路径拦截
        urlMap.put("/auth/sms", "sms");
        //urlMap.put("/custom/sms", "sms");
        //urlMap.put("/oauth/sms", "sms");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String validateCodeType = getValidateCodeType(request);
        if (!StringUtils.isEmpty(validateCodeType)) {
            try {
                log.info("请求需要验证！验证请求：" + request.getRequestURI() + " 验证类型：" + validateCodeType);
                validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                        .validate(new ServletWebRequest(request, response));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getValidateCodeType(HttpServletRequest request) {
        if (HttpMethod.POST.matches(request.getMethod())) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                // 如果路径匹配，就回去他的类型，也就是 map 的 value
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    return urlMap.get(url);
                }
            }
        }
        return null;
    }
}