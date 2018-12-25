package com.sapit.springcloud.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathUtil {

	/**
	 * 
	 * @Description (数字转大写，保留一位小数并去掉小数点)
	 * @param bigDecimal
	 *            3600
	 * @return 叁陆零零零
	 */
	public static String digitUppercase(BigDecimal bigDecimal) {
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String source = new DecimalFormat("#.0").format(bigDecimal);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			if ('.' == source.charAt(i)) {
				continue;
			}
			sb.append(digit[Character.getNumericValue(source.charAt(i))]);
		}

		return sb.toString();
	}

	public static Integer greaterThanZero(Integer integer) {
		if (integer == null || integer < 0) {
			return 0;
		} else {
			return integer;
		}
	}
	
	public static BigDecimal toPercent(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return BigDecimal.ZERO;
		} else {
			return bigDecimal.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	public static BigDecimal toDecimal(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return BigDecimal.ZERO;
		} else {
			return bigDecimal.divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	public static BigDecimal greaterThanZero(BigDecimal bigDecimal) {
		if (bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
			return BigDecimal.ZERO;
		} else {
			return bigDecimal;
		}
	}

	public static void main(String[] args) {
		System.out.println(digitUppercase(new BigDecimal(3600.04)));
	}
	
	/**
	 * chocus添加，把10000转成1万
	 * @param bigDecimal
	 * @return
	 */
	public static BigDecimal toMillion(BigDecimal bigDecimal) {
      if (bigDecimal == null) {
          return BigDecimal.ZERO;
      } else {
          return bigDecimal.divide(new BigDecimal(10000)).setScale(2, BigDecimal.ROUND_HALF_UP);
      }
    }
	
	/**
     * chocus添加，去掉小数点
     * @param bigDecimal
     * @return
     */
	public static BigDecimal toInteger(BigDecimal bigDecimal) {
      if (bigDecimal == null) {
          return BigDecimal.ZERO;
      } else {
          return bigDecimal.divide(new BigDecimal(1)).setScale(0, BigDecimal.ROUND_HALF_UP);
      }
    }
	
	/**
     * chocus添加，保留两位小数
     * @param bigDecimal
     * @return
     */
    public static BigDecimal toDouble(BigDecimal bigDecimal) {
      if (bigDecimal == null) {
          return BigDecimal.ZERO;
      } else {
          return bigDecimal.divide(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_UP);
      }
    }
    
    /**
     * chocus添加，保留一位小数，比例专用
     * @param bigDecimal
     * @return
     */
    public static BigDecimal toOne(BigDecimal bigDecimal) {
      if (bigDecimal == null) {
          return BigDecimal.ZERO;
      } else {
          return bigDecimal.divide(new BigDecimal(1)).setScale(1, BigDecimal.ROUND_HALF_UP);
      }
    }

}
