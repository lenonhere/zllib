package com.zl.base.core.fileserver.socket.secret;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

public class SecretSocket
{
  private Key key = null;
  private Cipher outCipher = null;
  private Cipher inCipher = null;
  private CipherInputStream cis = null;
  private CipherOutputStream cos = null;
  private Socket socket = null;
  private String algorithm = "DES";

  public SecretSocket(Socket paramSocket, Key paramKey)
  {
    this.socket = paramSocket;
    this.key = paramKey;
    this.algorithm = paramKey.getAlgorithm();
    initializeCipher();
  }

  private void initializeCipher()
  {
    try
    {
      this.outCipher = Cipher.getInstance(this.algorithm);
      this.outCipher.init(1, this.key);
      this.inCipher = Cipher.getInstance(this.algorithm);
      this.inCipher.init(2, this.key);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      localNoSuchPaddingException.printStackTrace();
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      localInvalidKeyException.printStackTrace();
    }
  }

  public InputStream getInputStream()
    throws IOException
  {
    InputStream localInputStream = this.socket.getInputStream();
    this.cis = new CipherInputStream(localInputStream, this.inCipher);
    return this.cis;
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    OutputStream localOutputStream = this.socket.getOutputStream();
    this.cos = new CipherOutputStream(localOutputStream, this.outCipher);
    return this.cos;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.socket.secret.SecretSocket
 * JD-Core Version:    0.6.1
 */