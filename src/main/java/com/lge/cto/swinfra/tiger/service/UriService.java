package com.lge.cto.swinfra.tiger.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lge.cto.swinfra.tiger.dao.UriDao;
import com.lge.cto.swinfra.tiger.domain.AvgRestofUriDto;
import com.lge.cto.swinfra.tiger.domain.HttpStatusDto;
import com.lge.cto.swinfra.tiger.domain.LatencyDto;
import com.lge.cto.swinfra.tiger.domain.ShortPeriodDataofUriDto;
import com.lge.cto.swinfra.tiger.domain.UriHistoryDto;
import com.lge.cto.swinfra.tiger.domain.UriPathDto;
import com.lge.cto.swinfra.tiger.util.DateUtil;
import com.lge.cto.swinfra.tiger.vo.AvgRestofUriVO;

/**
 * URI 관련 서비스
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Service
@Transactional(readOnly = true)
public class UriService extends ServiceRoot {

    @Autowired
    private UriDao uriDao;

    /**
     * 일자별 REST API 통계
     * @param searchDate	검색 일자
     * @param limitCnt		호출된 횟수 최소치
     * @param logType		apache or tomcat
     * @param service		jira1,..., collab1
     * @return
     * @throws ParseException
     */
    public List<UriPathDto> retrieveRESTApiSummaryDaily(String searchDate, String limitCnt,
            String logType, String service) throws ParseException {

        DateUtil.isValidDateParameters(searchDate, DateUtil.YMD_FORMAT);

        Map<String, String> params = newHashMap();

        params.put("date", searchDate);
        params.put("limitCnt", limitCnt);
        params.put("logType", logType);

        if (service.compareToIgnoreCase("all") != 0)
            params.put("service", service);

        return uriDao.retrieveRESTApiSummaryDaily(params);
    }

    public List<HttpStatusDto> retreiveHttpStatusDaily(String searchDate, String logType,
            String service) throws ParseException {

        DateUtil.isValidDateParameters(searchDate, DateUtil.YMD_FORMAT);

        Map<String, String> params = newHashMap();
        params.put("date", searchDate);
        params.put("logType", logType);

        if (service.compareToIgnoreCase("all") != 0)
            params.put("service", service);

        return uriDao.retreiveHttpStatusDaily(params);
    }

    /**
     * 
     * 시간 간격
     * 0~1초
     * 1~2초
     * 2~3초
     * ...
     * 10초~120초
     * 120초~
     * @param searchDate
     * @return
     * @throws ParseException
     */
    public List<LatencyDto> retrieveLatencyDaily(final String searchDate, final String logType,
            final String service) throws ParseException {

        List<LatencyDto> result = new ArrayList<LatencyDto>();

        class thresholdResT {
            public String minResT;
            public String maxResT;

            public thresholdResT(String minResT, String maxResT) {
                this.minResT = minResT;
                this.maxResT = maxResT;
            }

            public Map<String, String> makeParams() throws ParseException {
                Map<String, String> params = newHashMap();
                params.put("date", DateUtil.date2str(DateUtil.str2Date(searchDate)));
                params.put("minResT", String.valueOf(this.minResT));
                params.put("maxResT", String.valueOf(this.maxResT));

                params.put("logType", logType);

                if (service.compareToIgnoreCase("all") != 0)
                    params.put("service", service);

                return params;
            }
        }

        DateUtil.isValidDateParameters(searchDate, DateUtil.YMD_FORMAT);

        result.add(new LatencyDto("", "100000",
                uriDao.retrieveLatencyDailyMin((new thresholdResT("", "100000").makeParams()))));

        List<thresholdResT> list = new ArrayList<thresholdResT>();

        list.add(new thresholdResT("100001", "200000"));
        list.add(new thresholdResT("200001", "300000"));
        list.add(new thresholdResT("300001", "400000"));
        list.add(new thresholdResT("400001", "500000"));
        list.add(new thresholdResT("500001", "600000"));
        list.add(new thresholdResT("600001", "700000"));
        list.add(new thresholdResT("700001", "800000"));
        list.add(new thresholdResT("800001", "900000"));
        list.add(new thresholdResT("900001", "1000000"));

        list.add(new thresholdResT("1000001", "2000000"));
        list.add(new thresholdResT("2000001", "3000000"));
        list.add(new thresholdResT("3000001", "4000000"));
        list.add(new thresholdResT("4000001", "5000000"));
        list.add(new thresholdResT("5000001", "6000000"));
        list.add(new thresholdResT("6000001", "7000000"));
        list.add(new thresholdResT("7000001", "8000000"));
        list.add(new thresholdResT("8000001", "9000000"));
        list.add(new thresholdResT("9000001", "10000000"));
        list.add(new thresholdResT("10000001", "120000000"));

        for (thresholdResT e : list) {
            result.add(new LatencyDto(e.minResT, e.maxResT, uriDao.retrieveLatencyDaily(e
                    .makeParams())));
        }

        result.add(new LatencyDto("120000000", "",
                uriDao.retrieveLatencyDailyMax((new thresholdResT("120000000", "").makeParams()))));

        return result;
    }

    /**
     * y축의 데이터가 없더라도, 
     * 그래프의 x축 데이터를 [00:00:00] ~ [23:59:00] 에서 1초 단위로 생성한다.
     * @param date		검색일자	required
     * @param uripath	uripath	optional
     * @param logType	logtype	required
     * @param service	서비스명칭	optional
     * @return
     * @throws ParseException
     */
    public List<AvgRestofUriDto> retrieveAvgRestofUri(String date, String uripath,
            String logType, String service
            ) throws ParseException {
        DateUtil.isValidDateParameters(date, DateUtil.YMD_FORMAT);

        Map<String, String> params = newHashMap();
        params.put("date", date);

        if (uripath != null && uripath.length() > 0 )
        	params.put("uripath", uripath);
        
        params.put("logType", logType); //required

        if (service.compareToIgnoreCase("all") != 0) //optional
            params.put("service", service);

        return uriDao.retrieveAvgRestofUri(params);
    }

    /**
     * 특정 기간 동안의 임의 URI Path에 대한 평균 지연과 호출 횟수
     * - 검색 기간이 1일 이하 일 경우, 분단위로 집계
     * - 30일 이하일 경우, 시간 단위로 집계
     * - 30일을 넘길 경우, 일 단위로 집계
     * @param sdatetime	검색 시작 일시 (주의, 분 단위. 예, yyyy-mm-dd HH:MI)
     * @param edatetime	검색 종료 일시 (주의, 분 단위. 예, yyyy-mm-dd HH:MI)
     * @param uripath	검색 대상 uripath
     * @param logType	logType
     * @param service	각 서비스 명칭
     * @return list of ShortPeriodDataofUriDto
     * @throws ParseException
     */
    public List<ShortPeriodDataofUriDto> retrieveShortPeriodDataofUri(
            String sdatetime,
            String edatetime,
            String uripath,
            String logType,
            String service
            ) throws ParseException {
    	
    	final SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Map<String, String> params = newHashMap();
        params.put("start", sdatetime);
        params.put("end", edatetime);
        params.put("uripath", uripath + "%");
        params.put("logType", logType); //required

        if (service.compareToIgnoreCase("all") != 0) //optional
            params.put("service", service);

        
        List<ShortPeriodDataofUriDto> res = null;
        double diffDays = DateUtil.getDiffDays( DateUtil.getDateFromDateFormat(sdatetime, ymdhm), 
        		DateUtil.getDateFromDateFormat(edatetime, ymdhm));

        if (diffDays > 30) {
        	res = uriDao.retrieveShortPeriodDataofUriPerDay(params);
        } else if (diffDays > 1) {
        	res = uriDao.retrieveShortPeriodDataofUriPerHour(params);
        } else {
        	res = uriDao.retrieveShortPeriodDataofUriPerMinute(params);
        }
        
        return res;
    }

    
    /**
     * searchDate와 uri에 해당하는 데이터를 분 단위 집계를 구한다.
     * 또한, searchDate를 기준으로 하루 전과 1주일 전 데이터를 구하여, 이 셋을 비교할 수 있게 한다. 
     * @param searchDate	검색 대상 일
     * @param uripath		검색 대상 uri
     * @param logType		logType (apache, tomcat)
     * @param service		service name (e.g., collab1, jira1, crowd...)
     * @return The List of UriHistoryDtos
     * @throws ParseException 
     */
    public List<UriHistoryDto> retrieveUriHistory(String searchDate, String uripath, 
    		String logType, String service) throws ParseException {
    	
    	Map<String, String> params = newHashMap();
    	params.put("sdate", searchDate + " 00:00");
    	params.put("edate", searchDate + " 23:59");
    	params.put("uripath", uripath +"%");
    	params.put("logType", logType);
    	
        if (service.compareToIgnoreCase("all") != 0) //optional
            params.put("service", service);
    	
        Map<String, UriHistoryDto> res = newTreeMap();
    	
    	//1. 검색 대상 일
    	List<AvgRestofUriVO> list = uriDao.retrieveUriHistory(params);
    	for(AvgRestofUriVO e : list){
			res.put(e.getCalledTime(), 
					new UriHistoryDto.Builder(e.getCalledTime())
					.curRowCnt(e.getCount())
					.curAvg((long)e.getAvgRest()).build() );
    	}

    	
    	//2. 검색 대상 -1일
    	String prev = DateUtil.date2str(DateUtil.addDays(DateUtil.str2Date(searchDate), -1));
    	params.put("sdate", prev + " 00:00");
    	params.put("edate", prev + " 23:59");
    	list = uriDao.retrieveUriHistory(params);
 
    	for(AvgRestofUriVO e : list){
    		UriHistoryDto dto = res.get(e.getCalledTime());
    		if(dto == null){
    			dto = new UriHistoryDto.Builder(e.getCalledTime()).build();
    			res.put(e.getCalledTime(), dto);
    		}
    		
    		dto.setYesterDayRowCnt(e.getCount());
    		dto.setYesterDayAvg((long)e.getAvgRest());
    	}
    	
    	
    	//3. 검색 대상 -7 일
    	prev = DateUtil.date2str(DateUtil.addDays(DateUtil.str2Date(searchDate), -7));
    	params.put("sdate", prev + " 00:00");
    	params.put("edate", prev + " 23:59");
    	
    	list = uriDao.retrieveUriHistory(params);
 
    	for(AvgRestofUriVO e : list){
    		UriHistoryDto dto = res.get(e.getCalledTime());
    		if(dto == null){
    			dto = new UriHistoryDto.Builder(e.getCalledTime()).build();
    			res.put(e.getCalledTime(), dto);
    		}
    		
    		dto.setaWeekAgoRowCnt(e.getCount());
    		dto.setaWeekAgoAvg((long)e.getAvgRest());
    	}

    	return new ArrayList<UriHistoryDto>(res.values());
    }
}
