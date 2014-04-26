package com.zl.base.core.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GZIPResponseStream extends ServletOutputStream
{
  protected OutputStream bufferedOutput = null;
  protected boolean closed = false;
  protected HttpServletResponse response = null;
  protected ServletOutputStream output = null;
  private int bufferSize = 50000;

  public GZIPResponseStream(HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.response = paramHttpServletResponse;
    this.output = paramHttpServletResponse.getOutputStream();
    this.bufferedOutput = new ByteArrayOutputStream();
  }

  public void close()
    throws IOException
  {
    Object localObject;
    if ((this.bufferedOutput instanceof ByteArrayOutputStream))
    {
      localObject = (ByteArrayOutputStream)this.bufferedOutput;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
      byte[] arrayOfByte1 = ((ByteArrayOutputStream)localObject).toByteArray();
      localGZIPOutputStream.write(arrayOfByte1);
      localGZIPOutputStream.finish();
      byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
      this.response.setContentLength(arrayOfByte2.length);
      this.response.addHeader("Content-Encoding", "gzip");
      this.output.write(arrayOfByte2);
      this.output.flush();
      this.output.close();
      this.closed = true;
    }
    else if ((this.bufferedOutput instanceof GZIPOutputStream))
    {
      localObject = (GZIPOutputStream)this.bufferedOutput;
      ((GZIPOutputStream)localObject).finish();
      this.output.flush();
      this.output.close();
      this.closed = true;
    }
  }

  public void flush()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Cannot flush a closed output stream");
    this.bufferedOutput.flush();
  }

  public void write(int paramInt)
    throws IOException
  {
    if (this.closed)
      throw new IOException("Cannot write to a closed output stream");
    checkBufferSize(1);
    this.bufferedOutput.write((byte)paramInt);
  }

  private void checkBufferSize(int paramInt)
    throws IOException
  {
    if ((this.bufferedOutput instanceof ByteArrayOutputStream))
    {
      ByteArrayOutputStream localByteArrayOutputStream = (ByteArrayOutputStream)this.bufferedOutput;
      if (localByteArrayOutputStream.size() + paramInt > this.bufferSize)
      {
        this.response.addHeader("Content-Encoding", "gzip");
        byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
        GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(this.output);
        localGZIPOutputStream.write(arrayOfByte);
        this.bufferedOutput = localGZIPOutputStream;
      }
    }
  }

  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.closed)
      throw new IOException("Cannot write to a closed output stream");
    checkBufferSize(paramInt2);
    this.bufferedOutput.write(paramArrayOfByte, paramInt1, paramInt2);
  }

  public boolean closed()
  {
    return this.closed;
  }

  public void reset()
  {
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.filter.GZIPResponseStream
 * JD-Core Version:    0.6.1
 */