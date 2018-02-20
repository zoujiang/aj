package com.frame.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 浮点值辅助处理函数
 * 
 * @author liuxiaochun
 * @author huangrui
 */
public class DoubleUtil{

	/**
	 * 根据double值创建BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal createBigDecimal(double value){
		return BigDecimal.valueOf(value);
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数).
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double round(double value, int scale, int roundingMode){
		BigDecimal bd = createBigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		return bd.doubleValue();
	}

	/**
	 * 对double数据进行取精度，超出末位部分四舍五入
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double round(double value, int scale){
		return round(value, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 对double数据进行取精度，保留两位小数，超出部分四舍五入
	 * 
	 * @param value
	 * @return
	 */
	public static double round(double value){
		return round(value, 2);
	}

	/**
	 * 对double数据进行取精度，保留两位小数，超出部分四舍五入
	 * 并返回保留两位小数格式的字符串
	 * @param value
	 * @return String
	 */
	public static String getRoundStr(Double value){
		if(null == value){
			return "";
		}
		return new DecimalFormat("###,##0.00").format(round(value, 2));
	} 
	
	public static String getRoundStr(Double value, boolean hasThousands){
		if(null == value){
			return "";
		}
		if(hasThousands) {
			return new DecimalFormat("###,##0.00").format(round(value, 2));
		} else {
			return new DecimalFormat("##0.00").format(round(value, 2));
		}
	} 
	/**
	 * 对double数据进行取精度，超出部分四舍五入
	 * 并返回相应格式的字符串
	 * @param value
	 * @param scale(大于等于零的整数)
	 * @return String
	 */
	public static String getRoundStr(Double value, int scale){
		if(null == value){
			return "";
		}
		String formater = ".00";
		if(scale > 0){
			StringBuilder ss = new StringBuilder(".");
			for(int i = 0; i < scale; i++){
				ss.append("0");
			}
			formater = ss.toString();
		}
		else if(scale == 0){
			formater = "";
		}
		return new DecimalFormat("###,##0" + formater).format(round(value, scale));
	}
	/**
	 * 对double数据进行取精度，超出部分四舍五入
	 * 并返回相应格式的字符串(非千分位格式)
	 * @param value
	 * @param scale(大于等于零的整数)
	 * @return String
	 */
	public static String getRoundStrWithOutQfw(Double value, int scale){
		if(null == value){
			return "";
		}
		String formater = ".00";
		if(scale > 0){
			StringBuilder ss = new StringBuilder(".");
			for(int i = 0; i < scale; i++){
				ss.append("0");
			}
			formater = ss.toString();
		}
		else if(scale == 0){
			formater = "";
		}
		return new DecimalFormat("##0" + formater).format(round(value, scale));
	}
	/**
	 * 将转换为千分位的double字符串，转换回double
	 * @param value
	 * @return String
	 */
	public static Double strToDouble(String value){
		if(null == value||"".equals(value.trim())){
			return null;
		}
		return Double.parseDouble(value.replaceAll(",", "").trim());
	}
	/**
	 * 将转换为千分位的double字符串，转换回double,包含精度
	 * @param value
	 * @return String
	 */
	public static Double strToDouble(String value,int scale){
		if(null == value||"".equals(value.trim())){
			return null;
		}
		return round(Double.parseDouble(value.replaceAll(",", "").trim()),scale);
	}
	/**
	 * 将数字字符串转换为千分位的double字符串
	 * @param value
	 * @return String
	 */
	public static String strToQfwStr(String value){
		if(null == value||"".equals(value.trim())){
			return null;
		}
		return getRoundStr(strToDouble(value));
	}
	
	/**
	 * 将数字字符串转换为千分位的double字符串
	 * @param value
	 * @return String
	 */
	public static String strToQfwStr(String value,int scale){
		if(null == value||"".equals(value.trim())){
			return null;
		}
		return getRoundStr(strToDouble(value),scale);
	}

	/**
	 * double 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sum(double d1, double d2){
		return sum(createBigDecimal(d1), createBigDecimal(d2)).doubleValue();
	}

	/**
	 * double 相减
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double d2){
		return sub(createBigDecimal(d1), createBigDecimal(d2)).doubleValue();
	}

	/**
	 * double 相乘
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(double d1, double d2){
		return mul(createBigDecimal(d1), createBigDecimal(d2)).doubleValue();
	}
	
	/**
	 * double 相乘
	 * 
	 * @param d1
	 * @param d2
	 * @param scale 四舍五入 小数点位数
	 * @return 四舍五入 
	 */
	public static double mul(double d1, double d2,int scale){
		return round(mul(createBigDecimal(d1), createBigDecimal(d2)).doubleValue(),scale);
	}

	/**
	 * double 相除
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            四舍五入 小数点位数
	 * @return
	 */
	public static double div(double d1, double d2, int scale){
		return round(div(createBigDecimal(d1), createBigDecimal(d2)).doubleValue(), scale);
	}

	/**
	 * double 相除，保留2位小数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double div(double d1, double d2){
		return div(d1, d2, 2);
	}

	/**
	 * BigDecimal 相加
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal sum(BigDecimal bd1, BigDecimal bd2){
		return bd1.add(bd2);
	}

	/**
	 * BigDecimal 相减
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal bd1, BigDecimal bd2){
		return bd1.subtract(bd2);
	}

	/**
	 * BigDecimal 相乘
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal bd1, BigDecimal bd2){
		return bd1.multiply(bd2);
	}

	/**
	 * BigDecimal 相除
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal div(BigDecimal bd1, BigDecimal bd2){
		if(bd2.equals(BigDecimal.ZERO)){
			throw new IllegalArgumentException("除数不能为0。");
		}
		return bd1.divide(bd2, 12, RoundingMode.HALF_UP);
	}

	/**
	 * BigDecimal 和 double 相加
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal sum(BigDecimal bd1, double d2){
		return sum(bd1, createBigDecimal(d2));
	} 
	/**
	 * BigDecimal 和 double 相减
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal bd1, double d2){
		return sub(bd1, createBigDecimal(d2));
	}

	/**
	 * BigDecimal 和 double 相乘
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal bd1, double d2){
		return mul(bd1, createBigDecimal(d2));
	}

	/**
	 * BigDecimal 和 double 相除
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal div(BigDecimal bd1, double d2){
		return div(bd1, createBigDecimal(d2));
	}
	
	/**
	 * 取绝对值
	 * 
	 * @param d1
	 * @return double
	 */
	public static double abs(double d1)
	{
		return Math.abs(d1);
	}

	/**
	 * 均分一个值为多份（保留两位小数），所有结果只和等于原值<br>
	 * 如果均分后结果只和不等于原值，则在最后一个结果上补平
	 * 
	 * @param value
	 *            原值
	 * @param times
	 *            均分份数，必须大于1
	 * @return
	 */
	public static double[] split(double value, int times){
		if(times > 0){
			throw new IllegalArgumentException("均分份数必须大于0");
		}
		double[] result = new double[times];
		BigDecimal dbValue = createBigDecimal(value);
		BigDecimal average = div(dbValue, times).setScale(2, BigDecimal.ROUND_HALF_UP);
		for(int i = 0; i < times - 1; i++)
			result[i] = average.doubleValue();
		result[times - 1] = sub(dbValue, mul(average, times - 1)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}

	/**
	 * 数字格式化
	 */
	public static final String DATA_FOMAT = "%(,.2f";
}
