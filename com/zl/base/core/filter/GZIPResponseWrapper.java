package com.zl.base.core.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZIPResponseWrapper extends HttpServletResponseWrapper
{
  protected HttpServletResponse origResponse = null;
  protected ServletOutputStream stream = null;
  protected PrintWriter writer = null;

  public GZIPResponseWrapper(HttpServletResponse paramHttpServletResponse)
  {
    super(paramHttpServletResponse);
    this.origResponse = paramHttpServletResponse;
  }

  public ServletOutputStream createOutputStream()
    throws IOException
  {
    return new GZIPResponseStream(this.origResponse);
  }

  public void finishResponse()
  {
    try
    {
      if (this.writer != null)
        this.writer.close();
      else if (this.stream != null)
        this.stream.close();
    }
    catch (IOException localIOException)
    {
    }
  }

  public void flushBuffer()
    throws IOException
  {
    this.stream.flush();
  }

  public ServletOutputStream getOutputStream()
    throws IOException
  {
    if (this.writer != null)
      throw new IllegalStateException("getWriter() has already been called!");
    if (this.stream == null)
      this.stream = createOutputStream();
    return this.stream;
  }

  public PrintWriter getWriter()
    throws IOException
  {
    if (this.writer != null)
      return this.writer;
    if (this.stream != null)
      throw new IllegalStateException("getOutputStream() has already been called!");
    this.stream = createOutputStream();
    this.writer = new PrintWriter(new OutputStreamWriter(this.stream, this.origResponse.getCharacterEncoding()));
    return this.writer;
  }

  public void setContentLength(int paramInt)
  {
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.filter.GZIPResponseWrapper
 * JD-Core Version:    0.6.1
 */