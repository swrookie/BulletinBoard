package com.swrookie.bulletinboard.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberAccessDeniedHandler implements AccessDeniedHandler
{
    private static final Logger logger = LoggerFactory.getLogger(MemberAccessDeniedHandler.class);
   

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            		   throws IOException, ServletException 
    {        
        log.debug("접근 권한 없습니다");
        this.makeExceptionResponse(req, res, ade);
    }
    
    public void makeExceptionResponse(HttpServletRequest request, HttpServletResponse response,
                                      Exception exception) throws IOException 
    {
        logger.debug("CustomAccessDeniedHandler.makeExceptionResponse :::: {}",exception.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
    }
}
