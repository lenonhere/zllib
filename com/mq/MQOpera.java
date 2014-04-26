package com.mq;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.common.SystemConfig;
import com.common.log.LogFactory;
import com.ws.basedata.basicdata;
import com.zl.base.core.db.CallHelper;

public class MQOpera {
	private static Log logger = LogFactory.getLog();

	public static void main(String args[]) {
		MQOpera mqOpera = new MQOpera();
		String x = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BD_COSTSUBJ><HEAD><MSGCODE>JK_NSAL_NC_BDCOSTSUBJ_20100203163034936088</MSGCODE><MSGID>JK_NSAL_NC_BDCOSTSUBJ</MSGID><MSGNAME>营销科目收支项目</MSGNAME><SOURCESYS>NSAL</SOURCESYS><TARGETSYS>NC</TARGETSYS><CREATETIME>2010-02-03 16:30:34</CREATETIME></HEAD><DATA><ROW ACTION='INSERT'><PK_CORP>20420001</PK_CORP><COSTCODE>ZL-1</COSTCODE><COSTNAME>收支项目</COSTNAME><PK_PARENT></PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1000</COSTCODE><COSTNAME>差旅费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1001</COSTCODE><COSTNAME>会务费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1002</COSTCODE><COSTNAME>招待费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1003</COSTCODE><COSTNAME>礼品费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1004</COSTCODE><COSTNAME>办公费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1005</COSTCODE><COSTNAME>培训费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1006</COSTCODE><COSTNAME>租赁费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1007</COSTCODE><COSTNAME>通讯费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1008</COSTCODE><COSTNAME>车辆使用费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1009</COSTCODE><COSTNAME>市场交易费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1010</COSTCODE><COSTNAME>劳务费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1011</COSTCODE><COSTNAME>打假费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1012</COSTCODE><COSTNAME>职工福利费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1015</COSTCODE><COSTNAME>运输费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1017</COSTCODE><COSTNAME>保管费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1018</COSTCODE><COSTNAME>装卸费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1019</COSTCODE><COSTNAME>媒体广告</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1020</COSTCODE><COSTNAME>户外广告</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1021</COSTCODE><COSTNAME>宣传活动</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1023</COSTCODE><COSTNAME>评吸烟</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1024</COSTCODE><COSTNAME>促销品</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL1025</COSTCODE><COSTNAME>印刷品</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2000</COSTCODE><COSTNAME>交通费</COSTNAME><PK_PARENT>ZL1000</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2001</COSTCODE><COSTNAME>住宿补助</COSTNAME><PK_PARENT>ZL1000</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2003</COSTCODE><COSTNAME>总部会务</COSTNAME><PK_PARENT>ZL1001</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2005</COSTCODE><COSTNAME>日常招待费</COSTNAME><PK_PARENT>ZL1002</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2007</COSTCODE><COSTNAME>日常礼品费</COSTNAME><PK_PARENT>ZL1003</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2009</COSTCODE><COSTNAME>办公庶务费</COSTNAME><PK_PARENT>ZL1004</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2010</COSTCODE><COSTNAME>信息化建设</COSTNAME><PK_PARENT>ZL1004</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2011</COSTCODE><COSTNAME>办公租赁费</COSTNAME><PK_PARENT>ZL1006</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2013</COSTCODE><COSTNAME>车辆租赁费</COSTNAME><PK_PARENT>ZL1006</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2014</COSTCODE><COSTNAME>固定电话</COSTNAME><PK_PARENT>ZL1007</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2015</COSTCODE><COSTNAME>移动电话</COSTNAME><PK_PARENT>ZL1007</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2016</COSTCODE><COSTNAME>路桥油费</COSTNAME><PK_PARENT>ZL1008</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2017</COSTCODE><COSTNAME>修理费</COSTNAME><PK_PARENT>ZL1008</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2020</COSTCODE><COSTNAME>车辆规费</COSTNAME><PK_PARENT>ZL1008</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2022</COSTCODE><COSTNAME>职工工作餐</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2027</COSTCODE><COSTNAME>卷烟销售运费</COSTNAME><PK_PARENT>ZL1015</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2029</COSTCODE><COSTNAME>宣传用品运费</COSTNAME><PK_PARENT>ZL1015</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2030</COSTCODE><COSTNAME>其他运输费</COSTNAME><PK_PARENT>ZL1015</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2031</COSTCODE><COSTNAME>电视</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2032</COSTCODE><COSTNAME>报刊杂志</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2033</COSTCODE><COSTNAME>广播</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2034</COSTCODE><COSTNAME>网络广告</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2035</COSTCODE><COSTNAME>广告片制作</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2036</COSTCODE><COSTNAME>其他媒体</COSTNAME><PK_PARENT>ZL1019</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2039</COSTCODE><COSTNAME>总部促销品</COSTNAME><PK_PARENT>ZL1024</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2040</COSTCODE><COSTNAME>总部印刷品</COSTNAME><PK_PARENT>ZL1025</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2067</COSTCODE><COSTNAME>区域会务</COSTNAME><PK_PARENT>ZL1001</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2068</COSTCODE><COSTNAME>酒店住宿</COSTNAME><PK_PARENT>ZL1000</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2069</COSTCODE><COSTNAME>其他会务</COSTNAME><PK_PARENT>ZL1001</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2072</COSTCODE><COSTNAME>终端宣传</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2073</COSTCODE><COSTNAME>文化宣传</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2074</COSTCODE><COSTNAME>车身广告</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2075</COSTCODE><COSTNAME>管理咨询</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2076</COSTCODE><COSTNAME>其它活动</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2085</COSTCODE><COSTNAME>户外广告</COSTNAME><PK_PARENT>ZL1020</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2088</COSTCODE><COSTNAME>各厂促销品</COSTNAME><PK_PARENT>ZL1024</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2089</COSTCODE><COSTNAME>各厂印刷品</COSTNAME><PK_PARENT>ZL1025</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2111</COSTCODE><COSTNAME>宣传促销活动</COSTNAME><PK_PARENT>ZL1021</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2131</COSTCODE><COSTNAME>总部培训</COSTNAME><PK_PARENT>ZL1005</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2132</COSTCODE><COSTNAME>区域培训</COSTNAME><PK_PARENT>ZL1005</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2151</COSTCODE><COSTNAME>样品烟</COSTNAME><PK_PARENT>ZL1023</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2152</COSTCODE><COSTNAME>评吸烟分摊</COSTNAME><PK_PARENT>ZL1023</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2171</COSTCODE><COSTNAME>物业管理费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2173</COSTCODE><COSTNAME>水电费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW><ROW ACTION=\"INSERT\"><PK_CORP>20420001</PK_CORP><COSTCODE>ZL2174</COSTCODE><COSTNAME>办公修理费</COSTNAME><PK_PARENT>ZL-1</PK_PARENT><REMARK></REMARK><INCOMEFLAG>N</INCOMEFLAG><IOFLAG>N</IOFLAG><OUTFLAG>Y</OUTFLAG></ROW></DATA></BD_COSTSUBJ>";
		mqOpera.sendMQtest(x);
		// mqOpera.T_RETAILER_INFO();
		// mqOpera.RETAILER_WEEK_TRADE();
		// mqOpera.RETAILER_MONTH_TRADE();
		// mqOpera.PURSALSTK_HB();
		// mqOpera.T_TOBACCO_BARCODE();
		// mqOpera.BUYPLAN();
		// mqOpera.RETAILBILLMERGE();
		// mqOpera.STORAGEWARN();
		// mqOpera.MARKETINFO();
		// mqOpera.PurchConsultHalfYear();
		// mqOpera.MONSALE();
		// mqOpera.PURCHCONSULT();
		// mqOpera.CONTEXEC();
		// mqOpera.WEEKREQSUPPLY();
		// mqOpera.PURSALSTK();
		// mqOpera.PURCHCONSULTCONFIRMHALFYEAR();
		// mqOpera.BPURCHCONSULTHALFYEAR();
		// mqOpera.CONTVOUCHER();
		// mqOpera.REQUESTCO();
		// mqOpera.REQUESTCOCONFIRM();
		// mqOpera.WEEKREQCONFIRM();
		// mqOpera.WEEKREQ();
		// mqOpera.PURCHCONSULTCONFIRM();
		// mqOpera.BPURCHCONSULT();
	}

	public MQOpera() {
	}

	public static String saveMQ(String inXml) {
		String MSGID = basicdata.getHeadInfo(inXml, "MSGID");
		String MSGCODE = basicdata.getHeadInfo(inXml, "MSGCODE");
		String MSGNAME = basicdata.getHeadInfo(inXml, "MSGNAME");
		String SOURCESYS = basicdata.getHeadInfo(inXml, "SOURCESYS");
		String TARGETSYS = basicdata.getHeadInfo(inXml, "TARGETSYS");
		String ACTION = basicdata.getHeadInfo(inXml, "ACTION");
		String CREATETIME = basicdata.getHeadInfo(inXml, "CREATETIME");
		CallHelper helper = new CallHelper("C_LOG_MQ");
		helper.setParam("MSGID", MSGID);
		helper.setParam("MSGCODE", MSGCODE);
		helper.setParam("MSGNAME", MSGNAME);
		helper.setParam("SOURCESYS", SOURCESYS);
		helper.setParam("TARGETSYS", TARGETSYS);
		helper.setParam("ACTION", ACTION);
		helper.setParam("CREATETIME", CREATETIME);
		helper.setParam("COM_CODE", "");
		if (TARGETSYS.toUpperCase().equals("NSAL")) {
			helper.setParam("INSERTROWS", "-1");
			helper.setParam("rows", "-1");
		} else if (SOURCESYS.toUpperCase().equals("NSAL")) {
			helper.setParam("INSERTROWS", "-999");
			helper.setParam("rows", "-999");
		}
		helper.setParam("REMARK", "");
		helper.setParam("CONTENT", inXml);
		helper.execute();
		return (String) helper.getOutput("errCode");
	}

	public static boolean sendMQ(String str, String msgid) {
		boolean status = false;
		MQQueue mqQueue = null;
		MQQueueManager qMgr = null;
		try {
			System.out.println("send MQ.................");
			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.CCSID = MQbase._CCSID;
			MQEnvironment.port = MQbase._port;
			MQEnvironment.hostname = MQbase._hostname;
			MQEnvironment.channel = MQbase._channel;
			qMgr = new MQQueueManager(MQbase._qMgr);
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			String mqtype = SystemConfig.getString(msgid);
			if (mqtype.equals("yw")) {
				mqQueue = qMgr.accessQueue(MQbase.queue_remote, openOptions);
			} else if (mqtype.equals("jc")) {
				mqQueue = qMgr.accessQueue(MQbase.queue_remote_jc, openOptions);
			}

			MQMessage mqMsg = new MQMessage();
			mqMsg.correlationId = MQC.MQCI_NONE;
			mqMsg.messageId = MQC.MQMI_NONE;
			mqMsg.characterSet = MQbase._CCSID;
			mqMsg.writeString(str);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			mqQueue.put(mqMsg, pmo);
			saveMQ(new String(str));
			status = true;
		} catch (MQException e) {
			if (!(e.reasonCode == 2033)) {
				e.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ e.completionCode + " Reasoncode " + e.reasonCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("An error occurred whilst writing to the message buffer: "
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error occurred whilst importing the base data:"
					+ e);
		} finally {
			try {
				if (mqQueue != null) {
					mqQueue.close();
					mqQueue = null;
				}
				if (qMgr != null) {
					qMgr.close();
					qMgr.disconnect();
					qMgr = null;
				}
				System.out
						.println(" ----------------- close MQ ---------------- ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public static boolean sendMQtest(String str) {
		boolean status = false;
		MQQueue mqQueue = null;
		MQQueueManager qMgr = null;
		try {
			System.out.println("send MQ.................");
			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.CCSID = MQbase._CCSID;
			MQEnvironment.port = MQbase._port;
			MQEnvironment.hostname = MQbase._hostname;
			MQEnvironment.channel = MQbase._channel;
			qMgr = new MQQueueManager(MQbase._qMgr);
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;
			mqQueue = qMgr.accessQueue("test", openOptions);

			MQMessage mqMsg = new MQMessage();
			mqMsg.correlationId = MQC.MQCI_NONE;
			mqMsg.messageId = MQC.MQMI_NONE;
			mqMsg.characterSet = MQbase._CCSID;
			mqMsg.writeString(str);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			mqQueue.put(mqMsg, pmo);
			saveMQ(new String(str));
			status = true;
		} catch (MQException e) {
			if (!(e.reasonCode == 2033)) {
				e.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ e.completionCode + " Reasoncode " + e.reasonCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("An error occurred whilst writing to the message buffer: "
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error occurred whilst importing the base data:"
					+ e);
		} finally {
			try {
				if (mqQueue != null) {
					mqQueue.close();
					mqQueue = null;
				}
				if (qMgr != null) {
					qMgr.close();
					qMgr.disconnect();
					qMgr = null;
				}
				System.out
						.println(" ----------------- close MQ ---------------- ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public static boolean sendMQ(String str, String msgid, String oid) {
		boolean status = false;
		MQQueue mqQueue = null;
		MQQueueManager qMgr = null;
		try {
			System.out.println("send MQ.................");
			MQEnvironment.addConnectionPoolToken();
			MQEnvironment.CCSID = MQbase._CCSID;
			MQEnvironment.port = MQbase._port;
			MQEnvironment.hostname = MQbase._hostname;
			MQEnvironment.channel = MQbase._channel;
			qMgr = new MQQueueManager(MQbase._qMgr);
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING;// MQC.MQOO_INPUT_AS_Q_DEF
																			// |
																			// MQC.MQOO_OUTPUT;
			String mqtype = SystemConfig.getString(msgid);
			if (mqtype.equals("yw")) {
				mqQueue = qMgr.accessQueue(MQbase.queue_remote, openOptions);
			} else if (mqtype.equals("jc")) {
				mqQueue = qMgr.accessQueue(MQbase.queue_remote_jc, openOptions);
			}

			MQMessage mqMsg = new MQMessage();
			mqMsg.correlationId = MQC.MQCI_NONE;
			mqMsg.messageId = MQC.MQMI_NONE;
			mqMsg.characterSet = MQbase._CCSID;
			mqMsg.writeString(str);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			mqQueue.put(mqMsg, pmo);
			CallHelper helper = new CallHelper("updateLOG_MQ");
			helper.setParam("OID", oid);
			helper.setParam("ROWS", "-999");
			helper.setParam("INSERTROWS", "-999");
			helper.execute();
			status = true;
		} catch (MQException e) {
			if (!(e.reasonCode == 2033)) {
				e.printStackTrace();
				logger.error("A WebSphereMQ error occurred:Completioncode "
						+ e.completionCode + " Reasoncode " + e.reasonCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("An error occurred whilst writing to the message buffer: "
					+ e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error occurred whilst importing the base data:"
					+ e);
		} finally {
			try {
				if (mqQueue != null) {
					mqQueue.close();
					mqQueue = null;
				}
				if (qMgr != null) {
					qMgr.close();
					qMgr.disconnect();
					qMgr = null;
				}
				System.out
						.println(" ----------------- close MQ ---------------- ");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public static void writeMQ(String str) {
		try {
			MQQueue mqQueue = MQbase.getMQQueue("put");

			MQMessage hello_world = new MQMessage();
			hello_world.characterSet = MQbase._CCSID;
			hello_world.writeString(str);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			mqQueue.put(hello_world, pmo);
		} catch (MQException ex) {
			System.out.println("A WebSphereMQ error occurred:Completioncode"
					+ ex.completionCode + "Reasoncode" + ex.reasonCode);
		} catch (java.io.IOException ex) {
			System.out
					.println("An error occurred whilst writing to the message buffer:"
							+ ex);
		}
	}

	public static String makeXml(String rootName, List resultList,
			String[] resultNames, Hashtable hData, String[] headNames) {
		DocumentBuilder db = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println(e);
			return "error";
		}
		Document doc = null;
		doc = db.newDocument();
		Element eRoot = doc.createElement(rootName);
		doc.appendChild(eRoot);

		Text nodeText = null;
		Element headData = doc.createElement("HEAD");
		eRoot.appendChild(headData);

		for (int i = 0; i < headNames.length; i++) {
			Element codeData = doc.createElement(headNames[i]);
			headData.appendChild(codeData);
			nodeText = doc.createTextNode((String) hData.get(headNames[i]));
			codeData.appendChild(nodeText);
		}

		Element eData = doc.createElement("DATA");
		eRoot.appendChild(eData);
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				HashMap bean = (HashMap) resultList.get(i);

				Element rowData = doc.createElement("ROW");
				if (bean.get("ACTION") != null) {
					rowData.setAttribute("ACTION", (String) bean.get("ACTION"));
				}
				eData.appendChild(rowData);

				for (int j = 0; j < resultNames.length; j++) {
					if (!resultNames[j].equals("ACTION")) {
						Element element = doc.createElement(resultNames[j]);
						rowData.appendChild(element);
						Text tnode = doc.createTextNode(String.valueOf(
								bean.get(resultNames[j])).replace("null", ""));
						element.appendChild(tnode);
					}
				}
			}
		}
		StringWriter sWriter = new StringWriter();
		try {
			TransformerFactory transfactory = TransformerFactory.newInstance();
			Transformer trans = transfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = // new StreamResult(
			// new FileOutputStream("c:\\ssprocessed.xml"));
			new StreamResult(sWriter);
			Properties properties = trans.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "UTF-8");
			trans.setOutputProperties(properties);

			trans.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sWriter.getBuffer().toString();
	}

	public static void send_test() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "DEMAND_ID", "MONTH_BPC" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "11500");
			bean.put("COM_CODE", "11320301");
			bean.put("DEMAND_ID", "0");
			bean.put("MONTH_BPC", "200904");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "send_test");
		hData.put("MSGID", "send_test");
		hData.put("MSGNAME", "send_test");
		hData.put("SOURCESYS", "NSAL");
		hData.put("TARGETSYS", "DATACENT");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("BPURCHCONSULT", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void BPURCHCONSULT() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "DEMAND_ID", "MONTH_BPC" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "11500");
			bean.put("COM_CODE", "11320301");
			bean.put("DEMAND_ID", "0");
			bean.put("MONTH_BPC", "200904");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "BPURCHCONSULT");
		hData.put("MSGNAME", "商业公司月度采购衔接需求信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("BPURCHCONSULT", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PURCHCONSULTCONFIRM() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "IND_CODE", "MONTH_PCC" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "2500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("MONTH_PCC", "200904");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "PURCHCONSULTCONFIRM");
		hData.put("MSGNAME", "商业公司月度采购衔接确认信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURCHCONSULTCONFIRM", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void WEEKREQ() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "IND_CODE", "BEG_DATE",
				"END_DATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "1500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("BEG_DATE", "2009-04-01");
			bean.put("END_DATE", "2009-04-02");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "WEEKREQ");
		hData.put("MSGNAME", "商业公司周需求计划");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("WEEKREQ", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void WEEKREQCONFIRM() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"EXEC_ARRVIAL_DATE", "CGT_DEMAND_QTY", "COM_CODE", "IND_CODE",
				"BEG_DATE", "END_DATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("EXEC_ARRVIAL_DATE", "20081001");
			bean.put("CGT_DEMAND_QTY", "500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("BEG_DATE", "2009-04-01");
			bean.put("END_DATE", "2009-04-02");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "WEEKREQCONFIRM");
		hData.put("MSGNAME", "商业公司周需求计划确认");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("WEEKREQCONFIRM", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void REQUESTCOCONFIRM() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"EXPECT_ARRIVAL_DATE", "CGT_DEMAND_QTY", "COM_CODE",
				"IND_CODE", "REQUEST_NUM" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("EXPECT_ARRIVAL_DATE", "20010212");
			bean.put("CGT_DEMAND_QTY", "500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("REQUEST_NUM", i);
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "REQUESTCOCONFIRM");
		hData.put("MSGNAME", "应急补货确认信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("REQUESTCOCONFIRM", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void REQUESTCO() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"EXPECT_ARRIVAL_DATE", "CGT_DEMAND_QTY", "COM_CODE",
				"IND_CODE", "REQUEST_NUM" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("EXPECT_ARRIVAL_DATE", "20070212");
			bean.put("CGT_DEMAND_QTY", "13500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("REQUEST_NUM", i);
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "REQUESTCO");
		hData.put("MSGNAME", "应急补货需求信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("REQUESTCO", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void CONTVOUCHER() {
		String[] resultNames = new String[] { "CONT_NUM", "VOUCHER_DATE",
				"COM_CODE", "IND_CODE", "CONTRACT_STATUS" };
		HashMap bean = null;
		List resultList = new ArrayList();
		for (int i = 0; i < 3; i++) {
			bean = new HashMap();
			bean.put("CONT_NUM", i);
			bean.put("VOUCHER_DATE", "20091116");
			bean.put("COM_CODE", "11320301");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "CONTVOUCHER");
		hData.put("MSGNAME", "合同到货信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("CONTVOUCHER", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void BPURCHCONSULTHALFYEAR() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "IND_CODE", "HALFYEAR" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "12500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("HALFYEAR", "2009Y1");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "BPURCHCONSULTHALFYEAR");
		hData.put("MSGNAME", "商业公司半年采购衔接需求信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("BPURCHCONSULTHALFYEAR", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PURCHCONSULTCONFIRMHALFYEAR() {
		String[] resultNames = new String[] { "CGT_CARTON_CODE",
				"CGT_DEMAND_QTY", "COM_CODE", "IND_CODE", "HALFYEAR" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_DEMAND_QTY", "1500");
			bean.put("COM_CODE", "11320301");
			bean.put("IND_CODE", "20420001");
			bean.put("HALFYEAR", "2009Y1");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "PURCHCONSULTCONFIRMHALFYEAR");
		hData.put("MSGNAME", "商业公司半年采购衔接确认信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURCHCONSULTCONFIRMHALFYEAR", resultList,
				resultNames, hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PURSALSTK() {
		String[] resultNames = new String[] { "COM_CODE", "CGT_CARTON_CODE",
				"CGT_PUR_QTY", "COM_SAL_QTY", "CGT_STK_QTY", "TRANS_DATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_PUR_QTY", "250");
			bean.put("COM_SAL_QTY", "250");
			bean.put("CGT_STK_QTY", "250");
			bean.put("COM_CODE", "11320301");
			bean.put("TRANS_DATE", "2009-12-09");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "PURSALSTK");
		hData.put("MSGNAME", "商业购销存信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURSALSTK", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void WEEKREQSUPPLY() {
		String[] resultNames = new String[] { "COM_CODE", "BEG_DATE",
				"END_DATE", "CGT_CARTON_CODE", "PLAN_ARRIVAL_DATE",
				"CGT_SUPPLY_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_SUPPLY_QTY", "1500");
			bean.put("COM_CODE", "11320301");
			bean.put("PLAN_ARRIVAL_DATE", "20081001");
			bean.put("BEG_DATE", "20090401");
			bean.put("END_DATE", "20090407");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "WEEKREQSUPPLY");
		hData.put("MSGNAME", "周需求计划反馈信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("WEEKREQSUPPLY", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void CONTEXEC() {
		String[] resultNames = new String[] { "CONT_NUM", "EXEC_DATE",
				"PLAN_SHIPPMENT_DATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		for (int i = 0; i < 3; i++) {
			bean = new HashMap();
			bean.put("CONT_NUM", i);
			bean.put("EXEC_DATE", "20091116");
			bean.put("PLAN_SHIPPMENT_DATE", "20091116");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "CONTEXEC");
		hData.put("MSGNAME", "合同执行信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("CONTEXEC", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PURCHCONSULT() {
		String[] resultNames = new String[] { "COM_CODE", "MONTH",
				"CGT_CARTON_CODE", "CGT_SUPPLY_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_SUPPLY_QTY", "11500");
			bean.put("COM_CODE", "11320301");
			bean.put("MONTH", "200904");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "PURCHCONSULT");
		hData.put("MSGNAME", "工业采购衔接可供信息反馈");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURCHCONSULT", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void MONSALE() {
		String[] resultNames = new String[] { "COM_CODE", "MONTH",
				"CGT_CARTON_CODE", "DIV_CODE", "IND_SAL_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("IND_SAL_QTY", "11500");
			bean.put("COM_CODE", "11320301");
			bean.put("MONTH", "200904");
			bean.put("DIV_CODE", "110200");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "MONSALE");
		hData.put("MSGNAME", "工业销售信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("MONSALE", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PurchConsultHalfYear() {
		String[] resultNames = new String[] { "COM_CODE", "HALFYEAR",
				"CGT_CARTON_CODE", "CGT_SUPPLY_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_SUPPLY_QTY", "11500");
			bean.put("COM_CODE", "11320301");
			bean.put("HALFYEAR", "2009Y1");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "PURCHCONSULT_HALF");
		hData.put("MSGNAME", "工业半年采购衔接可供信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURCHCONSULT_HALF", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void MARKETINFO() {
		String[] resultNames = new String[] { "COM_ID", "YEARMONTH", "PERIOD",
				"PRODUCT_ID", "PRODUCT_NAME", "MARKET_PRICE", "RETAIL_PRICE",
				"DISTR_RATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COM_ID", "11440101");
			bean.put("YEARMONTH", "2008-03");
			bean.put("PERIOD", "1");
			bean.put("PRODUCT_NAME", "84mm条盒硬盒玉溪");
			bean.put("PRODUCT_ID", tobaccoIds[i]);
			bean.put("MARKET_PRICE", "50.00");
			bean.put("RETAIL_PRICE", "50.00");
			bean.put("DISTR_RATE", "50");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "MARKETINFO");
		hData.put("MSGNAME", "广州市场信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("MARKETINFO", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void STORAGEWARN() {
		String[] resultNames = new String[] { "COM_ID", "DATE", "PRODUCT_ID",
				"PRODUCT_NAME", "OOS_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COM_ID", "11440101");
			bean.put("DATE", "2008-03-01");
			bean.put("PRODUCT_NAME", "84mm条盒硬盒玉溪");
			bean.put("PRODUCT_ID", tobaccoIds[i]);
			bean.put("OOS_QTY", "50.00");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "STORAGEWARN");
		hData.put("MSGNAME", "广州库存预警");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("STORAGEWARN", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void RETAILBILLMERGE() {
		String[] resultNames = new String[] { "COM_ID", "DATE", "PRODUCT_ID",
				"PRODUCT_NAME", "DEMAND_QTY", "ORDER_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COM_ID", "11440101");
			bean.put("DATE", "20090828");
			bean.put("PRODUCT_NAME", "84mm条盒硬盒玉溪");
			bean.put("PRODUCT_ID", tobaccoIds[i]);
			bean.put("DEMAND_QTY", "50.00");
			bean.put("ORDER_QTY", "50.00");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "RETAILBILLMERGE");
		hData.put("MSGNAME", "广州零售户单据汇总");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("RETAILBILLMERGE", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void BUYPLAN() {
		String[] resultNames = new String[] { "COM_ID", "DEMAND_ID",
				"YEARMONTH", "PRODUCT_ID", "PRODUCT_NAME", "DEMAND_QTY" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COM_ID", "11440101");
			bean.put("DEMAND_ID", "200803");
			bean.put("YEARMONTH", "200803");
			bean.put("PRODUCT_NAME", "84mm条盒硬盒玉溪");
			bean.put("PRODUCT_ID", tobaccoIds[i]);
			bean.put("DEMAND_QTY", "50.00");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "BUYPLAN");
		hData.put("MSGNAME", "广州月度采购需求量信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("BUYPLAN", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void T_TOBACCO_BARCODE() {
		String[] resultNames = new String[] { "TOBACCO_ID", "TOBACCO_NAME",
				"BAR_BARCODE", "PACK_BARCODE", "ATTRIBUTE1", "TRADE_PRICE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("TOBACCO_ID", 42020003 + i);
			bean.put("TOBACCO_NAME", "黄鹤楼(软红)");
			bean.put("BAR_BARCODE", tobaccoIds[i]);
			bean.put("PACK_BARCODE", "");
			bean.put("ATTRIBUTE1", "ATTRIBUTE1");
			bean.put("TRADE_PRICE", "123.155");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "MSGCODE");
		hData.put("MSGID", "T_TOBACCO_BARCODE");
		hData.put("MSGNAME", "卷烟条码信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("T_TOBACCO_BARCODE", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void PURSALSTK_HB() {
		String[] resultNames = new String[] { "COM_CODE", "TOBACCO_ID",
				"CGT_CARTON_CODE", "CGT_PUR_QTY", "COM_SAL_QTY", "CGT_STK_QTY",
				"TRANS_DATE" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "6901028180184", "6901028179645",
				"6901028185578" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("TOBACCO_ID", i);
			bean.put("CGT_CARTON_CODE", tobaccoIds[i]);
			bean.put("CGT_PUR_QTY", "250");
			bean.put("COM_SAL_QTY", "250");
			bean.put("CGT_STK_QTY", "250");
			bean.put("COM_CODE", "11320301");
			bean.put("TRANS_DATE", "2009-12-09");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "PURSALSTK_HB");
		hData.put("MSGNAME", "省局进销存信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("PURSALSTK", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void RETAILER_MONTH_TRADE() {
		String[] resultNames = new String[] { "COMP_STD_ID", "CUST_ID",
				"TOBACCO_ID", "YEAR_MONTH", "BUY_QTY", "INVENTORY_QTY",
				"QTY_UNITS" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "42900005", "42020039", "42020036" };
		String[] compIds = new String[] { "11440101", "11320301", "11429002" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COMP_STD_ID", compIds[i]);
			bean.put("CUST_ID", "250");
			bean.put("TOBACCO_ID", tobaccoIds[i]);
			bean.put("YEAR_MONTH", "200909");
			bean.put("BUY_QTY", "250.0001");
			bean.put("INVENTORY_QTY", "125.1111");
			bean.put("QTY_UNITS", "箱");
			bean.put("ACTION", "INSERT");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "RETAILER_MONTH_TRADE");
		hData.put("MSGNAME", "湖北省内580户零售户样本数据");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("T_RETAILER_MONTH_TRADE", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void RETAILER_WEEK_TRADE() {
		String[] resultNames = new String[] { "COMP_STD_ID", "CUST_ID",
				"TOBACCO_ID", "START_YEAR", "WEEK_ID", "BUY_QTY",
				"INVENTORY_QTY", "QTY_UNITS" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "42900005", "42020039", "42020036" };
		String[] compIds = new String[] { "11440101", "11320301", "11429002" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COMP_STD_ID", compIds[i]);
			bean.put("CUST_ID", "250");
			bean.put("TOBACCO_ID", tobaccoIds[i]);
			bean.put("START_YEAR", "2009");
			bean.put("WEEK_ID", "33");
			bean.put("BUY_QTY", "250.0001");
			bean.put("INVENTORY_QTY", "125.1111");
			bean.put("QTY_UNITS", "箱");
			bean.put("ACTION", "INSERT");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "RETAILER_WEEK_TRADE");
		hData.put("MSGNAME", "湖北省内2000零售户样本数据");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("T_RETAILER_WEEK_TRADE", resultList, resultNames,
				hData, headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}

	private void T_RETAILER_INFO() {
		String[] resultNames = new String[] { "COMP_STD_ID", "CUST_ID",
				"CUST_NAME", "COLLECTION_TYPE", "MARKET_TYPE", "PROPERTY",
				"CLIENT_LEVEL" };
		HashMap bean = null;
		List resultList = new ArrayList();
		String[] tobaccoIds = new String[] { "42900005", "42020039", "42020036" };
		String[] compIds = new String[] { "11440101", "11320301", "11429002" };
		for (int i = 0; i < tobaccoIds.length; i++) {
			bean = new HashMap();
			bean.put("COMP_STD_ID", compIds[i]);
			bean.put("CUST_ID", "250");
			bean.put("CUST_NAME", "客户名称");
			bean.put("COLLECTION_TYPE", "采集");
			bean.put("MARKET_TYPE", "市场类型");
			bean.put("PROPERTY", "经营业态");
			bean.put("CLIENT_LEVEL", "客户星级");
			bean.put("ACTION", "INSERT");
			resultList.add(bean);
		}
		String[] headNames = new String[] { "MSGCODE", "MSGID", "MSGNAME",
				"SOURCESYS", "TARGETSYS", "ACTION", "CREATETIME" };
		Hashtable hData = new Hashtable();
		hData.put("MSGCODE", "");
		hData.put("MSGID", "T_RETAILER_INFO");
		hData.put("MSGNAME", "湖北省内零售户信息");
		hData.put("SOURCESYS", "DATACENT");
		hData.put("TARGETSYS", "NSAL");
		hData.put("ACTION", "INSERT");// INSERT,UPDATE,DELETE
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysDatetime = fmt.format(rightNow.getTime());
		hData.put("CREATETIME", sysDatetime);
		String str = makeXml("T_RETAILER_INFO", resultList, resultNames, hData,
				headNames);
		MQOpera mqo = new MQOpera();
		mqo.sendMQtest(str);
	}
}