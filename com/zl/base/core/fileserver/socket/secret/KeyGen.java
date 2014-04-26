package com.zl.base.core.fileserver.socket.secret;

import com.sun.crypto.provider.SunJCE;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGen
{
  public static final String KEY_FILE = "secret.key";
  public static final String ALGORITHM = "DES";

  public static void main(String[] paramArrayOfString)
  {
    Security.addProvider(new SunJCE());
    new KeyGen();
  }

  public KeyGen()
  {
    KeyGenerator localKeyGenerator = null;
    try
    {
      localKeyGenerator = KeyGenerator.getInstance("DES");
      SecretKey localSecretKey = localKeyGenerator.generateKey();
      writeKey("secret.key", localSecretKey);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
  }

  private void writeKey(String paramString, Object paramObject)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
      localObjectOutputStream.writeObject(paramObject);
      localObjectOutputStream.flush();
      localFileOutputStream.close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static Key getSecretKey()
  {
    Security.addProvider(new SunJCE());
    FileInputStream localFileInputStream = null;
    try
    {
      localFileInputStream = new FileInputStream("secret.key");
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    Key localKey = null;
    try
    {
      ObjectInputStream localObjectInputStream = null;
      localObjectInputStream = new ObjectInputStream(localFileInputStream);
      localKey = null;
      localKey = (Key)localObjectInputStream.readObject();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    System.out.println("key = " + localKey);
    return localKey;
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.fileserver.socket.secret.KeyGen
 * JD-Core Version:    0.6.1
 */