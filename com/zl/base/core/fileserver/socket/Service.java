package com.zl.base.core.fileserver.socket;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface Service
{
  public abstract void service(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception;
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.socket.Service
 * JD-Core Version:    0.6.1
 */