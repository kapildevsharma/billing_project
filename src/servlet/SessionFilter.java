package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class SessionFilter implements Filter 
{ 
    private ArrayList<String> urlList;
     
    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
 
        urlList = new ArrayList<String>();
 
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
 
        }
    }
 
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
    { 
    	System.out.println("Enter in session filter start.");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        boolean allowedRequest = false;
        try 
        {
        	System.out.println("urlList"+urlList);
        	System.out.println("url"+url);
            if(urlList.contains(url)) {
                allowedRequest = true;
            }
                 
            if (!allowedRequest) {
                HttpSession session = request.getSession(false);
                System.out.println("session"+session);
                //System.out.println("session"+session.isNew());
                if (null == session) {
                    response.sendRedirect("index.jsp");
                }
            }
		} 
        
        catch (Exception e) 
        {
			e.printStackTrace();
		}
        
        
        System.out.println("Enter in session filter end.");
        chain.doFilter(req, res);
    }
 

    public void destroy() 
    {
    }
}