package com.warehouse.demo.demospringapp.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class DemoAppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // public DemoAppAuthenticationSuccessHandler() {
    //     super();
    // }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication authentication) throws IOException {
        
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null ){
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

    }
 
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if(response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }        
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) throws IOException {
        Map<String, String> roleTargetUrlMap =  new HashMap<>();
        // Supplier currentSupplier = supplierService.getCurrentSupplier();
        // Long currentSupplierId = currentSupplier.getId();
        // String convertedCurrentSupplierId = Long.toString(currentSupplierId);
        // String suppUrl = "supplier/" + convertedCurrentSupplierId + "/dashboard";
        // System.out.println(suppUrl);
        String suppUrl = "/";
        // logger.debug(suppUrl);
        roleTargetUrlMap.put("SUPPLIER", suppUrl);
        roleTargetUrlMap.put("CUSTOMER", "/");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority authority : authorities) {
            String authName = authority.getAuthority();
            if(roleTargetUrlMap.containsKey(authName)) {
                return roleTargetUrlMap.get(authName);
            }
        }
        throw new IllegalStateException();
    }

    
}
