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
public class LatencyTest {

    @Autowired
    private org.springframework.web.context.WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void retrieveDurationLatencyTest() throws Exception {
    	
    	//분단위 검색
        this.mockMvc.perform(get("/rest/durlatency/search")
                .param("sdatetime", "2014-06-15 00:00")
                .param("edatetime", "2014-06-15 23:59")
                .param("logType", "tomcat")
                .param("service", "all")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        ;
    	
        
        //일단위 검색
        this.mockMvc.perform(get("/rest/durlatency/search")
                .param("sdatetime", "2014-06-14 00:00")
                .param("edatetime", "2014-06-15 23:59")
                .param("logType", "tomcat")
                .param("service", "all")
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
        ;
    	

    }
}
