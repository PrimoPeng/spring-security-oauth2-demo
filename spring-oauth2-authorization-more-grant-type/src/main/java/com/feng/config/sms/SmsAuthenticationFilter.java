package com.feng.config.sms;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author primo
 * @date 2021/10/25
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsAuthenticationFilter(){
        super(new AntPathRequestMatcher("/oauth/sms", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException{
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String sms = obtainSms(request);
        sms = sms == null ? "" : sms.trim();
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(sms);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainSms(HttpServletRequest request){
        return request.getParameter("sms");
    }

    private void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
