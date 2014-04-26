package com.zl.base.core.fileserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.zl.base.core.file.directory;
import com.zl.base.core.fileserver.socket.Client;
import com.zl.base.core.fileserver.zip.FileEx;

public class WordClient extends Client {
	public WordClient(String paramString, int paramInt) throws IOException {
		super(paramString, paramInt);
	}

	public String GetWord(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5)
			throws Exception {
		String str1 = null;
		ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
				this.out);
		FileTCommand localFileTCommand = new FileTCommand();
		localFileTCommand.SetType(2001);
		localFileTCommand.SetOperator("word");
		Properties localProperties = new Properties();
		localProperties.setProperty("word.data", paramString1);
		localProperties.setProperty("word.tmpl", paramString3 + "/"
				+ paramString2);
		localProperties.setProperty("path.label", paramString5);
		localFileTCommand.SetParmeter(localProperties);
		localObjectOutputStream.writeObject(localFileTCommand);
		this.out.flush();
		ObjectInputStream localObjectInputStream = new ObjectInputStream(
				this.in);
		FileTResult localFileTResult = (FileTResult) localObjectInputStream
				.readObject();
		if (localFileTResult.GetCode() == 8)
			return null;
		str1 = localFileTResult.GetMessage();
		String str2 = FileEx.GetCurrentFilePath(paramString4,
				localFileTResult.GetMessage());
		long l = localFileTResult.GetFileCount();
		File localFile = new File(str2);
		directory.createdir(localFile.getParentFile().getAbsolutePath());
		FileOutputStream localFileOutputStream = new FileOutputStream(str2);
		byte[] arrayOfByte = new byte[1024];
		for (int i = this.in.read(arrayOfByte); i >= 0; i = this.in
				.read(arrayOfByte)) {
			localFileOutputStream.write(arrayOfByte, 0, i);
			l -= i;
			if (l <= 0L)
				break;
		}
		localFileOutputStream.close();
		return str1;
	}

	public void Close() throws Exception {
		ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
				this.out);
		FileTCommand localFileTCommand = new FileTCommand();
		localFileTCommand.SetType(2001);
		localFileTCommand.SetOperator("disconnect");
		localObjectOutputStream.writeObject(localFileTCommand);
		this.out.flush();
		Thread.sleep(1000L);
	}
}

/*
 * Location: E:\zllib\zllib.jar Qualified Name:
 * com.zl.base.core.fileserver.WordClient JD-Core Version: 0.6.1
 */