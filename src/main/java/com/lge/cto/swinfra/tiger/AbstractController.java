package com.lge.cto.swinfra.tiger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AbstractController {

    protected String getServerTime(Locale locale) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(date);
    }
    
    protected List<String> getLogTypeList() {
        List<String> logTypeList = new ArrayList<String>();
        logTypeList.add("tomcat");
        logTypeList.add("apache");
        return logTypeList;
    }

    protected List<String> getTomcatServiceList() {
        List<String> tomcatServiceList = new ArrayList<String>();
        tomcatServiceList.add("all");
        tomcatServiceList.add("jira1");
        tomcatServiceList.add("jira2");
        tomcatServiceList.add("jira3");
        tomcatServiceList.add("jira4");
        tomcatServiceList.add("jira5");
        tomcatServiceList.add("jira6");
        tomcatServiceList.add("jira7");
        tomcatServiceList.add("jira_tv");
        tomcatServiceList.add("jira_harmony");
        tomcatServiceList.add("collab1");
        tomcatServiceList.add("crowd");
        return tomcatServiceList;
    }
}
