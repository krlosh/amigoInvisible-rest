package org.chenche.amigoInvisible.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//TODO: Esto permite llamada desde cualquier "cliente", deberíamos controlar a algunos específicos
		HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request= (HttpServletRequest) req;

          response.setHeader("Access-Control-Allow-Origin", "*");
          response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
          response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
          response.setHeader("Access-Control-Expose-Headers", "x-requested-with"); chain.doFilter(req, res);
        }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}