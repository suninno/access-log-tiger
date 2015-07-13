package com.lge.cto.swinfra.tiger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:root-context.xml")
@EnableWebMvc
public class RestUriDailyTest {

    @Autowired
    private org.springframework.web.context.WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void retrieveRESTApiSummaryDailyTest() throws Exception {

        this.mockMvc.perform(get("/rest/daily/restapisummary")
                .param("date", "2014-06-30")
                .param("limitCnt", "5")
                .param("logType", "tomcat")
                .param("service", "jira3")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void retrieveHttpStatusDailyTest() throws Exception {

        this.mockMvc.perform(get("/rest/daily/httpstatus")
                .param("date", "2014-06-01")
                .param("logType", "tomcat")
                .param("service", "jira3")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void retrieveLatencyDailyTest() throws Exception {

        this.mockMvc.perform(get("/rest/daily/latency")
                .param("date", "2014-06-30")
                .param("logType", "tomcat")
                .param("service", "jira_tv")
                .accept(MediaType.APPLICATION_JSON)
                //				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }
}
