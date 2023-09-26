package com.hepsijet.kargo2.Permissions;

import com.hepsijet.kargo2.Employee.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomInterceptor implements HandlerInterceptor {
    private final PermissionRepository permissionRepository;
    private final EmployeeService employeeService;
    public CustomInterceptor(PermissionRepository permissionRepository, EmployeeService employeeService) {
        this.permissionRepository = permissionRepository;
        this.employeeService = employeeService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String  requestURI = request.getRequestURI();
        String role = permissionRepository.checkPermission(requestURI);
        if (requestURI.contains("/employee/login") || requestURI.contains("/swagger-ui")
                || requestURI.contains("/v3/api-docs") || requestURI.contains("/xdock/city") || requestURI.contains("/xdock/cache")
                || requestURI.contains("/xdock/all")) {
            return true;
        }
        else if (!employeeService.checkRole().equals(role)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 Unauthorized
            response.getWriter().write("Erişim Yetkiniz yok. Rolunuz: " + employeeService.checkRole() );
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}

