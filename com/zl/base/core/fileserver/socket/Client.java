package com.zl.base.core.fileserver.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

public abstract class Client
{
  protected Socket socket = null;
  protected InputStream in = null;
  protected OutputStream out = null;
  protected String orginPath = null;
  protected String tempPath = null;
  protected String serverAddress = null;
  protected int serverPort = 0;

  public Client(String paramString, int paramInt)
    throws IOException
  {
    this.serverAddress = paramString;
    this.serverPort = paramInt;
    this.socket = new Socket(this.serverAddress, this.serverPort);
    this.in = this.socket.getInputStream();
    this.out = this.socket.getOutputStream();
  }

  public Client(String paramString1, int paramInt, String paramString2)
    throws IOException
  {
    this.serverAddress = paramString1;
    this.serverPort = paramInt;
    this.socket = new Socket(this.serverAddress, this.serverPort);
    this.in = this.socket.getInputStream();
    this.out = this.socket.getOutputStream();
    this.orginPath = paramString2;
  }

  public String GetOrginPath()
  {
    return this.orginPath;
  }

  public void SetOrginPath(String paramString)
  {
    this.orginPath = paramString;
  }

  public String GetTempPath()
  {
    return this.tempPath;
  }

  public void SetTempPath(String paramString)
  {
    this.tempPath = paramString;
  }

  public String GetInetAddress()
  {
    return this.serverAddress;
  }

  public void SetInetAddress(String paramString)
  {
    this.serverAddress = paramString;
  }

  public int GetInetPort()
  {
    return this.serverPort;
  }

  public void SetInetPort(int paramInt)
  {
    this.serverPort = paramInt;
  }

  public void SetScoTimeOut(int paramInt)
    throws SocketException
  {
    this.socket.setSoTimeout(paramInt);
  }

  public Calendar GenPacketTimer()
  {
    return Calendar.getInstance();
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.socket.Client
 * JD-Core Version:    0.6.1
 */