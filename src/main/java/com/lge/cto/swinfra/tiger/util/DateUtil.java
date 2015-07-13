package com.lge.cto.swinfra.tiger.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.lge.cto.swinfra.tiger.exception.InvalidDateString;

/**
 * Date 핼퍼 클래스
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class DateUtil {

    public final static String YM_FORMAT = "yyyy-MM";
    public final static String YMD_FORMAT = "yyyy-MM-dd";
    public final static String YMDHM_FORMAT = "yyyy-MM-dd HH:mm";
    public final static String YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 파라미터 now에 파라미터 delta를 더함
     * @param now	기준 시각
     * @param delta	가산치
     * @return	계산된 결과 Date
     */
    public static Date addMinutes(Date now, int delta) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, delta);
        return cal.getTime();
    }

    public static Date addDays(Date now, int delta){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
    	cal.add(Calendar.DAY_OF_YEAR, delta);
    	return cal.getTime();
    }
    
    public static Date getDateFromDateFormat(String ymd, DateFormat format) throws ParseException{
    	return format.parse(ymd);
    }
    
    public static long getDiff(Date d1, Date d2){
    	return Math.abs(d2.getTime() - d1.getTime());
    }
    
    public static double getDiffDays(Date d1, Date d2){
    	return (double)DateUtil.getDiff(d1, d2) / (24* 60 * 60 * 1000);
    }
    
    /**
     * Date 자료형을 'yyyy-MM-dd' 형 문자열로 변환
     * 
     * @param date
     * @return yyyy-MM-dd 문자열
     */
    public static String getYMD(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * Date 자료형을 'yyyy-MM-dd HH:mm:ss' 형 문자열로 변환
     * @param date
     * @return
     */
    public static String getYMDHMS(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    /**
     * 'yyyy-MM-dd HH:mm:ss' 문자열을 Date 자료형으로 변환
     * @param ymdhms
     * @return
     * @throws ParseException
     */
    public static Date str2DateTime(String ymdhms) throws ParseException {
        isValidDateParameters(ymdhms, YMDHMS_FORMAT);

        return (new SimpleDateFormat(YMDHMS_FORMAT)).parse(ymdhms);
    }

    /**
     * Date 자료형을 yyyy-mm-dd 형식의 문자열으로 변환
     * 
     * @param date
     * @return
     */
    public static String date2str(java.util.Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * 일자 문자열(yyyy-MM-dd)을 Date 자료형으로 변환
     * 
     * @param ymd
     * @return
     * @throws ParseException
     */
    public static java.util.Date str2Date(String ymd) throws ParseException {
        isValidDateParameters(ymd, YMD_FORMAT);

        return (new SimpleDateFormat("yyyy-MM-dd")).parse(ymd);
    }

    /**
     * 문자열의 시각이 파라미터 format 형식인지 체크
     * @param date		문자열의 시각
     * @param format	체크할 시각 포멧
     */
    public static void isValidDateParameters(String date, String format) {

    	
    	if (date == null || date.length() == 0){
    		throw new InvalidDateString("date should not be null or empty");
    	}
    	
    	if (format == null || format.length() == 0){
    		throw new InvalidDateString("format should not be null or empty");
    	}

        if (format.length() != date.length()) {
            throw new InvalidDateString("the length of date and format must be the same");
        }
        
        
        switch(format){
        case DateUtil.YM_FORMAT:
            try {
                Integer.parseInt(date.substring(0, 3));
                Integer.parseInt(date.substring(5, 6));
            } catch (NumberFormatException e) {
            	throw new InvalidDateString("date format is wrong. date:" + date);
            }
            break;

        case DateUtil.YMD_FORMAT:
            try {
                Integer.parseInt(date.substring(0, 3));
                Integer.parseInt(date.substring(5, 6));
                Integer.parseInt(date.substring(8, 9));
            } catch (NumberFormatException e) {
                throw new InvalidDateString("date format is wrong. date:" + date);
            }
            break;
        
        case DateUtil.YMDHM_FORMAT:
        	
            try {
                Integer.parseInt(date.substring(0, 3));
                Integer.parseInt(date.substring(5, 6));
                Integer.parseInt(date.substring(8, 9));
                Integer.parseInt(date.substring(11, 12));
                Integer.parseInt(date.substring(14, 15));
            } catch (NumberFormatException e) {
            	throw new InvalidDateString("date format is wrong. date:" + date);
            }
            
            break;
            
        case DateUtil.YMDHMS_FORMAT:
        	
            try {
                Integer.parseInt(date.substring(0, 3));
                Integer.parseInt(date.substring(5, 6));
                Integer.parseInt(date.substring(8, 9));
                Integer.parseInt(date.substring(11, 12));
                Integer.parseInt(date.substring(14, 15));
                Integer.parseInt(date.substring(17, 18));
            } catch (NumberFormatException e) {
            	throw new InvalidDateString("date format is wrong. date:" + date);
            }
            break;

        default:
            throw new InvalidDateString("Unknown Date Format");
        }//switch
    }
}
