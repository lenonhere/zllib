package com.common;

import java.io.File;
import java.util.GregorianCalendar;

import com.qmx.ioutils.FileUtils;

public class DelTempFile extends Thread {
	public void run() {
		try {
			for (int i = 0; i < 1; i = 0) {
				String s = getClass().getResource("/").getPath();
				s = s.replaceAll("\\\\", "/");
				if ("/".equals(s.substring(0, 1)))
					s = s.substring(1, s.length());
				if ((s != null) && (!s.equals(""))) {
					String[] as = s.split("/");
					s = "";
					int j = as.length - 1;

					while (j >= 0) {
						if (as[j].equals("WEB-INF")) {
							for (int k = 0; k < j; k++) {
								s = s + as[k] + "/";
							}
							break;
						}
						j--;
					}
					if (!"".equals(s)) {
						s = s + "~temp/";
						if (FileUtils.checkdirexits(s)) {
							GregorianCalendar gregoriancalendar = new GregorianCalendar();
							long l = gregoriancalendar.getTime().getTime();
							long l1 = 3600000L;
							File[] afile = new File(s).listFiles();
							for (int i1 = 0; i1 < afile.length; i1++) {
								if ((afile[i1].isFile())
										&& (l - afile[i1].lastModified() > l1))
									afile[i1].delete();
							}
						}
					}
				}
				Thread.sleep(3600000L);
			}
		} catch (Exception localException) {
		}
	}
}