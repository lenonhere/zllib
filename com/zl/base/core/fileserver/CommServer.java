package com.zl.base.core.fileserver;

import com.zl.base.core.fileserver.socket.Server;
import com.zl.base.core.fileserver.socket.Service;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class CommServer
  implements Service
{
  private singleFileServer singlefileserver = null;
  private WordServer wordserver = null;

  public void SetsingleFileServer(singleFileServer paramsingleFileServer)
  {
    this.singlefileserver = paramsingleFileServer;
  }

  public void SetWordServer(WordServer paramWordServer)
  {
    this.wordserver = paramWordServer;
  }

  public void service(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception
  {
    while (true)
    {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(paramInputStream);
      FileTCommand localFileTCommand = new FileTCommand();
      try
      {
        localFileTCommand = (FileTCommand)localObjectInputStream.readObject();
      }
      catch (Exception localException)
      {
        localException = localException;
        System.out.println(localException.toString());
      }
      finally
      {
      }
      if (localFileTCommand != null)
      {
        if (localFileTCommand.GetType() == 1001)
          this.singlefileserver.service(paramInputStream, paramOutputStream, localFileTCommand);
        if (localFileTCommand.GetType() == 2001)
          this.wordserver.service(paramInputStream, paramOutputStream, localFileTCommand);
        if (localFileTCommand.GetOperator().equals("disconnect"))
          break;
      }
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      ServerProperties localServerProperties = new ServerProperties("/fileserver.properties");
      Properties localProperties = localServerProperties.GetProperties();
      WordServer localWordServer = new WordServer();
      localWordServer.SetOutPath(localProperties.getProperty("temppath"));
      localWordServer.SetTmplPath(localProperties.getProperty("template"));
      CommServer localCommServer = new CommServer();
      localCommServer.SetWordServer(localWordServer);
      Server localServer = new Server();
      Integer localInteger = new Integer(localProperties.getProperty("comm.port"));
      localServer.addService(localCommServer, localInteger.intValue());
      localServer.addService(localWordServer, 4006);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
    }
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.CommServer
 * JD-Core Version:    0.6.1
 */