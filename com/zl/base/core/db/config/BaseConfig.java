package com.zl.base.core.db.config;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.common.SystemConfig;
import com.zl.util.MethodFactory;

public class BaseConfig {
	/**
	 * base path for all method below
	 */
	protected String basePath = null;

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	// TODO Main
	public static void main(String[] args) {
		BaseConfig config = new BaseConfig();
		String basePath = SystemConfig.getDocBasePath() + "/WEB-INF";
		config.setBasePath(basePath);
		System.out.println("BasePath: " + basePath);

		System.out.println("--test config.getFile--");
		File file = config.getFile("procedure-config.xml");
		System.out.println(file);
		System.out.println("---------------------");

		System.out.println("--test config.getFiles--");
		List<String> fileNames = new ArrayList<String>();
		fileNames.add("web.xml");
		List<File> files = config.getFiles(null, ".xml", fileNames);
		System.out.println(files);
		System.out.println("---------------------");

		System.out.println("--test config.getAllFiles--");
		List<File> allFiles = config.getAllFiles("BaseConfig", ".class", null);
		System.out.println(allFiles);
		System.out.println("---------------------");
	}

	/**
	 * get a file with name <i>filename</i> under basePath
	 *
	 * @param filename
	 *            String
	 * @return File
	 */
	protected File getFile(String filename) {
		return new File(basePath, filename);
	}

	/**
	 * get files under path (base), with <i>prefix</i> & <i>suffix</i> as
	 * supplied, and with name not in list of <i>fileNames</i>
	 *
	 * @param prefix
	 *            String
	 * @param suffix
	 *            String
	 * @param fileNames
	 *            List<String>
	 * @return List
	 */
	protected List<File> getFiles(String prefix, String suffix,
			List<String> fileNames) {
		FileFilter nameFilter = new NameFileFilter(fileNames, null);
		FileFilter fixFilter = new FixFileFilter(prefix, suffix, nameFilter);

		File path = new File(basePath);
		File[] files = path.listFiles(fixFilter);

		List<File> fileList = new ArrayList<File>();
		int n = (files == null ? 0 : files.length);
		for (int i = 0; i < n; i++) {
			fileList.add(files[i]);
		}
		return fileList;
	}

	/**
	 * get all files under path (base)&subpaths with <i>prefix</i> &
	 * <i>suffix</i> as supplied, and with name not in list of <i>fileNames</i>
	 *
	 * @param prefix
	 *            String
	 * @param suffix
	 *            String
	 * @param fileNames
	 *            List<String>
	 * @return List
	 */
	protected List<File> getAllFiles(String prefix, String suffix,
			List<String> fileNames) {

		FileFilter nameFilter = new NameFileFilter(fileNames, null);
		FileFilter fixFilter = new FixFileFilter(prefix, suffix, nameFilter);

		File path = new File(basePath);
		return getSubFiles(path, fixFilter);
	}

	private List<File> getSubFiles(File path, FileFilter filter) {
		File[] files = path.listFiles();
		List<File> fileList = new ArrayList<File>();
		int n = (files == null ? 0 : files.length);
		for (int i = 0; i < n; i++) {
			if (files[i].isDirectory()) {
				List<File> subFileList = getSubFiles(files[i], filter);
				fileList.addAll(subFileList);
			}
			if (filter.accept(files[i])) {
				fileList.add(files[i]);
			}
		}
		return fileList;
	}

	protected URL getFileURL(ServletContext servletcontext, String s)
			throws MalformedURLException {
		return servletcontext.getResource(this.basePath + s);
	}

	protected List<String> getFilesURL(ServletContext servletcontext, String s,
			String s1, List<Object> list) {
		Object[] aobj = servletcontext.getResourcePaths(this.basePath)
				.toArray();
		ArrayList<String> arraylist = new ArrayList<String>();
		int i = arraylist != null ? aobj.length : 0;

		for (int j = 0; j < i; j++) {
			String s3 = aobj[j].toString();
			boolean flag1 = false;
			s3 = MethodFactory.replace(s3, this.basePath, "", 1);
			if ((s3.startsWith(s)) && (s3.endsWith(s1))) {
				int k = 0;

				while (k < list.size()) {
					if (s3.endsWith(list.get(k).toString())) {
						flag1 = true;
						break;
					}
					k++;
				}
				if (!flag1)
					arraylist.add(aobj[j].toString());
			}
		}
		return arraylist;
	}

	/**
	 * NameFileFilter to filter files with a set of names
	 */
	protected class NameFileFilter implements FileFilter {

		protected List<String> fileNames;
		protected FileFilter chainFilter;

		NameFileFilter(List<String> fileNames, FileFilter chainFilter) {
			this.fileNames = fileNames;
			this.chainFilter = chainFilter;
		}

		public boolean accept(File file) {
			if (file.isDirectory()) {
				return false;
			}
			if (fileNames != null && fileNames.contains(file.getName())) {
				return false;
			}
			return chainFilter == null ? true : chainFilter.accept(file);
		}
	}

	/**
	 * FixFileFilter to filter files with prefix & suffix
	 */
	protected class FixFileFilter implements FileFilter {

		protected String prefix;
		protected String suffix;
		protected FileFilter chainFilter;

		FixFileFilter(String prefix, String suffix, FileFilter chainFilter) {
			this.prefix = prefix;
			this.suffix = suffix;
			this.chainFilter = chainFilter;
		}

		public boolean accept(File file) {
			if (file.isDirectory()) {
				return false;
			}
			if ((prefix != null && !file.getName().startsWith(prefix))
					|| (suffix != null && !file.getName().endsWith(suffix))) {
				return false;
			}
			return chainFilter == null ? true : chainFilter.accept(file);
		}
	}

}
