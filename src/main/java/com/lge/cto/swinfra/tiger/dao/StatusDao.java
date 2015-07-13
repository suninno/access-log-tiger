package com.lge.cto.swinfra.tiger.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lge.cto.swinfra.tiger.vo.StatusVO;

/**
 * 기간 지연 관련 DAO
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16
 */
@Repository
@Transactional(readOnly = true)
public class StatusDao {

    private final String namespace = "com.lge.cto.swinfra.tiger.status";

    @Autowired
    SqlSession sqlSession;

    public List<StatusVO> retrieveStatuaData() {
    	return sqlSession.selectList(String.format("%s.retrieveStatusData", namespace));
    }
    
}
