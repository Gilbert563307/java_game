package com.game.java_game.security;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

public class UserProfileResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserProfile.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        /*
         * This is (of course) not real security, but it works ‘mostly’ the same as
         * proper security. Almost all web security methods use a technically tricky
         * header to store authentication information.
         * 
         * In this case we just use a very simple header: anyone is allowed to claim who
         * they are, and we simply believe it.
         * 
         * This way you at least encounter part of the technique already without having
         * to set up a complicated security framework. In this way, switching to real
         * security later on will have as little impact as possible on the rest of the
         * code.
         * 
         * Naturally, we will also go through a proper demo of actually working security
         * with you at a later time.
         */

        String username = webRequest.getHeader("X-User");
        if (username != null) {
            return new UserProfile(username);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user profile found");
        }
    }

}
