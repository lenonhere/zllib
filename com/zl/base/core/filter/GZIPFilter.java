package com.zl.base.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GZIPFilter
  implements Filter
{
  public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
    throws IOException, ServletException
  {
    if ((paramServletRequest instanceof HttpServletRequest))
    {
      HttpServletRequest localHttpServletRequest = (HttpServletRequest)paramServletRequest;
      HttpServletResponse localHttpServletResponse = (HttpServletResponse)paramServletResponse;
      String str = localHttpServletRequest.getHeader("accept-encoding");
      if ((str != null) && (str.indexOf("gzip") != -1))
      {
        GZIPResponseWrapper localGZIPResponseWrapper = new GZIPResponseWrapper(localHttpServletResponse);
        paramFilterChain.doFilter(paramServletRequest, localGZIPResponseWrapper);
        localGZIPResponseWrapper.finishResponse();
        return;
      }
      paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
    }
  }

  public void init(FilterConfig paramFilterConfig)
  {
  }

  public void destroy()
  {
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.filter.GZIPFilter
 * JD-Core Version:    0.6.1
 */