package com.automoto.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing HTTP cookies in a web application.
 * Provides static methods for adding, retrieving, and deleting cookies.
 * All methods handle cookies at the root path ("/") of the application.
 */
public class CookieUtil {

    /**
     * Creates and adds a cookie to the HTTP response with specified parameters.
     * The cookie is set to be accessible to all paths within the application ("/").
     *
     * @param response the HttpServletResponse object to which the cookie will be added
     * @param name the name of the cookie to be created
     * @param value the value of the cookie to be created
     * @param maxAge the maximum age of the cookie in seconds;
     *               if positive, the cookie will expire after that many seconds;
     *               if zero, the cookie will be deleted;
     *               if negative, the cookie will not be stored (session cookie)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Retrieves a cookie by name from the HTTP request.
     *
     * @param request the HttpServletRequest object containing the cookies
     * @param name the name of the cookie to retrieve
     * @return the Cookie object with the specified name if found;
     *         null if no cookie with that name exists or if no cookies are present
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Deletes a cookie by setting its max age to zero and value to null.
     * The cookie to be deleted must have the same path as specified here ("/").
     *
     * @param response the HttpServletResponse object to which the deletion instruction will be added
     * @param name the name of the cookie to be deleted
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}