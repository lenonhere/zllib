package com.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.BasicDynaBean;

import com.zl.base.core.db.Executer;
import com.zl.base.core.db.SqlReturn;
import com.zl.util.MethodFactory;

public class ImportExcel {

	public static void test2() {
		Workbook workbook = null;

		try {
			workbook = Workbook.getWorkbook(new File("d:\\sp1_9.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		Sheet sheet = workbook.getSheet(0);
		Cell cell = null;
		Cell headerCell = null;
		Cell colCell = null;
		Cell contractCell = null;
		Cell billCell = null;

		int columnCount = 6;
		int rowCount = sheet.getRows();

		/*
		ArrayList headerList = new ArrayList();
		for (int j = 1; j < columnCount; j++) {
			headerCell = sheet.getCell(j, 2);
			headerList.add(headerCell);
		}*/

		for (int i = 3; i < rowCount; i++) {
			for (int j = 1; j < columnCount; j++) {
				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				headerCell 	= sheet.getCell(j,2);	//�������
				colCell		= sheet.getCell(0,i);	//����
				contractCell = sheet.getCell(10,i);	//��ͬ��
				billCell = sheet.getCell(8,i);	//����Ʊ��
				cell = sheet.getCell(j, i);

				// Ҫ��ݵ�Ԫ������ͷֱ������?�����ʽ��������ݿ��ܻ᲻��ȷ
				if (cell.getType() == CellType.NUMBER) {
					System.out.print(((NumberCell) cell).getValue());
				} else if (cell.getType() == CellType.DATE) {
					System.out.print(((DateCell) cell).getDate());
				} else {
					System.out.print(cell.getContents());
				}

				// System.out.print(cell.getContents());
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		// �ر����������ڴ�й¶
		workbook.close();


	}

	public static void getSpBillLines(int k) throws Exception{
		Workbook workbook = null;
		Executer executeDb2 = Executer.getInstance();

		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\sp2006.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		Sheet sheet = workbook.getSheet(k);
		Cell cell = null;
		Cell headerCell = null;
		Cell colCell = null;
		Cell contractCell = null;
		Cell billCell = null;

		int columnCount = 5;

		if(k==0)
			columnCount = 4;

		int rowCount = sheet.getRows();

		/*
		ArrayList headerList = new ArrayList();
		for (int j = 1; j < columnCount; j++) {
			headerCell = sheet.getCell(j, 2);
			headerList.add(headerCell);
		}*/

		String date = "";
		String billCode = "";
		String tobaccoId = "-1";
		String tobaccoName = "";
		String dataValue = "";

		int billCount = 1;

		for (int i = 3; i < rowCount; i++) {
			for (int j = 1; j < columnCount; j++) {
				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				headerCell 	= sheet.getCell(j,2);	//�������
				colCell		= sheet.getCell(0,i);	//����
				billCell = sheet.getCell(columnCount+2,i);	//����Ʊ��
				cell = sheet.getCell(j, i);

				/*if(cell.isHidden())
					continue;*/

				if (colCell.getType() != CellType.DATE)
					continue;

				Date cvtDate     = ((DateCell) colCell).getDate();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				date = formatter.format(cvtDate);
				//date = date.replace("2006","2005");
				try{
					tobaccoName = MethodFactory.replace(headerCell.getContents(),"\n","");
				}catch(Exception ex){

				}
				billCode  = billCell.getContents();
				dataValue = cell.getContents();

				if("���̺���".equals(tobaccoName) || tobaccoName.indexOf("����")>0){
					tobaccoId = "1327";
				}else if("����Ʒ".equals(tobaccoName) || tobaccoName.indexOf("��Ʒ")>0){
					tobaccoId = "1328";
				}else if("�����촼��".equals(tobaccoName) || tobaccoName.indexOf("����")>0){
					tobaccoId = "1033";
				}else if("ܽ�ػƺ�".equals(tobaccoName) || tobaccoName.indexOf("�ƺ�")>0){
					tobaccoId = "926";
				}else if("ܽ�ؼ�Ʒ".equals(tobaccoName) || tobaccoName.indexOf("��Ʒ")>0){
					tobaccoId = "1030";
				}else if("ܽ�غ���".equals(tobaccoName) || tobaccoName.indexOf("����")>0){
					tobaccoId = "1031";
				}else if("ܽ�ػ�".equals(tobaccoName) || tobaccoName.indexOf("��")>0){
					tobaccoId = "1032";
				}else{
					tobaccoId = "0";
				}

				if( billCode!=null && !billCode.equals("") && dataValue!=null && !dataValue.equals("") && !dataValue.equals("0"))
				{
					//System.out.println(billCode+","+tobaccoId+","+dataValue+";");
					double dbValue = Double.parseDouble(dataValue);
					dbValue = dbValue/5;

					billCode = "2006"+billCode;
					String strSQL = "insert into N_SaleTicketDetail (BILLCODE, TOBACCOID, QUANTITY, WAREHOUSE) values ";
					strSQL  +=  "('"+billCode+"',"+tobaccoId+","+dbValue+",'C3') ";
					System.out.println(strSQL+";"+"--"+billCount);
					//executeDb2.ExecUpdateSQL(strSQL);
					billCount++;
				}

				// Ҫ��ݵ�Ԫ������ͷֱ������?�����ʽ��������ݿ��ܻ᲻��ȷ
				/*if (cell.getType() == CellType.NUMBER) {
					System.out.print(((NumberCell) cell).getValue());
				} else if (cell.getType() == CellType.DATE) {
					System.out.print(((DateCell) cell).getDate());
				} else {
					System.out.print(cell.getContents());
				}*/

				// System.out.print(cell.getContents());
				//System.out.print("\t");
			}
			//System.out.print("\n");
		}
		// �ر����������ڴ�й¶
		workbook.close();

	}



	public static void getSpBillInfo(int n) throws Exception{

		Executer executeDb2 = Executer.getInstance();

		Workbook workbook = null;
		int billCount = 1;
		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\sp2006.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		int sheetNum = workbook.getSheets().length;
		for(int k=n;k<n+1;k++){
		//for(int k=0;k<sheetNum;k++){
			Sheet sheet = workbook.getSheet(k);
			Cell cell = null;
			Cell headerCell = null;
			Cell colCell = null;
			Cell contractCell = null;
			Cell billCell = null;
			Cell companyCell = null;

			int columnCount = 5;

			if(k==0)
				columnCount = 4;

			int rowCount = sheet.getRows();


			String date = "";
			String contractCode = "";
			String billCode = "";
			String tobaccoId = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";

			for (int i = 3; i < rowCount; i++) {

					// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
					colCell		= sheet.getCell(0,i);	//����
					contractCell = sheet.getCell(columnCount+4,i);	//��ͬ��
					billCell = sheet.getCell(columnCount+2,i);	//����Ʊ��
					companyCell = sheet.getCell(columnCount+1,i);	//��˾

					if (colCell.getType() != CellType.DATE)
						continue;

					Date cvtDate     = ((DateCell) colCell).getDate();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					date = formatter.format(cvtDate);
					//date = date.replace("2006","2005");

					billCode  = billCell.getContents();
					contractCode  = contractCell.getContents();
					companyName = companyCell.getContents();
					companyShortName = convertCompanyJc(companyName);

					String sycompsystid = "0";
					if(companyShortName.equals("��ɽ")){
						sycompsystid = "187";
					}else if(companyShortName.equals("�˰�")){
						sycompsystid = "40";
					}else if(companyShortName.equals("����")){
						sycompsystid = "166";
					}else{
						String strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
						//System.out.println(strSQL);
						SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
						ArrayList compList = result.getResultSet();
						int compNum = compList.size();
						if(compNum==1){
							BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
							sycompsystid = MethodFactory.getThisString(detailBean.get("sycompsystid"));
						}else{

							System.out.println(companyName+":"+strSQL);
							continue;
						}
					}

					if(contractCode!=null && contractCode.indexOf("\n")>0){
						contractCode = contractCode.substring(0,contractCode.indexOf("\n"));
					}

					if( billCode!=null && !billCode.equals("")){
						billCode = "2006"+billCode;
						contractCode = billCode;
						//System.out.println(billCount+":"+billCode+","+contractCode+","+date+","+companyShortName+";");
						String strSQL = "insert into N_SaleTicket (BILLCODE,CONTRACTCODE,CUSTOMERID,SYCOMPANYID,BILLMEMO ";
						strSQL  +=  ",INPUTUSER,INPUTDATE,LADINGDATE,DELIVERYPLACE,FACTORYID,ProtocolYear,ProtocolHalf,BillType) values ";
						strSQL  +=  "('"+billCode+"','"+contractCode+"',"+sycompsystid+","+sycompsystid+",'"+companyName+"' ";
						strSQL  +=  " ,0,'"+date+"','"+date+"','"+companyName+"','3','2006','1','SELL') ";
						System.out.println(strSQL+";"+"--"+billCount);
						//executeDb2.ExecUpdateSQL(strSQL);
						billCount++;
					}

			}
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}

	public static void getProtocolInfo() throws Exception{
		Executer executeDb2 = Executer.getInstance();

		Workbook workbook = null;
		int billCount = 1;
		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\������ƽ07���ϰ���Э��ǩ�����.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		for(int k=0;k<1;k++){
			Sheet sheet = workbook.getSheet(k);

			Cell colCell = null;
			Cell protocolCell = null;
			Cell factoryCell = null;
			Cell companyCell = null;

			int rowCount = sheet.getRows();

			String protocolCode = "";
			String factoryCode = "";
			String companyCode = "";
			String tobaccoId = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";
			String behalfid = "0";

			for (int i = 1; i < rowCount; i++) {

				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				//colCell		= sheet.getCell(0,i);	//����
				protocolCell = sheet.getCell(0,i);	//��ͬ��
				factoryCell = sheet.getCell(1,i);	//�������
				companyCell = sheet.getCell(3,i);	//��˾���

				protocolCode  = protocolCell.getContents();
				factoryCode = factoryCell.getContents();
				companyCode = companyCell.getContents();

				if(companyCode==null || companyCode.length()==0)
					continue;

				if("12220301".equals(factoryCode))
					behalfid = "3";
				else if ("12640301".equals(factoryCode))
					behalfid = "6";

				companyName = sheet.getCell(4,i).getContents();

				String sycompsystid = "0";

				String strSQL = "select * from G_Sycompany where gj_code = '"+companyCode+"' and isactive='1' " ;
				//System.out.println(strSQL);
				SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
				ArrayList compList = result.getResultSet();
				int compNum = compList.size();
				if(compNum==1){
					BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
					sycompsystid = MethodFactory.getThisString(detailBean.get("gj_code"));
				}else{
					System.out.println(companyCode+":"+strSQL);
					continue;
				}

				if( protocolCode!=null && !protocolCode.equals("")){

					//System.out.println(billCount+":"+billCode+","+contractCode+","+date+","+companyShortName+";");

					strSQL = "insert into I_HalfProtocol(ProtocolCode,ProtocolYear,ProtocolHalf,SycompanyCode,SycompanyName,BehalfId,Memo) ";
					strSQL  +=  " values ('"+protocolCode+"','2007','0','"+sycompsystid+"','"+companyName+"',"+behalfid+",'') ";

					System.out.println(strSQL+";"+"--"+billCount);
					//executeDb2.ExecUpdateSQL(strSQL);

					billCount++;
				}

			}
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}
	public static void getProtocolLines() throws Exception{
		Workbook workbook = null;
		Executer executeDb2 = Executer.getInstance();
		int k=0;
		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\������ƽ07���ϰ���Э��ǩ�����.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		Sheet sheet = workbook.getSheet(k);
		Cell cell = null;
		Cell headerCell = null;
		Cell colCell = null;
		Cell factoryCell = null;
		Cell protocolCell = null;

		int columnCount = 11;

		int rowCount = sheet.getRows();

		String factoryCode = "";
		String protocolCode = "";
		String tobaccoId = "-1";
		String tobaccoName = "";
		String dataValue = "";

		int billCount = 1;

		for (int i = 1; i < rowCount; i++) {

			protocolCell = sheet.getCell(0,i);	//Э���
			factoryCell = sheet.getCell(1,i);	//�������

			protocolCode  = protocolCell.getContents();
			factoryCode   = factoryCell.getContents();

			for (int j = 5; j < columnCount; j++) {
				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				headerCell 	= sheet.getCell(j,0);	//�������

				cell = sheet.getCell(j, i);

				tobaccoName =MethodFactory.replace( headerCell.getContents(),"\n","");

				dataValue = cell.getContents();

				if("ܽ��(��)".equals(tobaccoName)){
					tobaccoId = "6901028194327";
				}else if("ܽ��(�ƺ�)".equals(tobaccoName)){
					tobaccoId = "6901028199469";
				}else if("ܽ��(��Ʒ)".equals(tobaccoName)){
					tobaccoId = "6901028194297";
				}else if("ƹ̳(����)".equals(tobaccoName)){
					tobaccoId = "6901028069441";
				}else if("ƹ̳(Ӳ��)".equals(tobaccoName)){
					tobaccoId = "6901028069236";
				}else if("ƹ̳(Ӳ��)".equals(tobaccoName) ){
					tobaccoId = "6901028069090";
				}else{
					tobaccoId = "0";
				}

				tobaccoId = tobaccoId+factoryCode+"00";

				if( protocolCode!=null && !protocolCode.equals("") && dataValue!=null && !dataValue.equals("") && !dataValue.equals("0") && factoryCode!=null && !factoryCode.equals(""))
				{
					//System.out.println(billCode+","+tobaccoId+","+dataValue+";");
					String strSQL = "insert into I_HalfProtocolDetail(ProtocolCode,TobaccoCode,Plate,quantity,Price) ";
					strSQL  +=  " values ('"+protocolCode+"','"+tobaccoId+"','"+tobaccoName+"',"+dataValue+",0) ";

					System.out.println(strSQL+";"+"--");
				}
			}
			//System.out.print("\n");
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}


	public static String convertCompanyJc(String fullname)
	{
		String sname = fullname;
		if(sname==null || sname.equals(""))
			return "";

		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("�ӱ�ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("�㽭ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("�Ĵ�ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("�����̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("����ʡ�̲�","");
		sname = sname.replaceAll("ɽ��ʡ�̲ݹ�˾","");
		sname = sname.replaceAll("�㶫�̲�","");

		if(!sname.equals("���ɹ��������̲ݹ�˾"))
			sname = sname.replaceAll("���ɹ��������̲ݹ�˾","");

		if(!sname.equals("����׳���������̲ݹ�˾"))
			sname = sname.replaceAll("����׳���������̲ݹ�˾","");

		if(!sname.equals("���Ļ����������̲ݹ�˾"))
			sname = sname.replaceAll("���Ļ����������̲ݹ�˾","");

		if(!sname.equals("�½�ά����������̲ݹ�˾"))
			sname = sname.replaceAll("�½�ά����������̲ݹ�˾","");

		if( sname.indexOf("���޹�˾")>0)
			sname = sname.replaceAll("ɽ��","");

		sname = sname.replaceAll("�й��̲��ܹ�˾","");


		if( sname.indexOf("�������۹�˾")>0 || sname.indexOf("ʡ���̲����۹�˾")>0|| sname.indexOf("������˾")>0 || sname.indexOf("�������˾")>0 ){
			sname = sname.replaceAll("����ʡ","����");
			//sname = sname.replaceAll("����ʡ","����");
			sname = sname.replaceAll("����ʡ","����");
		}else{
			sname = sname.replaceAll("����ʡ","");
			sname = sname.replaceAll("����ʡ","");
			sname = sname.replaceAll("����ʡ","");
		}

		if( sname.indexOf("�ֹ�˾")>0 || sname.indexOf("�й�˾")>0 || sname.indexOf("���̲ݹ�˾")>0 || sname.indexOf("���޹�˾")>0){
			sname = sname.replaceAll("�����̲�","");
			sname = sname.replaceAll("�����̲�","");
			sname = sname.replaceAll("�����̲�","");
			sname = sname.replaceAll("����׳��������","");
			sname = sname.replaceAll("����ʡ","");
			sname = sname.replaceAll("�½��̲ݱ���","");
		}else{
			sname = sname.replaceAll("�����̲�","����");
			sname = sname.replaceAll("�����̲�","����");
			sname = sname.replaceAll("�����̲�","����");
			sname = sname.replaceAll("����׳��������","����");
			sname = sname.replaceAll("�½�ά���������","�½�");
		}

		sname = sname.replaceAll("�������۹�˾","");
		sname = sname.replaceAll("���̲����۹�˾","");
		sname = sname.replaceAll("�������̲ݹ�˾","");
		sname = sname.replaceAll("���̲ݷֹ�˾","");
		sname = sname.replaceAll("���̲ݷֹ�˾","");
		sname = sname.replaceAll("�̲ݷֹ�˾","");
		sname = sname.replaceAll("������˾","");
		sname = sname.replaceAll("�������˾","");
		sname = sname.replaceAll("�ֹ�˾","");
		sname = sname.replaceAll("���̲ݹ�˾","");
		sname = sname.replaceAll("���̲ݹ�˾","");
		sname = sname.replaceAll("�ݹ�˾","");
		sname = sname.replaceAll("����˾","");
		sname = sname.replaceAll("�����޹�˾","");
		sname = sname.replaceAll("�������ι�˾","");
		sname = sname.replaceAll("���޹�˾","");
		sname = sname.replaceAll("��˾","");
		sname = sname.replaceAll("�̲�","");

		if( sname.indexOf("��")>0 && sname.indexOf("��")>0 )
			sname = sname.substring(0,sname.indexOf("��"));
		if( sname.indexOf("(")>0 && sname.indexOf(")")>0 )
			sname = sname.substring(0,sname.indexOf("("));

		return sname;
	}

	public static void getLlBillInfo() throws Exception{

		Executer executeDb2 = Executer.getInstance();

		Workbook workbook = null;
		int billCount = 1;
		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\2005�������������.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		int sheetNum = workbook.getSheets().length;
		for(int k=0;k<12;k++){
		//for(int k=0;k<sheetNum;k++){
			Sheet sheet = workbook.getSheet(k);
			Cell cell = null;
			Cell headerCell = null;
			Cell colCell = null;
			Cell contractCell = null;
			Cell billCell = null;
			Cell companyCell = null;

			int columnCount = 6;

			int rowCount = sheet.getRows();

			String date = "";
			String contractCode = "";
			String billCode = "";
			String tobaccoId = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";

			for (int i = 3; i < rowCount; i++) {

					// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
					companyCell = sheet.getCell(0,i);	//��˾
					companyName = companyCell.getContents();

					//if(companyName==null || companyName.indexOf("��")<0)
					//	break;

					date = "2005-"+ (k>=9?( String.valueOf(k+1) ):( "0"+String.valueOf(k+1) )) +"-15";

					companyShortName = companyName;
					companyShortName = companyShortName.replaceAll("��","");
					companyShortName = companyShortName.trim();

					String sycompsystid = "0";

					 if(companyShortName.equals("����")){
						sycompsystid = "460";
					}else if(companyShortName.equals("����")){
						sycompsystid = "406";
					}else if(companyShortName.equals("����")){
						sycompsystid = "437";
					}else if(companyShortName.equals("����")){
						sycompsystid = "428";
					}else if(companyShortName.equals("����")){
						sycompsystid = "492";
					}else if(companyShortName.equals("����")){
						sycompsystid = "467";
					}else if(companyShortName.equals("����")){
						sycompsystid = "423";
					}else if(companyShortName.equals("����")){
						sycompsystid = "474";
					}else if(companyShortName.equals("¦��")){
						sycompsystid = "447";
					}else if(companyShortName.equals("��ɳ")){
						sycompsystid = "414";
					}else if(companyShortName.equals("��̶")){
						sycompsystid = "419";
					}else if(companyShortName.equals("����") || companyShortName.equals("����")){
						sycompsystid = "486";
					}else{

						/*companyShortName = companyShortName.replaceAll("��","");
						String strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
						//System.out.println(strSQL);
						SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
						ArrayList compList = result.getResultSet();
						int compNum = compList.size();
						if(compNum==1){
							BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
							sycompsystid = MethodFactory.getThisString(detailBean.get("sycompsystid"));
						}else{

							System.out.println(companyName+":"+strSQL);
							continue;
						}*/
						continue;
					}

					billCode  = date.replaceAll("-","")+sycompsystid;
					contractCode = billCode;

					if(contractCode!=null && contractCode.indexOf("\n")>0){
						contractCode = contractCode.substring(0,contractCode.indexOf("\n"));
					}

					if( billCode!=null && !billCode.equals("")){
						//System.out.println(billCount+":"+billCode+","+contractCode+","+date+","+companyShortName+";");
						String strSQL = "insert into N_SaleTicket (BILLCODE,CONTRACTCODE,CUSTOMERID,SYCOMPANYID,BILLMEMO ";
						strSQL  +=  ",INPUTUSER,INPUTDATE,LADINGDATE,DELIVERYPLACE,FACTORYID,BillType) values ";
						strSQL  +=  "('"+billCode+"','"+contractCode+"',"+sycompsystid+","+sycompsystid+",'"+companyName+"' ";
						strSQL  +=  " ,0,'"+date+"','"+date+"','"+companyName+"','2','SELL') ";
						System.out.println(strSQL+";"+"--"+billCount);
						//executeDb2.ExecUpdateSQL(strSQL);
						billCount++;
					}

			}
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}

	public static void getLlBillLines(int k,int columnCount) throws Exception{
		Workbook workbook = null;
		Executer executeDb2 = Executer.getInstance();

		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\2005�������������.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		Sheet sheet = workbook.getSheet(k);
		Cell cell = null;
		Cell headerCell = null;
		Cell colCell = null;
		Cell companyCell = null;
		Cell billCell = null;

		//int columnCount = 3;
		int rowCount = sheet.getRows();

		String date = "";
		String billCode = "";
		String tobaccoId = "-1";
		String tobaccoName = "";
		String dataValue = "";
		String companyName = "";
		String companyShortName = "";

		int billCount = 1;

		for (int i = 3; i < rowCount; i++) {

			companyCell = sheet.getCell(0,i);	//��˾
			companyName = companyCell.getContents();

			//if(companyName==null || companyName.indexOf("��")<0)
			//	break;

			date = "2005-"+ (k>=9?( String.valueOf(k+1) ):( "0"+String.valueOf(k+1) )) +"-15";

			companyShortName = companyName;

			companyShortName = companyShortName.replaceAll("��","");
			companyShortName = companyShortName.trim();

			String sycompsystid = "0";
			/*if(companyShortName.equals("����")){
				sycompsystid = "460";
			}else if(companyShortName.equals("����")){
				sycompsystid = "406";
			}else if(companyShortName.equals("����")){
				sycompsystid = "437";
			}else if(companyShortName.equals("����")){
				sycompsystid = "428";
			}else if(companyShortName.equals("����")){
				sycompsystid = "492";
			}else if(companyShortName.equals("����")){
				sycompsystid = "467";
			}else if(companyShortName.equals("����")){
				sycompsystid = "423";
			}else if(companyShortName.equals("����")){
				sycompsystid = "474";
			}else if(companyShortName.equals("¦��")){
				sycompsystid = "447";
			}else if(companyShortName.equals("��ɳ")){
				sycompsystid = "414";
			}else if(companyShortName.equals("��̶")){
				sycompsystid = "419";
			}else if(companyShortName.equals("����") || companyShortName.equals("����")){
				sycompsystid = "486";
			}else{
			*/
			if(companyShortName.equals("����")){
				sycompsystid = "296";
			}else if(companyShortName.equals("�㶫")){
				sycompsystid = "259";
			}else{
				/*companyShortName = companyShortName.replaceAll("��","");
				String strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
				//System.out.println(strSQL);
				SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
				ArrayList compList = result.getResultSet();
				int compNum = compList.size();
				if(compNum==1){
					BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
					sycompsystid = MethodFactory.getThisString(detailBean.get("sycompsystid"));
				}else{

					System.out.println(companyName+":"+strSQL);
					continue;
				}*/
				continue;
			}

			billCode  = date.replaceAll("-","")+sycompsystid;

			for (int j = 1; j < columnCount; j++) {

				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				headerCell 	= sheet.getCell(j,2);	//�������

				cell = sheet.getCell(j, i);

				tobaccoName = MethodFactory.replace(headerCell.getContents(),"\n","");
				dataValue = cell.getContents();

				if("ܽ�أ��ƣ�".equals(tobaccoName)){
					tobaccoId = "931";
				}else if("�춹���ƣ�".equals(tobaccoName)){
					tobaccoId = "1324";
				}else if("ܽ�أ���Ʒ��".equals(tobaccoName)){
					tobaccoId = "1320";
				}else if("ܽ�أ���Ʒ��".equals(tobaccoName)){
					tobaccoId = "1330";
				}else if("ܽ�أ����?".equals(tobaccoName)){
					tobaccoId = "1203";
				}else if("�춹���죩".equals(tobaccoName)){
					tobaccoId = "1325";
				}else if("�춹���ף�".equals(tobaccoName)){
					tobaccoId = "1326";
				}else if("�춹���?".equals(tobaccoName)){
					tobaccoId = "1323";
				}else{
					tobaccoId = "0";
				}

				if( billCode!=null && !billCode.equals("") && dataValue!=null && !dataValue.equals("") && !dataValue.equals("0"))
				{
					//System.out.println(billCode+","+tobaccoId+","+dataValue+";");
					double dbValue = Double.parseDouble(dataValue);
					dbValue = dbValue/5;

					String strSQL = "insert into N_SaleTicketDetail (BILLCODE, TOBACCOID, QUANTITY, WAREHOUSE) values ";
					strSQL  +=  "('"+billCode+"',"+tobaccoId+","+dbValue+",'C2') ";
					System.out.println(strSQL+";"+"--"+billCount);
					//executeDb2.ExecUpdateSQL(strSQL);
					billCount++;
				}
			}

		}
		// �ر����������ڴ�й¶
		workbook.close();

	}

	//���Ҿ��̳��ᵥ����
	public static void getWzBillInfo(String filename,int num) throws Exception{

		Executer executeDb2 = Executer.getInstance();

		Workbook workbook = null;
		int billCount = 1;
		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\WuZhongData\\"+filename));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		int sheetNum = workbook.getSheets().length;
		for(int k=0;k<num;k++){
		//for(int k=0;k<sheetNum;k++){
			Sheet sheet = workbook.getSheet(k);
			String sheetName = sheet.getName();
			sheetName = sheetName.trim();
			Cell cell = null;
			Cell headerCell = null;
			Cell colCell = null;
			Cell contractCell = null;
			Cell billCell = null;
			Cell companyCell = null;

			int columnCount = 6;

			int rowCount = sheet.getRows();

			String date = "";
			String contractCode = "";
			String billCode = "";
			String tobaccoId = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";

			for (int i = 3; i < rowCount; i++) {

					// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
					companyCell = sheet.getCell(0,i);	//��˾
					companyName = companyCell.getContents();

					if(companyName==null || companyName.indexOf("��")<0)
						break;

					date = MethodFactory.replace(sheetName,"��","");
					date =  MethodFactory.replace(date,"��","-");
					if(date.charAt(1)=='-')
						date = "0"+date;
					if(date.length()==4)
						date = date.substring(0,3)+"0"+date.substring(3);

					date = "2006-"+date;

					companyShortName = convertCompanyJc(companyName);

					String sycompsystid = "0";
					if(companyShortName.equals("44")){
						sycompsystid = "460";
					}else{
						companyShortName = companyShortName.replaceAll("��","");
						String strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
						//System.out.println(strSQL);
						SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
						ArrayList compList = result.getResultSet();
						int compNum = compList.size();
						if(compNum==1){
							BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
							sycompsystid = MethodFactory.getThisString(detailBean.get("sycompsystid"));
						}else{

							System.out.println(companyName+":"+strSQL);
							continue;
						}
					}

					billCode  = "6"+date.replaceAll("-","")+sycompsystid;
					contractCode = billCode;

					if(contractCode!=null && contractCode.indexOf("\n")>0){
						contractCode = contractCode.substring(0,contractCode.indexOf("\n"));
					}

					if( billCode!=null && !billCode.equals("")){
						//System.out.println(billCount+":"+billCode+","+contractCode+","+date+","+companyShortName+";");
						String strSQL = "insert into N_SaleTicket (BILLCODE,CONTRACTCODE,CUSTOMERID,SYCOMPANYID,BILLMEMO ";
						strSQL  +=  ",INPUTUSER,INPUTDATE,LADINGDATE,DELIVERYPLACE,FACTORYID,BILLTYPE,ProtocolYear,ProtocolHalf) values ";
						strSQL  +=  "('"+billCode+"','"+contractCode+"',"+sycompsystid+","+sycompsystid+",'"+companyName+"' ";
						strSQL  +=  " ,0,'"+date+"','"+date+"','"+companyName+"','6','SELL','2006','1') ";
						System.out.println(strSQL+";"+"--"+billCount);
						//executeDb2.ExecUpdateSQL(strSQL);
						billCount++;
					}

			}
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}

	public static void getWzBillLines(String filename,int num) throws Exception{
		Workbook workbook = null;
		Executer executeDb2 = Executer.getInstance();

		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\WuZhongData\\"+filename));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		int sheetNum = workbook.getSheets().length;
		for(int k=0;k<num;k++){
			Sheet sheet = workbook.getSheet(k);
			String sheetName = sheet.getName();
			sheetName = sheetName.trim();
			Cell cell = null;
			Cell headerCell = null;
			Cell colCell = null;
			Cell companyCell = null;
			Cell billCell = null;

			int columnCount = 5;
			int rowCount = sheet.getRows();

			String date = "";
			String billCode = "";
			String tobaccoId = "-1";
			String tobaccoName = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";

			int billCount = 1;

			for (int i = 3; i < rowCount; i++) {

				companyCell = sheet.getCell(0,i);	//��˾
				companyName = companyCell.getContents();

				if(companyName==null || companyName.indexOf("��")<0)
					break;

				date =MethodFactory.replace( sheetName,"��","");
				date =MethodFactory.replace( date,"��","-");
				if(date.charAt(1)=='-')
					date = "0"+date;
				if(date.length()==4)
					date = date.substring(0,3)+"0"+date.substring(3);

				date = "2006-"+date;

				companyShortName = convertCompanyJc(companyName);

				String sycompsystid = "0";
				if(companyShortName.equals("44")){
					sycompsystid = "460";
				}else{
					companyShortName = companyShortName.replaceAll("��","");
					String strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
					//System.out.println(strSQL);
					SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
					ArrayList compList = result.getResultSet();
					int compNum = compList.size();
					if(compNum==1){
						BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
						sycompsystid = MethodFactory.getThisString(detailBean.get("sycompsystid"));
					}else{

						System.out.println(companyName+":"+strSQL);
						continue;
					}
				}

				billCode  = "6"+date.replaceAll("-","")+sycompsystid;

				for (int j = 1; j < columnCount; j++) {
					// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
					headerCell 	= sheet.getCell(j,2);	//�������

					cell = sheet.getCell(j, i);

					tobaccoName = MethodFactory.replace( headerCell.getContents(),"\n","");
					dataValue = cell.getContents();

					if("��Ʒ��ƹ̳".equals(tobaccoName)){
						tobaccoId = "1296";
					}else if("Ӳ��ƹ̳".equals(tobaccoName)){
						tobaccoId = "1297";
					}else if("Ӳ��ƹ̳".equals(tobaccoName)){
						tobaccoId = "1298";
					}else if("��ƹ̳".equals(tobaccoName)){
						tobaccoId = "-1";
					}else if("�춹���죩".equals(tobaccoName)){
						tobaccoId = "1325";
					}else if("�춹���ף�".equals(tobaccoName)){
						tobaccoId = "1326";
					}else if("�춹���?".equals(tobaccoName)){
						tobaccoId = "1323";
					}else{
						tobaccoId = "0";
					}

					if( billCode!=null && !billCode.equals("") && dataValue!=null && !dataValue.equals("") && !dataValue.equals("0"))
					{
						//System.out.println(billCode+","+tobaccoId+","+dataValue+";");
						double dbValue = Double.parseDouble(dataValue);
						dbValue = dbValue/5;

						String strSQL = "insert into N_SaleTicketDetail (BILLCODE, TOBACCOID, QUANTITY, WAREHOUSE) values ";
						strSQL  +=  "('"+billCode+"',"+tobaccoId+","+dbValue+",'C6') ";
						System.out.println(strSQL+";"+"--"+billCount);
						//executeDb2.ExecUpdateSQL(strSQL);
						billCount++;
					}
				}

			}
		}

		// �ر����������ڴ�й¶
		workbook.close();
	}

	public static void getProtocolData() throws Exception{
		Workbook workbook = null;
		Executer executeDb2 = Executer.getInstance();

		try {
			workbook = Workbook.getWorkbook(new File("E:\\��Ŀ\\��������\\��Ŀ�ĵ�\\ҵ������\\2007���ϰ���Э��.xls"));
		} catch (Exception e) {
			System.out.println("file to import not found!");
		}

		int sheetNum = workbook.getSheets().length;
		for(int k=0;k<1;k++){
		//for(int k=0;k<sheetNum;k++){
			Sheet sheet = workbook.getSheet(k);
			String sheetName = sheet.getName();
			sheetName = sheetName.trim();
			Cell cell = null;
			Cell headerCell = null;
			Cell colCell = null;
			Cell protocolCell = null;
			Cell billCell = null;
			Cell companyCell = null;
			Cell tobaCell = null;
			Cell valCell = null;
			Cell factCell = null;
			Cell signCell = null;

			int columnCount = 6;

			int rowCount = sheet.getRows();

			String date = "";
			String protocolCode = "";
			String billCode = "";
			String tobaccoId = "";
			String tobaccoName = "";
			String dataValue = "";
			String companyName = "";
			String companyShortName = "";
			String factName = "";
			String signDate = "";

			String oldCode = null;
			int billCount = 0;

			String strSQL = "";
			String behalfid = "0";

			for (int i = 1; i < rowCount; i++) {

				// ע�⣬��������������һ���Ǳ�ʾ�еģ��ڶ��ű�ʾ��
				protocolCell = sheet.getCell(0,i);	//Э���
				factCell = sheet.getCell(1,i);		//�̳�
				companyCell = sheet.getCell(2,i);	//��˾
				tobaCell = sheet.getCell(4,i);		//����
				valCell = sheet.getCell(6,i);		//����
				signCell = sheet.getCell(3,i);		//Э���������

				//System.out.println(signCell.getType().toString());
				if (signCell.getType() != CellType.DATE)
					continue;

				protocolCode = protocolCell.getContents();
				factName = factCell.getContents();
				companyName = companyCell.getContents();
				tobaccoName = tobaCell.getContents();
				dataValue = valCell.getContents();
				signDate = signCell.getContents();

				//if(signDate==null && signDate.equals(""))
				//	continue;

				Date cvtDate     = ((DateCell) signCell).getDate();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				signDate = formatter.format(cvtDate);


				if(factName.equals("�������̹�ҵ��˾"))
					behalfid = "0";	//�������̹�ҵ��˾���¾��̳�
				else if(factName.equals("�������̹�ҵ��˾��ƽ���̳�"))
					behalfid = "3";	//���¾��̳���ƽ���̷ֳ�
				else if(factName.equals("�������̹�ҵ��˾���Ҿ��̳�"))
					behalfid = "6";	//��ɳ���̳����Ҿ��̷ֳ�

				if(companyName==null )
					break;

				companyShortName = convertCompanyJc(companyName);

				String sycompsystid = "0";
				if(companyName.equals("���ɹ��������̲ݹ�˾�˰��ֹ�˾")){
					//sycompsystid = "40";
					sycompsystid = "11152201";
				}else if(companyName.equals("�Ϻ��̲ݼ����Ϻ��̲�ó������")){
					//sycompsystid = "70";
					sycompsystid = "99310101";
				}else if(companyName.equals("�������̲ݹ�˾")){
					//sycompsystid = "177";
					sycompsystid = "11210201";
				}else if(companyName.equals("����ʡ�̲ݹ�˾���ַֹ�˾")){
					//sycompsystid = "166";
					sycompsystid = "11220201";
				}else if(companyName.equals("�й��̲��ܹ�˾�����й�˾���۷ֹ�˾")){
					//sycompsystid = "359";
					sycompsystid = "22500001";
				}else if(companyName.equals("�й��̲��ܹ�˾����ʡ��ɽ�ֹ�˾")){
					//sycompsystid = "187";
					sycompsystid = "11210301";
				}else if(companyName.equals("�㶫�̲ݽ��������޹�˾")){
					//sycompsystid = "245";
					sycompsystid = "11440701";
				}else if(companyName.equals("�㶫�̲���ͷ�κ����޹�˾")){
					//sycompsystid = "249";
					sycompsystid = "11440503";
				}else if(companyName.equals("����ʡ�̲ݹ�˾���ݹ�˾")){
					//sycompsystid = "787";
					sycompsystid = "11620101";
				}else{
					companyShortName = companyShortName.replaceAll("��","");
					strSQL = "select * from G_Sycompany where sycompalias like '%"+companyShortName+"%' and isactive='1' " ;
					//System.out.println(strSQL);
					SqlReturn result =executeDb2.ExecSeletSQL(strSQL);
					ArrayList compList = result.getResultSet();
					int compNum = compList.size();
					if(compNum==1){
						BasicDynaBean detailBean = (BasicDynaBean) compList.get(0);
						sycompsystid = MethodFactory.getThisString(detailBean.get("gj_code"));
					}else {
						if (!protocolCode.equals(oldCode)){
							System.out.println(companyName+":"+strSQL);
							//continue;
						}
					}
				}

				if(tobaccoName.equals("��ɳ(������)")){
					tobaccoId = "69010281969491243010100";
				}else if(tobaccoName.equals("��ɳ(��)")){
					tobaccoId = "69010281923231243010100";
				}else if(tobaccoName.equals("ܽ����(����)")){
					tobaccoId = "69010281943961243070100";
				}else if(tobaccoName.equals("��ɳ(������)")){
					tobaccoId = "69010281915551243010100";
				}else{
					strSQL = "select * from G_Tobacco where tobaname = '"+tobaccoName+"' and isactive='1' " ;
					//System.out.println(strSQL);
					SqlReturn tobaResult =executeDb2.ExecSeletSQL(strSQL);
					ArrayList tobaList = tobaResult.getResultSet();
					int compNum = tobaList.size();
					if(compNum==1){
						BasicDynaBean detailBean = (BasicDynaBean) tobaList.get(0);
						tobaccoId = MethodFactory.getThisString(detailBean.get("gj_code"));
					}else{
						System.out.println(tobaccoName+":"+strSQL);
						continue;
					}
				}

				if( protocolCode!=null && !protocolCode.equals("") ){
					if(!protocolCode.equals(oldCode)){
						//System.out.println(billCount+":"+billCode+","+contractCode+","+date+","+companyShortName+";");
						strSQL = "insert into I_HalfProtocol(ProtocolCode,ProtocolYear,ProtocolHalf,SycompanyCode,SycompanyName,BehalfId,Memo) ";
						strSQL  +=  " values ('"+protocolCode+"','2007','0','"+sycompsystid+"','"+companyName+"',"+behalfid+",'') ";

						System.out.println(strSQL+";"+"--"+billCount);
						executeDb2.ExecUpdateSQL(strSQL);
						billCount++;
					}

					strSQL = "insert into I_HalfProtocolDetail(ProtocolCode,TobaccoCode,Plate,quantity,Price) ";
					strSQL  +=  " values ('"+protocolCode+"','"+tobaccoId+"','"+tobaccoName+"',"+dataValue+",0) ";

					System.out.println(strSQL+";"+"--");
					//executeDb2.ExecUpdateSQL(strSQL);
				}

				oldCode = protocolCode ;

			}
		}
		// �ر����������ڴ�й¶
		workbook.close();
	}

	public static void main(String[] args) throws Exception {

		// new BaseInfoManager().UpdateArrivalDest() ;
		// new BaseInfoManager().UpdateCompanyInfo() ;
		// new BaseInfoManager().UpdateHalfProtocal() ;
		// new BaseInfoManager().UpdateTaxInfoFromOds();
		// new BaseInfoManager().CompareSaleTicket();

		//ImportExcel.test2();
		//ImportExcel.getSpBillInfo(10);
		//ImportExcel.getSpBillLines(10);

		/*ImportExcel.getLlBillInfo();

		ImportExcel.getLlBillLines(0,5);
		ImportExcel.getLlBillLines(1,5);
		ImportExcel.getLlBillLines(2,5);
		ImportExcel.getLlBillLines(3,6);
		ImportExcel.getLlBillLines(4,6);
		ImportExcel.getLlBillLines(5,6);

		ImportExcel.getLlBillLines(6,8);
		ImportExcel.getLlBillLines(7,8);
		ImportExcel.getLlBillLines(8,8);
		ImportExcel.getLlBillLines(9,8);
		ImportExcel.getLlBillLines(10,8);
		ImportExcel.getLlBillLines(11,8);*/

		//ImportExcel.getWzBillInfo("���ҷֳ�Ͷ�ŵ����ձ���0611.xls",20);
		//ImportExcel.getWzBillLines("���ҷֳ�Ͷ�ŵ����ձ���0611.xls",20);

		//ImportExcel.getProtocolData();

		ImportExcel.getProtocolInfo();
		ImportExcel.getProtocolLines();
	}
}
