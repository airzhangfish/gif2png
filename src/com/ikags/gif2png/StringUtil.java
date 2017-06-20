package com.ikags.gif2png;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;
/**
 * 关于字符串和字符转换，数字转换的工具类
 * @author airzhangfish
 *
 */
public class StringUtil
{

  public static final String TAG = "StringUtil";

  /**
   * 
   * 从一个输入流中根据一定的编码方式读取出内容文本
   * 
   * @param in InputStream，输入流
   * 
   * @param encode String，编码方式
   * 
   * @return String 内容文本
   * 
   * @throws UnsupportedEncodingException
   * 
   * @throws IOException
   */
  public static String getInputStreamText(InputStream in, String encode)
  {
    byte[] in2b = getInputStreamBytes(in);
    return getInputStreamText(in2b, encode);
  }

  public static String getInputStreamText(byte[] data, String encode)
  {
    try
    {
      if (data == null)
      {
        return null;
      }
      return new String(data, encode);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new String(data);
    }
  }

  public static byte[] getInputStreamBytes(InputStream in)
  {
    ByteArrayOutputStream swapStream = null;
    try
    {
      swapStream = new ByteArrayOutputStream();
      byte[] buff = new byte[50*1024];
      int rc = 0;
      while ((rc = in.read(buff, 0, buff.length)) > 0)
      {
        swapStream.write(buff, 0, rc);
      }
      byte[] in2b = swapStream.toByteArray();
      return in2b;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
    finally
    {
      if (swapStream != null)
      {
        try
        {
          swapStream.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  public static byte[] shortToByte(short s)
  {
    byte[] mybytes = new byte[2];
    mybytes[1] = ( byte ) (0xff & s);
    mybytes[0] = ( byte ) ((0xff00 & s) >> 8);
    return mybytes;
  }

  public static short byteToShort(byte mybytes[], int nOff)
  {
    return ( short ) ((( short ) (0xff & mybytes[nOff + 0]) << 8) | (( short ) (0xff & mybytes[nOff + 1])));
  }

  public static byte[] intToByte(int i)
  {
    byte[] mybytes = new byte[4];
    mybytes[3] = ( byte ) (0xff & i);
    mybytes[2] = ( byte ) ((0xff00 & i) >> 8);
    mybytes[1] = ( byte ) ((0xff0000 & i) >> 16);
    mybytes[0] = ( byte ) ( int ) ((( long ) 0xff000000 & ( long ) i) >> 24);
    return mybytes;
  }

  public static int byteToInt(byte mybytes[], int nOff)
  {
    return (0xff & mybytes[nOff + 0]) << 24 | (0xff & mybytes[nOff + 1]) << 16 | (0xff & mybytes[nOff + 2]) << 8 | 0xff & mybytes[nOff + 3];
  }

  public static String byteToHex(byte[] b)
  {
    String stmp = "";
    StringBuilder sb = new StringBuilder();
    for (int k = 0; k < b.length; k++)
    {
      stmp = Integer.toHexString(b[k] & 0xff);
      if (stmp.length() == 1)
        sb.append("0");
      sb.append(stmp);
    }
    return sb.toString();
  }

  public static byte[] hexToByte(String hex)
  {
    int len = hex.length() / 2;
    int offset = 0;
    byte[] b = new byte[len];
    String stemp = "";
    for (int k = 0; k < len; k++)
    {
      offset = k << 1;
      stemp = hex.substring(offset, offset + 2);
      try
      {
        b[k] = ( byte ) (Integer.parseInt(stemp, 16) & 0xff);
      }
      catch (NumberFormatException e)
      {
        b[k] = 0;
      }
    }
    return b;
  }
  
  public static int byteToInt(byte[] bytes)
  {
      int num = bytes[0] & 0xFF;
      num |= ((bytes[1] << 8) & 0xFF00);
      num |= ((bytes[2] << 16) & 0xFF0000);
      num |= ((bytes[3] << 24) & 0xFF000000);
      return num;
  }

  public static short byteToShort(byte[] bytes)
  {
      int num = bytes[0] & 0xFF;
      num |= ((bytes[1] << 8) & 0xFF00);
      return ( short ) num;
  }
  
  
  public static int getStringToInt(String str){
	    int cint=0;
	    try{
	      cint=Integer.parseInt(str);
	    }catch(Exception ex){
	      ex.printStackTrace();
	    }
	    return cint;
	  }
  
  
  
  /**
   * 读取txt UTF8文本数据,每行保存到Vector的String对象里面
   * @param path
   * @return Vector
   */
  public static Vector getInputstreamVector(InputStream input, String encode)
  {
      Vector scripts = new Vector();
      try
      {

          String buf = getInputStreamText(input, encode);
          // 存入数据
          int strstart = 0;
          int strend = 0;
          for (int i = 0;; i++)
          {
              strend = buf.indexOf("\n", strstart);
              if (strend == -1)
              {
                  return scripts;
              }
              scripts.addElement(buf.substring(strstart, strend - 1));
              strstart = strend + 1;
          }
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }
      return scripts;
  }
  
  /**
   * CVS格式读取,反馈给一个以Vector嵌套的Vector里
   * @param path
   * @param encode
   * @return Vector
   */
  public static Vector getCSVData(InputStream in, String encode)
  {
      String[] list;
      list = getInputstreamStrings(in, encode);
      Vector root = new Vector();
      StringBuffer sbu = new StringBuffer();
      for (int i = 0; i < list.length; i++)
      {
          // str.substring(0, str.indexOf(";", 0))
          Vector child = new Vector();
          int start = 0;
          int end = 0;
          for (int j = 0;; j++)
          {
              sbu.delete(0, sbu.length());
              end = list[i].indexOf(",", start);
              if (end > 0)
              {
                  // 检测到分割
                  sbu.append(list[i].substring(start, end));
                  child.addElement(sbu.toString());
                  start = end + 1;
              }
              else
              {
                  // 后面没有检测到分割
                  sbu.append(list[i].substring(start, list[i].length()));
                  child.addElement(sbu.toString());
                  break;
              }
          }
          root.addElement(child);
      }
      return root;
  }
  
  
  /**
   * 读取txt文件,返回存入String[]
   * @param path
   * @return String[]
   */
  public static String[] getInputstreamStrings(InputStream input, String encode)
  {
      try
      {
          Vector ini = getInputstreamVector(input, encode);
          String[] strs = new String[ini.size()];
          for (int i = 0; i < strs.length; i++)
          {
              strs[i] = ( String ) ini.elementAt(i);
          }
          return strs;
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }
      return null;
  }
  
 
  
}
