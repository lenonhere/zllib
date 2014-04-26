// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

package com.zl.base.core.document;

import java.io.*;
import java.util.ArrayList;
import org.apache.commons.beanutils.PropertyUtils;

// Referenced classes of package com.zl.base.core.document:
//            contentStruct, rstStruct, testdate

public class createHtmlDoc {

	public createHtmlDoc() {
	}

	public static String create(String s, String s1, ArrayList arraylist)
			throws Exception {
		String s2 = "";
		try {
			File file = new File(s);
			if (!file.exists())
				throw new Exception("模板文件：" + s + "不存在！");
			// throw new Exception("\u6A21\u677F\u6587\u4EF6\uFF1A" + s +
			// "\u4E0D\u5B58\u5728\uFF01");
			StringBuffer stringbuffer = new StringBuffer();
			FileInputStream fileinputstream = new FileInputStream(s);
			Long long1 = new Long(file.length());
			byte abyte0[] = new byte[long1.intValue()];
			fileinputstream.read(abyte0, 0, long1.intValue());
			fileinputstream.close();
			String s3 = toGb2312(abyte0);
			contentStruct contentstruct = new contentStruct();
			contentstruct.setType("0");
			analyseDoc(contentstruct, s3);
			s2 = createFile(contentstruct, s, "c:\\", arraylist);
		} catch (Exception exception) {
			throw exception;
		}
		return s2;
	}

	private static String createFile(contentStruct contentstruct, String s,
			String s1, ArrayList arraylist) throws Exception {
		String s2 = "";
		File file = new File(s);
		String s3 = file.getName();
		String as[] = contentStruct.split(s3, ".");
		s3 = as[0] + contentStruct.getdatestring();
		if (as.length > 1)
			s3 = s3 + "." + as[1];
		s2 = s1 + "/" + s3;
		try {
			s2 = contentStruct.replace(s2, "\\", "/");
			s2 = contentStruct.replace(s2, "//", "/");
		} catch (Exception exception) {
			throw exception;
		}
		try {
			FileOutputStream fileoutputstream = new FileOutputStream(s2);
			replaceContent(fileoutputstream, contentstruct, arraylist, null);
			fileoutputstream.close();
		} catch (Exception exception1) {
			throw exception1;
		}
		return s2;
	}

	private static void replaceContent(FileOutputStream fileoutputstream,
			contentStruct contentstruct, ArrayList arraylist, Object obj)
			throws Exception {
		if (contentstruct.getType().equals("0"))
			fileoutputstream.write(contentstruct.getFieldname().getBytes());
		else if (contentstruct.getType().equals("1")) {
			String s = "";
			if (obj == null) {
				String s2 = contentstruct.getTablename();
				ArrayList arraylist1 = null;
				if (s2 == null)
					s2 = "";
				arraylist1 = getrst(s2, arraylist);
				if (arraylist1 != null)
					s = getvalue(contentstruct.getFieldname(), arraylist1,
							contentstruct.getRowIndex().intValue());
			} else {
				s = getvalue(contentstruct.getFieldname(), obj);
			}
			fileoutputstream.write(s.getBytes());
		}
		if (contentstruct.getType().equals("2")) {
			fileoutputstream.write(contentstruct.getFieldname().getBytes());
			String s1 = "";
			String s3 = contentstruct.getTablename();
			ArrayList arraylist2 = null;
			if (contentstruct.getChild().size() > 0) {
				String s4 = ((contentStruct) contentstruct.getChild().get(0))
						.getTablename();
				if (s4 == null)
					s4 = "";
				arraylist2 = getrst(s4, arraylist);
			}
			if (arraylist2 != null) {
				for (int j = 0; j < arraylist2.size(); j++) {
					Object obj1 = arraylist2.get(j);
					for (int l = 0; l < contentstruct.getChild().size(); l++) {
						contentStruct contentstruct3 = (contentStruct) contentstruct
								.getChild().get(l);
						replaceContent(fileoutputstream, contentstruct3,
								arraylist, obj1);
					}

				}

			} else {
				for (int k = 0; k < contentstruct.getChild().size(); k++) {
					contentStruct contentstruct2 = (contentStruct) contentstruct
							.getChild().get(k);
					replaceContent(fileoutputstream, contentstruct2, arraylist,
							null);
				}

			}
		}
		if (contentstruct.getType().equals("0")
				|| contentstruct.getType().equals("1")) {
			for (int i = 0; i < contentstruct.getChild().size(); i++) {
				contentStruct contentstruct1 = (contentStruct) contentstruct
						.getChild().get(i);
				replaceContent(fileoutputstream, contentstruct1, arraylist,
						null);
			}

		}
	}

	private static String getvalue(String s, ArrayList arraylist, int i) {
		String s1 = "";
		if (arraylist.size() > i)
			try {
				Object obj = PropertyUtils.getProperty(arraylist.get(i - 1), s);
				if (obj != null)
					s1 = String.valueOf(obj);
				else
					s1 = "";
			} catch (Exception exception) {
			}
		return s1;
	}

	private static String getvalue(String s, Object obj) {
		String s1 = "";
		if (obj != null)
			try {
				s1 = String.valueOf(PropertyUtils.getProperty(obj, s));
			} catch (Exception exception) {
			}
		return s1;
	}

	private static ArrayList getrst(String s, ArrayList arraylist) {
		ArrayList arraylist1 = null;
		if (arraylist == null)
			arraylist1 = null;
		else if (s.trim().equals("")) {
			arraylist1 = ((rstStruct) arraylist.get(0)).getRst();
		} else {
			int i = 0;
			do {
				if (i >= arraylist.size())
					break;
				rstStruct rststruct = (rstStruct) arraylist.get(i);
				if (rststruct.getName().toUpperCase().equals(s.toUpperCase())) {
					arraylist1 = rststruct.getRst();
					break;
				}
				i++;
			} while (true);
		}
		return arraylist1;
	}

	private static contentStruct addContent(contentStruct contentstruct,
			String s, String s1) {
		if (!s.equals("")) {
			contentStruct contentstruct1 = new contentStruct();
			contentstruct1.setFieldname(s);
			contentstruct1.setType(s1);
			contentstruct.getChild().add(contentstruct1);
			return contentstruct1;
		} else {
			return null;
		}
	}

	private static int analyseDoc(contentStruct contentstruct, String s) {
		int i = 0;
		String s1 = "";
		byte byte0 = 0;
		String s3 = "";
		String s4 = "";
		boolean flag1 = false;
		boolean flag3 = false;
		String s5 = "";
		for (int j = 0; j < s.length(); j++) {
			i = j;
			String s2 = s.substring(j, j + 1);
			if (flag3) {
				s3 = s3 + s2;
				if (byte0 == 0) {
					if (s2.equals("["))
						byte0 = 1;
					else
						flag1 = true;
				} else if (byte0 == 1)
					if (s2.equals("]"))
						byte0 = 2;
					else if (s2.equals("@") || s2.equals("["))
						flag1 = true;
				if (flag1) {
					flag3 = false;
					flag1 = false;
					byte0 = 0;
					addContent(contentstruct, s4 + s3, "0");
					s5 = "";
					s4 = "";
					s3 = "";
				}
				if (byte0 == 2) {
					addContent(contentstruct, s4, "0");
					s4 = "";
					s5 = "";
					addContent(contentstruct, s3, "1");
					s3 = "";
					flag3 = false;
					flag1 = false;
					byte0 = 0;
					s3 = "";
				}
			} else if (s2.equals("@")) {
				addContent(contentstruct, s4, "0");
				s4 = "";
				s5 = "";
				s3 = s2;
				flag3 = true;
				flag1 = false;
				byte0 = 0;
			} else {
				s4 = s4 + s2;
			}
			if (s5.length() >= 5) {
				if (s5.equals("<zlcb>")) {
					if (s4.equals("")) {
						s4 = s3;
						s3 = "";
					}
					String s6 = s4.substring(0, s4.length() - 7);
					contentStruct contentstruct1 = addContent(contentstruct, s3
							+ s6 + " ", "2");
					s6 = s.substring(j, s.length());
					int k = analyseDoc(contentstruct1, s6);
					s5 = "";
					s4 = "";
					s3 = "";
					flag3 = false;
					flag1 = false;
					byte0 = 0;
					s3 = "";
					j += k;
					continue;
				}
				if ((s5 + s2).equals("</zlcb>")) {
					if (s4.equals("")) {
						s4 = s3;
						s3 = "";
					}
					String s7 = s4.substring(0, s4.length() - 7);
					contentStruct contentstruct2 = addContent(contentstruct, s3
							+ s7, "0");
					s5 = "";
					s4 = "";
					s3 = "";
					boolean flag4 = false;
					boolean flag2 = false;
					boolean flag = false;
					s3 = "";
					break;
				}
				s5 = s5 + s2;
				s5 = s5.substring(s5.length() - 6, s5.length());
			} else {
				s5 = s5 + s2;
			}
		}

		addContent(contentstruct, s4, "0");
		return i;
	}

	public static String toGb2312(byte abyte0[]) {
		String s = "";
		try {
			s = new String(abyte0, "GB2312");
			int i = 0;
			do {
				if (i >= abyte0.length)
					break;
				byte byte0 = abyte0[i];
				if (byte0 == 0) {
					s = new String(abyte0, 0, i, "GB2312");
					break;
				}
				i++;
			} while (true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return s;
	}

	public static void main(String args[]) {
		try {
			ArrayList arraylist = new ArrayList();
			rstStruct rststruct = new rstStruct();
			rststruct.setName("rst1");
			testdate testdate1 = new testdate();
			testdate1.a = "a1";
			testdate1.b = "b1";
			testdate1.c = "c1";
			testdate1.d = "d1";
			testdate1.e = "e1";
			testdate1.f = "f1";
			testdate1.g = "g1";
			testdate1.h = "h1";
			rststruct.getRst().add(testdate1);
			testdate1 = new testdate();
			testdate1.a = "a2";
			testdate1.b = "b2";
			testdate1.c = "c3";
			testdate1.d = "d4";
			testdate1.e = "e5";
			testdate1.f = "f6";
			testdate1.g = "g7";
			testdate1.h = "h8";
			rststruct.getRst().add(testdate1);
			arraylist.add(rststruct);
			create("c:\\index.htm", "c:\\", arraylist);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}
}
