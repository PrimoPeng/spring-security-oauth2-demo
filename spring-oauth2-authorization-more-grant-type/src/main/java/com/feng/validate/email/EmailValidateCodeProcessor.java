package com.feng.validate.email;

import com.feng.validate.impl.AbstractValidateCodeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
@RequiredArgsConstructor
public class EmailValidateCodeProcessor extends AbstractValidateCodeProcessor {

    @Override
    protected void send(ServletWebRequest request, String validateCode) {
        System.out.println(request.getParameter("email") +
                "邮箱验证码发送成功，验证码为：" + validateCode);
    }

}