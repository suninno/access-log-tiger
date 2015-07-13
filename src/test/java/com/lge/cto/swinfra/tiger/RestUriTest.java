package com.lge.cto.swinfra.tiger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:root-context.xml")
public class RestUriTest {

    @Autowired
    private org.springframework.web.context.WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void retrieveAvgRestofUriTest() throws Exception {

        this.mockMvc.perform(get("/rest/uri/avgRest")
                .param("date", "2014-06-30")
                .param("uripath", "/qi/rest/api/2/search")
                .param("logType", "tomcat")
                .param("service", "jira_tv")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))

        ;

    }

    
    //분단위
    @Test
    public void retrieveShotPeriodDataofUriTestPerMinute() throws Exception {

        this.mockMvc.perform(get("/rest/uri/averageLatencyForPeriod")
                .param("sdatetime", "2014-06-06 11:00")
                .param("edatetime", "2014-06-06 12:00")
                .param("uripath", "/qi/rest/api/2/search")
                .param("logType", "tomcat")
                .param("service", "jira_tv")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }


    //시간단위
    @Test
    public void retrieveShotPeriodDataofUriTestPerHour() throws Exception {

        this.mockMvc.perform(get("/rest/uri/averageLatencyForPeriod")
                .param("sdatetime", "2014-06-06 11:00")
                .param("edatetime", "2014-06-07 12:00")
                .param("uripath", "/qi/rest/api/2/search")
                .param("logType", "tomcat")
                .param("service", "jira_tv")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    
    //일단위
    @Test
    public void retrieveShotPeriodDataofUriTestPerDay() throws Exception {

        this.mockMvc.perform(get("/rest/uri/averageLatencyForPeriod")
                .param("sdatetime", "2014-04-06 11:00")
                .param("edatetime", "2014-06-07 12:00")
                .param("uripath", "/qi/rest/api/2/search")
                .param("logType", "tomcat")
                .param("service", "jira_tv")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }
    
    
    @Test
    public void retrieveUriHistory() throws Exception {
    	
    	this.mockMvc.perform( get("/rest/uri/urihistory")
    		.param("searchdate", "2014-06-30")
    		.param("uripath", "/crowd/rest/usermanagement/1/session")
            .param("logType", "tomcat")
            .param("service", "all")
    		.accept(MediaType.APPLICATION_JSON)
    		)
    		.andDo(print())
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));

    }
}
