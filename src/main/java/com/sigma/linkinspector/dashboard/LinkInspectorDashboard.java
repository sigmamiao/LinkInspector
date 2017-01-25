package com.sigma.linkinspector.dashboard;

import com.beust.jcommander.internal.Lists;
import com.sigma.linkinspector.test.WebLinkTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 17:31
 */
public class LinkInspectorDashboard {


    private static final Logger LOGGER = LoggerFactory.getLogger(LinkInspectorDashboard.class);

    public static void main(String[] args) {

        LOGGER.info("======开始执行测试======");
        XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setName("LinkInspector");
        XmlTest xmlTest = new XmlTest(xmlSuite);
        List<XmlClass> classes = Lists.newArrayList();
        xmlTest.setXmlClasses(classes);
        classes.add(new XmlClass(WebLinkTest.class));
        TestNG testNG = new TestNG(false);
        List<XmlSuite> suites = Lists.newArrayList();
        suites.add(xmlSuite);
        testNG.setXmlSuites(suites);
        testNG.run();

    }
}
