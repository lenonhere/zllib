package com.web.action;

import static com.zl.util.MethodFactory.print;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.web.form.DemosForm;
import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlParam;
import com.zl.base.core.db.SqlReturn;
import com.zl.base.core.file.filestruct;
import com.zl.base.core.file.fileupload;
import com.zl.util.OptionHold;

public class DemosAction extends DispatchAction {

	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DemosForm demosForm = (DemosForm) form;
		FormFile localFormFile = demosForm.getUploadFormFile();
		// E:\ProgramFiles\Tomcats\tomcat6.0\temp\files
		filestruct fs = fileupload.upload(request, response, localFormFile, "",
				"files", false, null);
		print(fs.getpathfilename());

		return mapping.findForward("upload");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward demos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DemosForm demosForm = (DemosForm) form;

		/* DateTag,ColorpickTag */
		demosForm.setColor("#FFFF00");
		demosForm.setDate("2013/01/25");

		/* SelectTag, ListTag */
		ArrayList<OptionHold> arrayList = new ArrayList<OptionHold>();
		arrayList.add(new OptionHold("A", "A0", "A1", "A2"));
		arrayList.add(new OptionHold("B", "B0", "B1", "B2"));
		arrayList.add(new OptionHold("C", "C0", "C1", "C2"));
		request.setAttribute("options", arrayList);

		/* SelectGroupTag */
		ArrayList<OptionHold> gArrayList = new ArrayList<OptionHold>();
		gArrayList.add(new OptionHold("A", "A一部", "", "A1", "A2", "GroupA"));
		gArrayList.add(new OptionHold("1", "A一", "A11", "A12", "A1", false));
		gArrayList.add(new OptionHold("2", "A二", "A21", "A22", "A2", false));
		gArrayList.add(new OptionHold("B", "B二部", "", "B1", "B2", "GroupB"));
		gArrayList.add(new OptionHold("3", "B一", "B11", "B12", "B1", false));
		gArrayList.add(new OptionHold("4", "B二", "B21", "B22", "B2", false));
		request.setAttribute("groupoptions", gArrayList);

		return mapping.findForward("demos");
	}

	/**
	 * TreeViewAction
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setArrayListTree(request);
		setXMLTree(request);
		setDBTree(request);
		return mapping.findForward("tree");
	}

	private static void setArrayListTree(HttpServletRequest request) {
		ArrayList<DemosForm> arrayList = new ArrayList<DemosForm>();
		DemosForm demosForm = new DemosForm();
		demosForm.setnodeid("1");
		demosForm.setnodetext("A");
		demosForm.setnodeparentid("1");
		arrayList.add(demosForm);

		demosForm = new DemosForm();
		demosForm.setnodeid("2");
		demosForm.setnodetext("A1");
		demosForm.setnodeparentid("1");
		demosForm.seturl("A01");
		arrayList.add(demosForm);

		demosForm = new DemosForm();
		demosForm.setnodeid("3");
		demosForm.setnodetext("A2");
		demosForm.seturl("A02");
		demosForm.setnodeparentid("1");
		arrayList.add(demosForm);
		//
		demosForm = new DemosForm();
		demosForm.setnodeid("4");
		demosForm.setnodetext("B");
		demosForm.setnodeparentid("4");
		arrayList.add(demosForm);

		demosForm = new DemosForm();
		demosForm.setnodeid("2");
		demosForm.setnodetext("B1");
		demosForm.setnodeparentid("4");
		demosForm.seturl("B01");
		arrayList.add(demosForm);

		request.setAttribute("listTree", arrayList);
	}

	private static void setXMLTree(HttpServletRequest request) {
		String str = "<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?><Node title='地域类别' id='0'>";
		str += "<Node title='进口' id='232'></Node><Node title='港澳台' id='233'><Node title='香港' id='326'>";
		str += "</Node><Node title='澳门' id='327' check='1'></Node><Node title='台湾' id='328'></Node>";
		str += "</Node><Node title='国内' id='234'><Node title='浙江省外' id='273' check='0'>";
		str += "<Node title='上海市' id='296'></Node><Node title='江苏' id='297'></Node><Node title='安徽' id='298'></Node><Node title='江西' id='299'></Node><Node title='福建' id='300'></Node><Node title='北京' id='301'></Node>";
		str += "<Node title='黑龙江' id='302'></Node><Node title='吉林' id='303'></Node><Node title='辽宁' id='304'>";
		str += "</Node><Node title='山东' id='305'></Node><Node title='天津' id='306'></Node><Node title='河北' id='307'></Node>";
		str += "<Node title='河南' id='308'></Node><Node title='山西' id='309' check='0'></Node>";
		str += "<Node title='陕西' id='310'></Node><Node title='甘肃' id='311'></Node>";
		str += "<Node title='宁夏' id='312'></Node><Node title='湖北' id='313'></Node>";
		str += "<Node title='湖南' id='314'></Node><Node title='广东' id='315'></Node>";
		str += "<Node title='广西' id='316'></Node><Node title='云南' id='317'></Node>";
		str += "<Node title='贵州' id='318'></Node><Node title='四川' id='319'></Node>";
		str += "<Node title='青海' id='320'></Node><Node title='西藏' id='322'></Node>";
		str += "<Node title='新疆' id='323'></Node><Node title='内蒙古' id='324'></Node>";
		str += "<Node title='重庆' id='325'></Node><Node title='海南' id='347'></Node>";
		str += "</Node><Node title='浙江省' id='295'></Node></Node><Node title='省内区外' id='274'>";
		str += "<Node title='湖州地区' id='286'></Node><Node title='嘉兴地区' id='287'></Node>";
		str += "<Node title='绍兴地区' id='288'></Node><Node title='宁波地区' id='289'></Node>";
		str += "<Node title='金华地区' id='290'></Node><Node title='舟山地区' id='291'></Node>";
		str += "<Node title='温州地区' id='292'></Node><Node title='台州地区' id='293'></Node>";
		str += "<Node title='丽水地区' id='294'></Node><Node title='衢州地区' id='329'></Node>";
		str += "</Node><Node title='富阳市' id='280'></Node><Node title='杭州地区' id='276'>";
		str += "<Node title='杭州市' id='278'></Node><Node title='萧山市' id='279'></Node>";
		str += "<Node title='桐庐市' id='281'></Node><Node title='建德市' id='282'></Node>";
		str += "<Node title='余杭市' id='283'></Node><Node title='临安市' id='284'></Node>";
		str += "<Node title='淳安市' id='285'></Node></Node></Node>";
		request.setAttribute("xmlTree", str);
	}

	private static void setDBTree(HttpServletRequest request) throws Exception {
		String sql = "select menucode,parentcode,menuname,ismenu,operatesn from g_menu m where m.isactive=1";
		ArrayList<BasicDynaBean> list = Executer.getInstance()
				.ExecSeletSQL(sql).getResultSet();
		request.setAttribute("tree", list);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward charts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		line(form, request);
		pie(form, request);
		bar(form, request);
		return mapping.findForward("charts");
	}

	/**
	 * @param form
	 * @param request
	 * @return
	 */
	private void line(ActionForm form, HttpServletRequest request) {
		ArrayList<DemosForm> arrayList1 = new ArrayList<DemosForm>();
		ArrayList<DemosForm> arrayList2 = new ArrayList<DemosForm>();
		DemosForm chartForm = new DemosForm();
		chartForm.setcaption("杭州");
		chartForm.setproperty("value1");
		arrayList1.add(chartForm);
		chartForm = new DemosForm();
		chartForm.setcaption("宁波");
		chartForm.setproperty("value2");
		arrayList1.add(chartForm);
		int i = 5;
		for (int j = 0; j < 50; j++) {
			chartForm = new DemosForm();
			if (i == 5) {
				chartForm.setxvalue("value" + String.valueOf(j));
				i = -1;
			} else {
				chartForm.setxvalue(" ");
			}
			i += 1;
			if (j > 8)
				chartForm.setvalue1(null);
			else
				chartForm.setvalue1(String.valueOf(Math.random() * 100.0D));
			if (j > 2)
				chartForm.setvalue2(String.valueOf(Math.random() * 100.0D));
			else
				chartForm.setvalue2(null);
			arrayList2.add(chartForm);
		}
		request.setAttribute("line", arrayList1);
		request.setAttribute("lineList", arrayList2);
	}

	/**
	 * @param form
	 * @param request
	 * @return
	 */
	private void pie(ActionForm form, HttpServletRequest request) {
		ArrayList<DemosForm> arrayList1 = new ArrayList<DemosForm>();
		ArrayList<DemosForm> arrayList2 = new ArrayList<DemosForm>();
		DemosForm chartForm = new DemosForm();
		chartForm.setcaption("杭州");
		chartForm.setproperty("value1");
		arrayList1.add(chartForm);
		chartForm = new DemosForm();
		chartForm.setcaption("宁波");
		chartForm.setproperty("value2");
		arrayList1.add(chartForm);
		for (int i = 0; i < 10; i++) {
			chartForm = new DemosForm();
			chartForm.setxvalue("value" + String.valueOf(i));
			chartForm.setvalue1(String.valueOf(Math.random() * 100.0D));
			chartForm.setvalue2(String.valueOf(Math.random() * 100.0D));
			arrayList2.add(chartForm);
		}
		request.setAttribute("pie", arrayList1);
		request.setAttribute("pieList", arrayList2);
	}

	/**
	 * @param form
	 * @param request
	 * @return
	 */
	private void bar(ActionForm form, HttpServletRequest request) {
		ArrayList<DemosForm> arrayList1 = new ArrayList<DemosForm>();
		ArrayList<DemosForm> arrayList2 = new ArrayList<DemosForm>();
		DemosForm chartForm = new DemosForm();
		chartForm.setcaption("杭州");
		chartForm.setproperty("value1");
		arrayList1.add(chartForm);
		chartForm = new DemosForm();
		chartForm.setcaption("宁波");
		chartForm.setproperty("value2");
		arrayList1.add(chartForm);
		for (int i = 0; i < 10; i++) {
			chartForm = new DemosForm();
			chartForm.setxvalue("value" + String.valueOf(i));
			chartForm.setvalue1(String.valueOf(100.0D));
			chartForm.setvalue2(String.valueOf(200.0D));
			arrayList2.add(chartForm);
		}
		request.setAttribute("bar", arrayList1);
		request.setAttribute("barList", arrayList2);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward gridTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String str1 = request.getParameter("Handle");
		// String str2 = request.getParameter("code");

		ArrayList<DemosForm> arrayList1 = new ArrayList<DemosForm>();
		ArrayList<DemosForm> arrayList2 = new ArrayList<DemosForm>();
		DemosForm gridForm = new DemosForm();
		gridForm.setcaption("ffffff");
		gridForm.setproperty("value1");
		gridForm.setWidth("100");
		gridForm.setType("input");
		arrayList2.add(gridForm);
		gridForm = new DemosForm();
		gridForm.setcaption("ggggg");
		gridForm.setproperty("value2");
		gridForm.setWidth("100");
		gridForm.setType("input");
		arrayList2.add(gridForm);
		gridForm = new DemosForm();
		gridForm.setcaption("hhhh");
		gridForm.setproperty("value3");
		gridForm.setWidth("100");
		gridForm.setType("input");
		arrayList2.add(gridForm);
		request.setAttribute("list.caption", arrayList2);
		for (int i = 0; i < 205; i++) {
			DemosForm demosForm = new DemosForm();
			demosForm.setvalue1("12345678901234567890P");
			demosForm.setvalue2("b"
					+ String.valueOf((int) (Math.random() * 1000.0D)));
			demosForm.setvalue3("c"
					+ String.valueOf((int) (Math.random() * 1000.0D)));
			demosForm.setvalue4("d"
					+ String.valueOf((int) (Math.random() * 1000.0D)));
			demosForm.setvalue5(String.valueOf((int) (Math.random() * 3.0D)));
			demosForm.setvalue6(String.valueOf((int) (Math.random() * 4.0D)));
			demosForm
					.setvalue7(String.valueOf((int) (Math.random() * 1000.0D)));
			demosForm
					.setvalue8(String.valueOf((int) (Math.random() * 1000.0D)));
			arrayList1.add(demosForm);
		}
		request.setAttribute("myList", arrayList1);

		String sql = "SELECT menucode,menuname,operatesn,ismenu,isactive FROM G_MENU";
		List list = Executer.getInstance().ExecSeletSQL(sql).getResultSet();
		int n = list.size();
		if (n > 0) {
			System.out.println("--------N>0--------" + n);
			request.setAttribute("menuList", list);
		}

		return mapping.findForward("grid");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward gridReportTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			SqlReturn sqlReturn = new SqlReturn();
			Executer executer = Executer.getInstance();
			SqlParam params[] = new SqlParam[2];
			params[0] = new SqlParam();
			params[0].setAll(0, 12, "99");// 菜单编码
			params[1] = new SqlParam();
			params[1].setAll(0, 4, "99");// 菜单上级编码
			print(params[0].toString());
			print(params[1].toString());
			sqlReturn = executer.ExecStoreProcedure("Demo_ShowGridReport",
					params);
			print(sqlReturn.toString());
			if (sqlReturn.getResultCount() != 0) {
				List<BasicDynaBean> arraylist1 = sqlReturn.getResultSet(0);
				request.setAttribute("caption.list", arraylist1);
				arraylist1 = sqlReturn.getResultSet(1);
				request.setAttribute("result.list", arraylist1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}

		return mapping.findForward("gridReport");
	}
}
