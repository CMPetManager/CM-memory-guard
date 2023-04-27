package com.catchthemoment.util;

import jakarta.servlet.http.HttpServletRequest;


public class SiteUrlUtil {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
