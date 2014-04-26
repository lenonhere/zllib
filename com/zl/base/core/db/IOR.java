package com.zl.base.core.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

public class IOR
{
  private ArrayList rslist = null;
  private int ResultCount = 0;
  private int sucess;
  private ArrayList InfoDump = null;
  private ArrayList InfoError = null;
  private ArrayList InfoTrace = null;
  static Logger logger = Logger.getLogger(IOR.class.getName());

  public int getSucess()
  {
    return this.sucess;
  }

  public void setSucess(int paramInt)
  {
    this.sucess = paramInt;
  }

  public ArrayList getResultSet(int paramInt)
  {
    paramInt -= 1;
    List localList = null;
    if (paramInt < this.rslist.size())
    {
      RowSetDynaClass localRowSetDynaClass = (RowSetDynaClass)this.rslist.get(paramInt);
      localList = localRowSetDynaClass.getRows();
    }
    return (ArrayList)localList;
  }

  public void addResultSet(ResultSet paramResultSet)
  {
    if (this.rslist == null)
      this.rslist = new ArrayList();
    try
    {
      RowSetDynaClass localRowSetDynaClass = new RowSetDynaClass(paramResultSet);
      this.rslist.add(localRowSetDynaClass);
    }
    catch (Exception localException)
    {
      localException.getMessage();
    }
  }

  public ArrayList getInfoDump()
  {
    return this.InfoDump;
  }

  public void setInfoDump(ArrayList paramArrayList)
  {
    this.InfoDump = paramArrayList;
  }

  public ArrayList getInfoError()
  {
    return this.InfoError;
  }

  public void setInfoError(ArrayList paramArrayList)
  {
    this.InfoError = paramArrayList;
  }

  public ArrayList getInfoTrace()
  {
    return this.InfoTrace;
  }

  public void setInfoTrace(ArrayList paramArrayList)
  {
    this.InfoTrace = paramArrayList;
  }

  public int getResultCount()
  {
    this.ResultCount = this.rslist.size();
    return this.ResultCount;
  }

  public void setResultSets(ArrayList paramArrayList)
  {
    if (paramArrayList != null)
      this.rslist = paramArrayList;
  }

  public ArrayList getResultSets()
  {
    if (this.rslist == null)
      this.rslist = new ArrayList();
    return this.rslist;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.InfoTrace != null)
      localStringBuffer.append("\n InfoTrace Size =" + this.InfoTrace.size());
    if (this.InfoError != null)
      localStringBuffer.append(localStringBuffer + "\n InfoError Size =" + this.InfoError.size());
    if (this.InfoDump != null)
      localStringBuffer.append(localStringBuffer + "\n InfoDump Size =" + this.InfoDump.size());
    if (this.rslist != null)
    {
      localStringBuffer.append("\n 结果集总数为(" + this.rslist.size() + ")个");
      for (int i = 1; i <= this.rslist.size(); i++)
      {
        RowSetDynaClass localRowSetDynaClass = (RowSetDynaClass)this.rslist.get(i - 1);
        List localList = localRowSetDynaClass.getRows();
        localStringBuffer.append("\n 第(" + i + ")个结果集有(" + localList.size() + ")条记录");
        DynaProperty[] arrayOfDynaProperty = localRowSetDynaClass.getDynaProperties();
        for (int j = 0; j < arrayOfDynaProperty.length; j++)
        {
          localStringBuffer.append("\n 字段(" + j + ")\t\t" + arrayOfDynaProperty[j].getName());
          if (localList.size() > 0)
          {
            BasicDynaBean localBasicDynaBean = (BasicDynaBean)localList.get(0);
            localStringBuffer.append("\t\t\t|" + localBasicDynaBean.get(arrayOfDynaProperty[j].getName().toString()));
          }
        }
      }
    }
    return localStringBuffer.toString();
  }

  public static void main(String[] paramArrayOfString)
  {
    IOR localIOR = new IOR();
    localIOR.setResultSets(null);
  }
}

/* Location:           E:\zllib\zllib.jar
 * Qualified Name:     com.zl.base.core.db.IOR
 * JD-Core Version:    0.6.1
 */