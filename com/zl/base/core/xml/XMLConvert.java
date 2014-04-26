package com.zl.base.core.xml;

import com.zl.util.MethodFactory;

import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLConvert
{
  public String RowSetDynatoXmlString(ArrayList paramArrayList, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "";
    localStringBuffer.append("<" + paramString + ">");
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      BasicDynaBean localBasicDynaBean = (BasicDynaBean)paramArrayList.get(i);
      DynaProperty[] arrayOfDynaProperty = localBasicDynaBean.getDynaClass().getDynaProperties();
      localStringBuffer.append("<row ");
      for (int j = 0; j < arrayOfDynaProperty.length; j++)
      {
        localStringBuffer.append(" " + arrayOfDynaProperty[j].getName().trim());
        try
        {
          str = MethodFactory.getThisString(PropertyUtils.getProperty(localBasicDynaBean, arrayOfDynaProperty[j].getName()));
          MethodFactory.replace(str, "&", "&amp;");
          MethodFactory.replace(str, "<", "&lt;");
          MethodFactory.replace(str, ">", "&gt;");
        }
        catch (Exception localException)
        {
          str = "";
        }
        localStringBuffer.append("=\"" + str + "\"");
      }
      localStringBuffer.append("/>");
    }
    localStringBuffer.append("</" + paramString + ">");
    return localStringBuffer.toString();
  }

  public String RowSetDynatoXmlString2(ArrayList paramArrayList, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "";
    localStringBuffer.append("<" + paramString + ">");
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      BasicDynaBean localBasicDynaBean = (BasicDynaBean)paramArrayList.get(i);
      DynaProperty[] arrayOfDynaProperty = localBasicDynaBean.getDynaClass().getDynaProperties();
      localStringBuffer.append("<row>");
      for (int j = 0; j < arrayOfDynaProperty.length; j++)
      {
        localStringBuffer.append("<" + arrayOfDynaProperty[j].getName().trim() + ">");
        try
        {
          str = MethodFactory.getThisString(PropertyUtils.getProperty(localBasicDynaBean, arrayOfDynaProperty[j].getName()));
          MethodFactory.replace(str, "&", "&amp;");
          MethodFactory.replace(str, "<", "&lt;");
          MethodFactory.replace(str, ">", "&gt;");
        }
        catch (Exception localException)
        {
          str = "";
        }
        localStringBuffer.append(str);
        localStringBuffer.append("</" + arrayOfDynaProperty[j].getName().trim() + ">");
      }
      localStringBuffer.append("</row>");
    }
    localStringBuffer.append("</" + paramString + ">");
    return localStringBuffer.toString();
  }

  public String XmlToString(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      paramString1 = MethodFactory.replace(paramString1, "\n", "");
      paramString1 = MethodFactory.replace(paramString1, "\r", "");
      DocumentBuilder localDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      StringReader localStringReader = new StringReader(paramString1);
      InputSource localInputSource = new InputSource(localStringReader);
      Document localDocument = localDocumentBuilder.parse(localInputSource);
      Element localElement = localDocument.getDocumentElement();
      NodeList localNodeList1 = localElement.getChildNodes();
      for (int i = 0; i < localNodeList1.getLength(); i++)
      {
        NodeList localNodeList2 = localNodeList1.item(i).getChildNodes();
        System.out.println(localNodeList2.getLength());
        for (int j = 0; j < localNodeList2.getLength(); j++)
          if ((paramInt == -1) || (paramInt == j))
          {
            Node localNode = localNodeList2.item(j);
            if (localNode.getFirstChild() == null)
              localStringBuffer.append("");
            else
              localStringBuffer.append(localNode.getFirstChild().getNodeValue());
            if (((i != localNodeList1.getLength() - 1) && (paramInt != -1)) || ((paramInt == -1) && (j != localNodeList2.getLength() - 1)))
              localStringBuffer.append(paramString2);
          }
        if ((i != localNodeList1.getLength() - 1) && (paramInt == -1))
          localStringBuffer.append(paramString3);
      }
    }
    catch (Exception localException)
    {
    }
    return localStringBuffer.toString();
  }

  public static void main(String[] paramArrayOfString)
  {
    String str = "";
    str = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>";
    str = str + "<root>\n<row>\n<name>aaab</name>\n<id></id>\n</row>\n<row>\n<name>aaa</name>\n<id>1</id>\n</row>\n<row>\n<name>aaa</name>\n<id>1</id>\n</row>\n</root>\n";
    System.out.print(new XMLConvert().XmlToString(str, ",", ";", -1));
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.xml.xmlconvert
 * JD-Core Version:    0.6.1
 */