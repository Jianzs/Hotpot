package cn.yzh.hotpot.web.interceptor;

import cn.yzh.hotpot.exception.NoAuthorizationException;
import cn.yzh.hotpot.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws NoAuthorizationException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        Integer role = (Integer) request.getAttribute(JWTUtil.USER_ROLE);


        if (role == null) {
            throw new NoAuthorizationException("Not Log In.");
        }

        return true;
    }
}
