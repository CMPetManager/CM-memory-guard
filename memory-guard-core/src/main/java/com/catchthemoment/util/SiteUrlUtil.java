package com.catchthemoment.util;


import javax.servlet.http.HttpServletRequest;

public class SiteUrlUtil {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
