package com.frame.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.RandomStringUtils;
/**
 * 该类提供些数值格式化为字符串的静态方法
 */
public class SparkLib {
	/**
	 * 空字符串
	 */
  public static final String     EMPTY_STRING          = "";
  /**
   * 字符串"0"
   */
  public static final String     SQL_FALSE             = "0";
  /**
   * 字符串"1"
   */
  public static final String     SQL_TRUE              = "1";
  
  /**
   * 十六进制字符
   */
  public final static char[] HEX_CHARS={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
 /**
  * base64字符
  */
  public final static char[] BASE64_CHARS = {
      'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
      'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
      '0','1','2','3','4','5','6','7','8','9','+','/','='};
  /**
   * 判断字符串是否为字母或数字
   * @param s 字符串
   * @return false:包含字母或数字以外的字符 true:全是字母或数字
   */
  public static boolean isSymbol(String s){
    if((s==null)||(s.length()==0)) return false;
    for(int i=0;i<s.length();i++)
      if(!Character.isLetterOrDigit(s.charAt(i))) return false;
    return true;
  }
  /**
   * 用rep替换字符串s中所有sub字符串
   * @param s 母字符串
   * @param sub 被替换掉的字符串
   * @param rep 用来替换的字符串
   * @return 替换后的字符串
   */
  public static String replaceString(String s, String sub, String rep){
	    if (rep==null) rep = "";
	    StringBuffer sb = new StringBuffer();
	    int fromIndex = 0, pos =0;
	    do{
	      pos = s.indexOf(sub,fromIndex);
	      if (pos>=0){
	        if (pos>fromIndex)
	          sb.append(s.substring(fromIndex,pos));
	        sb.append(rep);
	        fromIndex = pos + sub.length();
	      }
	    } while (pos >= 0);
	    if (fromIndex<s.length())
	      sb.append(s.substring(fromIndex,s.length()));
	    return sb.toString();
	  }
  
  public static boolean isIdentifier(String s){
    if((s==null)||(s.length()==0)) return false;
    for(int i=0;i<s.length();i++)
      if(!Character.isLetterOrDigit(s.charAt(i))) return false;
    return Character.isLetter(s.charAt(0));
  }
  /**
   * 判断字符串是否包含了空白字符
   * @param s 字符串
   * @return true:包含空白符 false:不包含
   */
  public static boolean hasSpaceChar(String s){
    for(int i=0;i<s.length();i++)
      if(Character.isSpaceChar(s.charAt(i))) return true;
    return false;
  }
  /**
   * 获取ISO8859_1解码后的字符串
   * @param input 字符串
   * @return 解码后的字符串
   */
  public static String convInput(String input){
    try {
      return new String(input.getBytes("ISO8859_1"));
    }
    catch (Exception ex) {
      return input;
    }
  }
  /**
   * 字符串添加双引号
   * @param s 要添加双引号的字符串
   * @return 添加双引号后的字符串
   */
  public static String quoteString(String s){
    return '"'+s+'"';
  }
  /**
   * 字符串添加单引号
   * @param s 要添加单引号的字符串
   * @return 添加单引号后的字符串
   */
  public static String quoteStringSingle(String s){
    return "'"+s+"'";
  }
  /**
   * 格式化数值为字符串
   * @param iv 数值
   * @param width 整数部分的位数
   * @param seperator 是否分组格式化
   * @return 格式化后的数字字符串
   */
  public static String toFixedString(long iv, int width, boolean seperator){
    NumberFormat fmt=new DecimalFormat();
    fmt.setGroupingUsed(seperator);
    fmt.setMaximumIntegerDigits(width);
    fmt.setMinimumIntegerDigits(width);
    return fmt.format(iv);
  }
  /**
   * 格式化数值为字符串
   * @param iv 数值
   * @param seperator 是否分组格式化
   * @return 格式化后的数字字符串
   */
  public static String toFixedString(long iv, boolean seperator){
    NumberFormat fmt=new DecimalFormat();
    fmt.setGroupingUsed(seperator);
    return fmt.format(iv);
  }
  /**
   * 格式化数值为字符串
   * @param value 数值
   * @param seperator 是否分组格式化
   * @return 格式化后的数字字符串
   */
  public static String toFixedString(double value, boolean seperator){
    NumberFormat fmt=new DecimalFormat();
    fmt.setGroupingUsed(seperator);
    return fmt.format(value);
  }
  /**
   * 从字符串中把某个字符全部剔除掉
   * @param str 字符串
   * @param ch 要剔除掉的字符
   * @return 剔除某个字符后的字符串
   */
  public static String strExclude(String str, char ch){
    if(str==null) return null;
    char[] chars=new char[str.length()];
    int index=0;
    for (int i=0; i<str.length(); i++){
      char c=str.charAt(i);
      if(c!=ch)chars[index++]=c;
    }
    return new String(chars,0,index);
  }
  /**
   * 从字符串中读出指定行数的内容
   * @param text 字符串
   * @param linenum 行数
   * @return 读出的内容
   */
  public static String getLine(String text, int linenum){
    BufferedReader buffer=new BufferedReader(new StringReader(text));
    try{
      String line=null;
      while(linenum-->=0)
        line=buffer.readLine();
      return line;
    }catch(IOException ex){
      return text;
    }
  }

  public static String encodePassword(String password,int maxlength){
    int s1=97;
    int s2=76;
    int len=password==null?0:password.length();

    if(len>maxlength) len=maxlength;
    StringBuffer pw=new StringBuffer(2*maxlength);
    for(int i=0;i<maxlength;i++){
      int ch,ch1,ch2;
      ch=i>=len?0:password.charAt(i);
      ch=(s1+++ch)^s2++;
      ch1=ch/26+(int)'I';ch2=ch%26+(int)'A';
      if((i & 1)==1) s2+=ch1&0xf;
      else s2-=ch1&0xf;
      pw.append((char)ch1);
      pw.append((char)ch2);
    }
    return new String(pw);
  }

  public static String decodePassword(String password){
    int s1=97;
    int s2=76;
    int len=password==null?0:password.length()/2;

    StringBuffer pw=new StringBuffer();
    for(int i=0;i<len;i++){
      int ch,ch1,ch2;
      ch1=password.charAt(2*i);
      ch2=password.charAt(2*i+1);
      ch=(ch1-(int)'I')*26+(ch2-(int)'A');
      ch=(ch^s2++)-(s1++);
      if(ch==0) break;
      pw.append((char)ch);
      if((i & 1)==1) s2+=ch1&0xf;
      else s2-=ch1&0xf;
    }
    return new String(pw);
  }

  public static int[] stringToColRow(String str){
    int[] pos=new int[2];
    pos[0]=0;pos[1]=0;
    for (int i=0; i<str.length(); i++){
      char ch=str.charAt(i);
      if(ch>='A' && ch<='Z'){
        pos[0]=pos[0]*26+(byte)ch-(byte)'A';
      }else{
        if(i>0) pos[0]+=1;
        try{
          pos[1]=Integer.parseInt(str.substring(i));
        }catch(NumberFormatException ex){
          pos[1]=0;
        }
        break;
      }
    }
    if(pos[0]>0 && pos[1]>0) return pos;
    else return null;
  }
  public static String colRowToString(int x, int y){
    char[] chrArray = new char[64];
    int len = 0;
    while(x > 0){
      char ch = (char)(x % 26 + (byte)'A' - 1);
      x = x / 26;
      if(ch < 'A'){
        ch = 'Z';
        x -= 1;
      }
      chrArray[len] = ch;
      len += 1;
    }
    String colStr = "";
    for(int i=0;i<len;i++)
      colStr = colStr + chrArray[len-i-1];
    return colStr+Integer.toString(y);
  }
  /**
   * 十进制数转化位十六进制
   * @param iv 十进制数
   * @return 转化后的十六进制数字字符串
   */
  public static String makeHexString(int iv) {
     char[] buf = new char[8];
     int charPos = 7;
     while(iv!=0){
       buf[charPos--]=HEX_CHARS[iv & 15];
       iv>>>=4;
     };
     while(charPos>=0) buf[charPos--]=HEX_CHARS[0];
     return new String(buf);
 }
  
 public static void main(String args[]){
	 for(int i=0;i<10000;i++){
		 System.out.println(makeHexString(new BigDecimal(RandomStringUtils.randomNumeric(6)).intValue()));
	 }
	
 }
}
