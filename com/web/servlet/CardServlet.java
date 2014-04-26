package com.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.qmx.ioutils.ImageUtils;


public class CardServlet extends HttpServlet {

	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CardServlet.class);
	private static ServletRequest request = null;
	private static ServletResponse response = null;
	private static String method = "";

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		try {
			request = req;
			response = resp;
			method = request.getParameter("method");
			log.info("MethodFlag:>>>> " + method);

			if ("initShow".equals(method)) {
				initShow();
			} else if ("editShow".equals(method)) {
				editShow();
			} else if ("saveData".equals(method)) {
				saveData();
			} else if ("saveImgs".equals(method)) {
				saveImgs();
			}

			// HFBottomPDFFront //codeman_9060_1
			//
			// HFBottomPDFBack //codeman_9060_2

			// hidSign
			// [�绰]|[��ַ]|[����]|[��˾]|[����]|[ְ��]|[�ʱ�]|[����]|[��ַ]|[�ֻ�]
			// hidSignBack
			// [tel]|[address]|[fax]|[company]|[name]|[duty]|[zip]|[email]|[web]|[mobile]
			//
			// hidSignID
			// [�绰]|[��ַ]|[����]|[��˾]|[����]|[ְ��]|[�ʱ�]|[����]|[��ַ]|[�ֻ�]
			// hidSignBackID
			// [TEL]|[ADDRESS]|[FAX]|[COMPANY]|[NAME]|[DUTY]|[ZIP]|[EMAIL]|[WEB]|[MOBILE]
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/index.jsp").forward(
					request, response);
		}

	}

	private void initShow() throws Exception {

		String hidDesignInfo = "[��ַ]=XXX��XXX·XXX��|[��˾]=��˾���|[ְ��]=ְ��|[����]=abc@namex.cn|[����]=010-12345678|[�ֻ�]=13900000000|[����]=����|[�绰]=010-12345678|[��ַ]=www.namex.cn|[�ʱ�]=100000|[ADDRESS]=NO.X XX ROAD XXX CITY|[COMPANY]=CompanyName|[DUTY]=Duty|[EMAIL]=qmx@126.cn|[FAX]=010-12345678|[MOBILE]=13900000000|[NAME]=Name|[TEL]=010-12345678|[WEB]=www.qmx.cn|[ZIP]=100000";

		String HFBottomPDFFront = "codeman_9060_1";
		String HFFrontContent = "<img src='images/cutlines.gif' style='width: 1px;height:331px;position:absolute;  left:6px; top:0px; ' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 1px;height:331px;position:absolute;  left:538px; top:0px; ' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 544px;height:1px;position:absolute;  left:0px; top:6px;' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 544px;height:1px;position:absolute;  left:0px; top:325px;' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><div id='�绰:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 203px; HEIGHT: 0px; TEXT-ALIGN: left'>�绰:</div><div id='[�绰]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 202px; HEIGHT: 0px; TEXT-ALIGN: left'>[�绰]</div><div id='��ַ:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 188px; HEIGHT: 0px; TEXT-ALIGN: left'>��ַ:</div><div id='[��ַ]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 187px; HEIGHT: 0px; TEXT-ALIGN: left'>[��ַ]</div><div id='����:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 233px; HEIGHT: 0px; TEXT-ALIGN: left'>����:</div><div id='[����]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 232px; HEIGHT: 0px; TEXT-ALIGN: left'>[����]</div><div id='[��˾]' contentEditable='false' cmykcenter='#0,0,0,100#|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 20px; Z-INDEX: 2; LEFT: 226px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 161px; HEIGHT: 0px; TEXT-ALIGN: left'>[��˾]</div><div id='[����]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 22px; Z-INDEX: 2; LEFT: 226px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 75px; HEIGHT: 0px; TEXT-ALIGN: left'>[����]</div><div id='[ְ��]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 334px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 87px; HEIGHT: 0px; TEXT-ALIGN: left'>[ְ��]</div><div id='�ʱ�:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 248px; HEIGHT: 0px; TEXT-ALIGN: left'>�ʱ�:</div><div id='[�ʱ�]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 247px; HEIGHT: 0px; TEXT-ALIGN: left'>[�ʱ�]</div><div id='����:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 263px; HEIGHT: 0px; TEXT-ALIGN: left'>����:</div><div id='[����]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 261px; HEIGHT: 0px; TEXT-ALIGN: left'>[����]</div><div id='��ַ:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 278px; HEIGHT: 0px; TEXT-ALIGN: left'>��ַ:</div><div id='[��ַ]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 277px; HEIGHT: 0px; TEXT-ALIGN: left'>[��ַ]</div><div id='�ֻ�:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 228px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: ����; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 218px; HEIGHT: 0px; TEXT-ALIGN: left'>�ֻ�:</div><div id='[�ֻ�]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 262px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 217px; HEIGHT: 0px; TEXT-ALIGN: left'>[�ֻ�]</div>";// str.toString();
		String HFFrontImage = "/card/Template/TemplateFiles/codeman_9060_1.jpg";
		String HFFrontPic = "Template/PicFiles/NO_9060_20087258312pa_s.jpg";

		String HFBottomPDFBack = "codeman_9060_2";
		String HFBackContent = "<img src='images/cutlines.gif' style='width: 1px;height:331px;position:absolute;  left:6px; top:0px; ' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 1px;height:331px;position:absolute;  left:538px; top:0px; ' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 544px;height:1px;position:absolute;  left:0px; top:6px;' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><img src='images/cutlines.gif' style='width: 544px;height:1px;position:absolute;  left:0px; top:325px;' contenteditable='false' onclick='return false;' UNSELECTABLE='on' name='cutline' id='cutline' /><div id='Tel:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 206px; HEIGHT: 0px; TEXT-ALIGN: left'>Tel:</div><div id='[TEL]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 257px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 206px; HEIGHT: 0px; TEXT-ALIGN: left'>[TEL]</div><div id='Address:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 191px; HEIGHT: 0px; TEXT-ALIGN: left'>Address:</div><div id='[ADDRESS]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 280px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 190px; HEIGHT: 0px; TEXT-ALIGN: left'>[ADDRESS]</div><div id='Fax:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 236px; HEIGHT: 0px; TEXT-ALIGN: left'>Fax:</div><div id='[FAX]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 260px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 235px; HEIGHT: 0px; TEXT-ALIGN: left'>[FAX]</div><div id='[COMPANY]' contentEditable='false' cmykcenter='#0,0,0,100#|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 20px; Z-INDEX: 2; LEFT: 231px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 164px; HEIGHT: 0px; TEXT-ALIGN: left'>[COMPANY]</div><div id='[NAME]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 22px; Z-INDEX: 2; LEFT: 232px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 77px; HEIGHT: 0px; TEXT-ALIGN: left'>[NAME]</div><div id='[DUTY]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 363px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 90px; HEIGHT: 0px; TEXT-ALIGN: left'>[DUTY]</div><div id='Zip:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 251px; HEIGHT: 0px; TEXT-ALIGN: left'>Zip:</div><div id='[ZIP]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 257px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 250px; HEIGHT: 0px; TEXT-ALIGN: left'>[ZIP]</div><div id='E-mail:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 266px; HEIGHT: 0px; TEXT-ALIGN: left'>E-mail:</div><div id='[EMAIL]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 271px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 265px; HEIGHT: 0px; TEXT-ALIGN: left'>[EMAIL]</div><div id='Web:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 281px; HEIGHT: 0px; TEXT-ALIGN: left'>Web:</div><div id='[WEB]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 264px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 280px; HEIGHT: 0px; TEXT-ALIGN: left'>[WEB]</div><div id='Mobile:' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 233px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 221px; HEIGHT: 0px; TEXT-ALIGN: left'>Mobile:</div><div id='[MOBILE]' contentEditable='false' cmykcenter='|0|' style='BORDER-RIGHT: black 0px dotted; BORDER-TOP: black 0px dotted; FONT-WEIGHT: normal; FONT-SIZE: 10px; Z-INDEX: 2; LEFT: 273px;MARGIN-LEFT: auto; BORDER-LEFT: black 0px dotted; WIDTH: 0px; COLOR: #000000; BORDER-BOTTOM: black 0px dotted; FONT-STYLE: normal; FONT-FAMILY: Arial; WHITE-SPACE: nowrap; POSITION: absolute; TOP: 220px; HEIGHT: 0px; TEXT-ALIGN: left'>[MOBILE]</div>";
		String HFBackImage = "/card/Template/TemplateFiles/codeman_9060_2.jpg";
		String HFBackPic = "Template/PicFiles/NO_9060_20087258312pb_s.jpg";

		request.setAttribute("id", "9060");
		request.setAttribute("hidDesignInfo", hidDesignInfo);

		request.setAttribute("HFBottomPDFFront", HFBottomPDFFront);
		request.setAttribute("HFFrontContent", HFFrontContent);
		request.setAttribute("HFFrontImage", HFFrontImage);
		request.setAttribute("HFFrontPic", HFFrontPic);

		request.setAttribute("HFBottomPDFBack", HFBottomPDFBack);
		request.setAttribute("HFBackContent", HFBackContent);
		request.setAttribute("HFBackImage", HFBackImage);
		request.setAttribute("HFBackPic", HFBackPic);

		getServletContext().getRequestDispatcher("/index.jsp?type=1").forward(
				request, response);
	}

	private void editShow() throws Exception {

		String id = request.getParameter("id");

		String HFFrontContent = request.getParameter("HFFrontContent");
		String HFFrontImage = request.getParameter("HFFrontImage");
		System.out.println(HFFrontContent);
		System.out.println(HFFrontImage);

		String HFBackContent = request.getParameter("HFBackContent");
		String HFBackImage = request.getParameter("HFBackImage");
		System.out.println(HFBackContent);
		System.out.println(HFBackImage);

		request.setAttribute("id", id);
		request.setAttribute("HFFrontContent", HFFrontContent);
		request.setAttribute("HFFrontImage", HFFrontImage);
		request.setAttribute("HFBackContent", HFBackContent);
		request.setAttribute("HFBackImage", HFBackImage);

		getServletContext().getRequestDispatcher(
				"/DesignPic/DesignPaint_Standard.jsp").forward(request,
				response);
	}

	private void saveData() throws Exception {

		String srcImageFile = "E:/logs/img1.jpg";
		String destImageFile = "E:/logs/card.jpg";
		//
		String cardinfo = request.getParameter("cardinfo");
		String array[] = cardinfo.split(";");
		for (String string : array) {
			System.out.println(string);
			String str[] = string.split(",");

			String txt = str[0];
			String top = str[1].replaceAll("px", "");
			String left = str[2].replaceAll("px", "");
			String fontSize = str[3].replaceAll("px", "");// 80
			String fontFamily = str[4];
			Color color = Color.black;

			ImageUtils.pressText(txt, srcImageFile, destImageFile, fontFamily,
					Font.BOLD, color, Integer.parseInt(fontSize), Integer
							.parseInt(left), Integer.parseInt(top), 1.0f);

			srcImageFile = destImageFile;
		}

		System.out.println("��ʼ�������ͼ... ...");
		boolean b = ImageUtils.scale(destImageFile, "E:/logs/card_scale.jpg", 2);
		System.out.println("�ɹ��������ͼ:>>> " + b);

		String msg = "Upload Success!";
		System.out.println(msg);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(msg);
		// getServletContext().getRequestDispatcher("/index.jsp?type=3").forward(
		// request, response);
	}

	private void saveImgs() throws Exception {
		String msg = "Save Images Success!";
		try {
			String srcImgData = request.getParameter("srcimg");
			String srcImgDataX = request.getParameter("srcimgx");
//			 System.out.println("A::"+srcImgData);
//			 System.out.println("B::"+srcImgDataX);

			String srcImg = "E:/logs/card.jpg";
			String srcImgx = "E:/logs/card_m2.jpg";
			String tagImg = "E:/logs/cardx.jpg";
			String tagImgx = "E:/logs/cardx_m2.jpg";
			ImageUtils.StringToImage(srcImgData, srcImg);// ���ԴͼƬ
			ImageUtils.StringToImage(srcImgDataX, srcImgx);// ���ԴͼƬ(����ͼ)

			ImageUtils.scale(srcImg, tagImg, 1, true);// ԴͼƬ�Ŵ�һ��
			ImageUtils.scale(srcImgx, tagImgx, 1, false);// ԴͼƬ��Сһ��
		} catch (Exception e) {
			msg = "Save Image Failure!!!";
			e.printStackTrace();
		}
		// System.out.println(msg);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(msg);
	}

	private void test(){
		String imgStr = "";
		String srcImg = "E:/logs/img3.jpg";
		String srcImgx = "E:/logs/img3_m2.jpg";
		String tagImg = "E:/logs/img3x2.jpg";
		String tagImgx = "E:/logs/img3x2_m2.jpg";
		ImageUtils.StringToImage(imgStr,srcImg);//���ԴͼƬ
		ImageUtils.scale(srcImg,srcImgx,2,false);//ԴͼƬ��С����
		ImageUtils.scale(srcImg,"E:/logs/img3x1.jpg",1,true);//ԴͼƬ�Ŵ�һ��
		// ImageUtils.scale(srcImg,tagImg,2,true);//ԴͼƬ�Ŵ�����
		// ImageUtils.scale(tagImg,tagImgx,2,false);//Ŀ��ͼƬ��С����
	}

}
