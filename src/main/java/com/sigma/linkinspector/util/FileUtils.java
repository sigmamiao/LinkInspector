package com.sigma.linkinspector.util;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IDEA
 * User: Omega
 * Date: 2017/1/25
 * Time: 16:39
 */
public class FileUtils {

    public static String getConfigFilePath(String filename) {
        assert filename.endsWith(".json");
        File currentDir = new File(System.getProperty("user.dir"));
        return currentDir + File.separator + "config" + File.separator + filename;
    }

    public static String readFile(String path) {
        File file = new File(path);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeToJsonReport(Set<String> set) {
        Iterator<String> iterator = set.iterator();
        File file = new File(System.getProperty("user.dir") + File.separator + "deadlink.json");
        try (FileWriter fw = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fw)) {
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeToHtmlReportHasProblem(Set<String> set) {
        try (PrintStream printStream = new PrintStream(new FileOutputStream("report.html"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("<!doctype html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>LinkChecker</title>");
            sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            sb.append("<style>");
            sb.append("</style></head>");
            sb.append("<body bgcolor=\"#FFF8DC\">");
            sb.append("<div>");
            sb.append("<p>异常链接如下</p>");
            sb.append("<br/>");
            sb.append("<ul>");
            for (String string : set) {
                sb.append("<li>" + string + "</li>");
            }
            sb.append("</ul>");
            sb.append("</div></body></html>");
            printStream.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeToHtmlReportNoProblem() {
        try (PrintStream printStream = new PrintStream(new FileOutputStream("report.html"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("<!doctype html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>LinkChecker</title>");
            sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            sb.append("<style>ul{text-align:center;list-style-type:none;}");
            sb.append("</style></head>");
            sb.append("<body bgcolor=\"#FFF8DC\">");
            sb.append("<div align=\"center\">");
            sb.append("<p>无异常链接</p>");
            sb.append("<br/>");
            sb.append("</div></body></html>");
            printStream.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
