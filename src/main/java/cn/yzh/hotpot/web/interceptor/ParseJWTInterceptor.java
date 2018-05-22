package cn.yzh.hotpot.web.interceptor;

import cn.yzh.hotpot.exception.NoAuthorizationException;
import cn.yzh.hotpot.util.JWTUtil;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ParseJWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws NoAuthorizationException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        String token = JWTUtil.getToken(request);

        // 前台可以没有token，后台后面还有身份认证
        if (token == null) {
            return true;
        }

        try {
            DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
            Integer uid = decodedJWT.getClaim(JWTUtil.USER_ID_KEY).asInt();
            Integer role = decodedJWT.getClaim(JWTUtil.USER_ROLE).asInt();
            request.setAttribute(JWTUtil.USER_ID_KEY, uid);
            request.setAttribute(JWTUtil.USER_ROLE, role);
        } catch (SignatureVerificationException e) {
            throw new NoAuthorizationException("Token is Wrong.");
        }

        return true;
    }
}
