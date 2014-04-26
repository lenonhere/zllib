package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class FileCopyer {

	public static class FileCopyInfo {

		private String srcFile;

		private String destFile;

		private boolean succeed;

		private String description;

		public FileCopyInfo() {
		}

		public FileCopyInfo(String srcFile, String destFile, boolean succeed) {
			this(srcFile, destFile, succeed, null);
		}

		public FileCopyInfo(String srcFile, String destFile, boolean succeed,
				String description) {
			this.srcFile = srcFile;
			this.destFile = destFile;
			this.succeed = succeed;
			this.description = description;
		}

		public String getSrcFile() {
			return srcFile;
		}

		public void setSrcFile(String srcFile) {
			this.srcFile = srcFile;
		}

		public String getDestFile() {
			return destFile;
		}

		public void setDestFile(String destFile) {
			this.destFile = destFile;
		}

		public boolean isSucceed() {
			return succeed;
		}

		public void setSucceed(boolean succeed) {
			this.succeed = succeed;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	private java.io.FilenameFilter filenameFilter;

	public FileCopyer() {
	}

	private void log(String msg) {
		// LoggerFacade.info(msg);
	}

	public static void main(String[] args) {
		FileCopyer fileCopyer1 = new FileCopyer();
		Set set = fileCopyer1
				.listChildren(new File(
						"F:\\java others\\struts-console-3.6\\struts-console-3.6\\org.apache.struts.console\\lib\\struts-console\\classes"));
		// System.out.println(set);
	}

	public boolean copyFile(File fileIn, File fileOut, boolean overwrite)
			throws FileNotFoundException, IOException {

		if (!fileIn.isFile()) {
			throw new FileNotFoundException();
		}

		if (!overwrite && fileOut.isFile()) {

			return false;
		}

		if (fileIn.equals(fileOut)) {

			return false;
		}

		File fileOutParent = fileOut.getParentFile();
		if (!fileOutParent.isDirectory()) {
			fileOutParent.mkdirs();
		}

		InputStream input = null;
		OutputStream output = null;

		try {
			input = new FileInputStream(fileIn);
			output = new FileOutputStream(fileOut);

			int readBytes = 0;
			int blockSize = 1024;
			byte[] b = new byte[blockSize];

			int remain = 0;
			while ((remain = input.available()) > 0) {
				readBytes = input.read(b, 0, Math.min(remain, blockSize));
				output.write(b, 0, readBytes);
			}

			return true;
		} catch (IOException e) {
			throw e;
		} finally {

			if (output != null) {

				try {
					output.close();
				} catch (IOException e) {
				}
			}

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
				}

			}
		}

	}

	public void copyFold(File foldIn, File foldOut, boolean includeSubfolders,
			boolean overwrite) {

		if (!foldIn.isDirectory()) {

			return;
		}

		if (foldIn.equals(foldOut)) {

			return;
		}

		if (!foldOut.isDirectory()) {
			foldOut.mkdirs();
		}

		String[] fileNames = foldIn.list(filenameFilter);

		for (int i = 0; i < fileNames.length; i++) {

			File fileIn = new File(foldIn, fileNames[i]);
			File fileOut = new File(foldOut, fileNames[i]);

			if (fileIn.isFile()) {

				try {
					copyFile(fileIn, fileOut, overwrite);
				} catch (IOException ex) {
					// LoggerFacade.printStackTrace(ex);
				}
			} else {

				if (includeSubfolders) {
					copyFold(fileIn, fileOut, includeSubfolders, overwrite);
				} else {
					fileOut.mkdir();
				}
			}
		}

	}

	public void duplicateFoldStructure(File foldIn, File foldOut,
			boolean limitLevels, int levels) {

		if (limitLevels && levels <= 0) {

			return;
		}

		if (!foldIn.isDirectory()) {

			return;
		}

		if (foldIn.equals(foldOut)) {

			return;
		}

		if (!foldOut.isDirectory()) {
			foldOut.mkdirs();
		}

		String[] fileNames = foldIn.list(filenameFilter);

		for (int i = 0; i < fileNames.length; i++) {

			File fileIn = new File(foldIn, fileNames[i]);
			File fileOut = new File(foldOut, fileNames[i]);

			if (fileIn.isDirectory()) {
				duplicateFoldStructure(fileIn, fileOut, limitLevels, levels - 1);
			}
		}

	}

	public int getChildFileCount(File fold) {

		if (!fold.isDirectory()) {

			return 0;
		}

		int count = 0;

		File[] children = fold.listFiles(filenameFilter);

		for (int i = 0; i < children.length; i++) {

			if (children[i].isDirectory()) {
				count += getChildFileCount(children[i]);
			} else {
				count++;
			}
		}

		return count;
	}

	public static boolean isAncestor(File ancestor, File descendant) {

		if (!(ancestor.exists() && descendant.exists())) {

			return false;
		}

		File parent = null;

		while ((parent = descendant.getParentFile()) != null) {

			if (ancestor.equals(parent)) {

				return true;
			}

			descendant = parent;

		}

		return false;

	}

	public static boolean verifyFile(File fileIn, File fileOut) {

		if (!fileIn.isFile() || !fileOut.isFile()) {
			return false;
		}

		if (fileIn.equals(fileOut)) {

			return true;
		}

		return fileIn.length() == fileOut.length();

	}

	public boolean verifyFold(File foldIn, File foldOut,
			boolean includeSubfolders) {

		if (!foldIn.isDirectory() || !foldOut.isDirectory()) {

			return false;
		}

		if (foldIn.equals(foldOut)) {

			return true;
		}

		String[] fileNames = foldIn.list(filenameFilter);

		for (int i = 0; i < fileNames.length; i++) {

			File fileIn = new File(foldIn, fileNames[i]);
			File fileOut = new File(foldOut, fileNames[i]);

			if (fileIn.isFile()) {
				if (!verifyFile(fileIn, fileOut)) {
					return false;
				}
			} else {
				if (includeSubfolders) {
					if (!verifyFold(fileIn, fileOut, includeSubfolders)) {
						return false;
					}
				}
			}
		}
		return true;

	}

	public static String getExtension(File file) {
		String ext = null;
		String s = file.getName();
		int dotPos = s.lastIndexOf('.');

		if (dotPos > 0 && dotPos < s.length() - 1) {
			ext = s.substring(dotPos + 1).toLowerCase();
		}
		return ext;
	}

	public java.io.FilenameFilter getFilenameFilter() {
		return filenameFilter;
	}

	public void setFilenameFilter(java.io.FilenameFilter filenameFilter) {
		this.filenameFilter = filenameFilter;
	}

	public static boolean writeFile(byte[] bytes, File fileOut,
			boolean overwrite) throws FileNotFoundException, IOException {

		File fileOutParent = fileOut.getParentFile();
		if (!fileOutParent.isDirectory()) {
			fileOutParent.mkdirs();
		}

		OutputStream output = null;

		try {
			output = new FileOutputStream(fileOut, !overwrite);

			output.write(bytes, 0, bytes.length);

			return true;
		} catch (IOException e) {
			throw e;
		} finally {

			if (output != null) {

				try {
					output.close();
				} catch (IOException e) {
				}
			}

		}

	}

	public static byte[] readFile(File fileIn) throws FileNotFoundException,
			IOException {

		byte[] bytes = null;
		InputStream input = null;

		try {
			input = new FileInputStream(fileIn);
			bytes = new byte[input.available()];

			input.read(bytes, 0, bytes.length);

		} catch (IOException ex) {
			throw ex;
		} finally {

			if (input != null) {

				try {
					input.close();
				} catch (IOException e) {
				}

			}
		}

		return bytes;
	}

	public static DiffInfo compareFold(File leftFold, File rightFold) {

		if (!leftFold.isDirectory() || !rightFold.isDirectory()
				|| leftFold.equals(rightFold)) {
			return new DiffInfo();
		}

		Set leftSet = listChildren(leftFold);
		Set rightSet = listChildren(rightFold);

		DiffInfo diffInfo = DiffInfo.makeDiffInfo(leftSet, rightSet);

		return diffInfo;
	}

	public static Set listChildren(File fold) {
		Set set = new HashSet();
		listChildren(fold, "", set);
		return set;
	}

	static void listChildren(File fold, String path, Set set) {

		// System.out.println("path " + path);
		if (!fold.isDirectory()) {
			return;
		}

		String[] children = fold.list();

		for (int i = 0; i < children.length; i++) {
			String selfPath = path + children[i];
			File selfFile = new File(fold, children[i]);

			if (selfFile.isDirectory()) {
				selfPath += "/";
				set.add(selfPath);

				listChildren(selfFile, selfPath, set);
			} else {
				set.add(selfPath);

			}

			// System.out.println("just added " + set.toArray()[set.size() -1]);
		}

	}

}
