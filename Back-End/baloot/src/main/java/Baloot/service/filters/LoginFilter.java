package Baloot.service.filters;

import Baloot.model.DTO.Response;
import Baloot.security.JwtTokenUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@ComponentScan(basePackages ={"ie.Baloot.security"})
public class LoginFilter implements Filter {


    JwtTokenUtil jwtTokenUtil= new JwtTokenUtil();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestPath = request.getRequestURI();
        var jwtToken = request.getHeader("Authorization");
        var test=request.getMethod();
        var needsAuthentication = needsAuthentication(requestPath, request.getMethod());
        if (needsAuthentication){
            if (jwtToken == null || !jwtTokenUtil.validateToken(jwtToken)){
                sendUnauthorizedResponse(response);
            } else {
                request.setAttribute("userEmail", jwtTokenUtil.getUserIdFromToken(jwtToken));
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean needsAuthentication(String url, String httpMethod) {
        if(httpMethod.equals("OPTIONS"))
            return false;
        if(!httpMethod.equals("GET")) {
            if(
                    url.matches("^/auth/login$") ||
                            url.matches("^/auth/login/$") ||
                            url.matches("^/auth/register$") ||
                            url.matches("^/auth/register/$") ||
                            url.matches("^/auth/oauth-login$") ||
                            url.matches("^/auth/oauth-login/$")
            ) return false;
            return true;
        }
        return url.matches("^/users/\\S+$") || url.matches("^/users/\\S+/$");
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        out.print(new ObjectMapper().writeValueAsString(new Response(false, "Unauthorized", null)));
        out.flush();
    }

}

