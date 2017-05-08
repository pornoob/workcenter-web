package workcenter.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Claudio Olivares
 */
public class SessionFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(SessionFilter.class.getName());

    private String loginPage;
    private List<String> excludeUrls;

    private String makeAjaxRedirect(HttpServletRequest request) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<partial-response>")
                .append("<redirect url=\"")
                .append(request.getContextPath())
                .append(loginPage)
                .append("\"/>")
                .append("</partial-response>")
                .toString();
    }

    private String makeNormalRedirect(HttpServletRequest request) {
        String url = request.getContextPath() + loginPage;
        return url;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("SessionFilter initialized");
        loginPage = filterConfig.getInitParameter("loginPage");
        if (loginPage == null || "".equals(loginPage.trim())) {
            throw new ServletException("Se requiere el parámetro de inicio: {loginPage}");
        }
        StringTokenizer tokenizer = new StringTokenizer(filterConfig.getInitParameter("excludeUrls"), ", ;\n");
        excludeUrls = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            excludeUrls.add(tokenizer.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // verificamos si requiere controlar la sesión
        if (!request.getRequestURI().contains(loginPage)
                && !request.getRequestURI().contains("javax.faces.resource")
                && request.getRequestedSessionId() != null
                && !request.isRequestedSessionIdValid()
                && !isExcludedUrl(request.getRequestURI())) {
            boolean isAjax = "partial/ajax".equals(request.getHeader("Faces-Request"));

            if (isAjax) {
                LOGGER.info("Sesión expirada, Tipo solicitud: AJAX");
                response.setContentType("text/xml");
                response.getWriter().write(makeAjaxRedirect(request));
                response.flushBuffer();
                return;
            } else {
                LOGGER.info("Sesión expirada, Tipo solicitud: NORMAL");
                request.getSession(true);
                response.sendRedirect(makeNormalRedirect(request));
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean isExcludedUrl(String url) {
        for (String excludeUrl : excludeUrls) {
            if (url.contains(excludeUrl)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
