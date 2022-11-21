package com.tbc.demo.catalog.maven;

import com.tbc.demo.catalog.extends_test.B;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MavenInstallLocalhost {
    /**
     * maven 依赖
     */
    private static final String DEPENDENCY = "<!-- https://mvnrepository.com/artifact/com.gitee.jd-platform-opensource/asyncTool -->\n" +
            "<dependency>\n" +
            "    <groupId>com.gitee.jd-platform-opensource</groupId>\n" +
            "    <artifactId>asyncTool</artifactId>\n" +
            "    <version>V1.4-22388ae880-1</version>\n" +
            "</dependency>\n";

    /**
     * 本地jar路径
     */
    private static final String JAR_PATH = "C:\\Users\\Andy Zhang\\Desktop\\asyncTool-V1.4-22388ae880-1.jar";


    public static void main(String[] args) throws IOException, InterruptedException {
        String dependencyCmd = getDependencyCmd(DEPENDENCY, JAR_PATH);
        System.out.println(dependencyCmd);
    }


    private static String getDependencyCmd(String dependency, String path) {
        if (StringUtils.isEmpty(dependency) || StringUtils.isEmpty(path)) {
            return "";
        }
        Matcher VERSION_REGEX = Pattern.compile(Constant.VERSION_REGEX).matcher(dependency);
        Matcher GROUP_ID_REGEX = Pattern.compile(Constant.GROUP_ID_REGEX).matcher(dependency);
        Matcher ARTIFACT_ID_REGEX = Pattern.compile(Constant.ARTIFACT_ID_REGEX).matcher(dependency);
        String version = null;
        String groupId = null;
        String artifactId = null;
        while (VERSION_REGEX.find()) {
            version = VERSION_REGEX.group().replaceAll("\\s", "");
        }
        while (GROUP_ID_REGEX.find()) {
            groupId = GROUP_ID_REGEX.group().replaceAll("\\s", "");
        }
        while (ARTIFACT_ID_REGEX.find()) {
            artifactId = ARTIFACT_ID_REGEX.group().replaceAll("\\s", "");
        }
        return Constant.CMD.replace(Constant.ARTIFACT_ID, artifactId).replace(Constant.VERSION, version).replace(Constant.GROUP_ID, groupId).replace(Constant.JAR_PATH, path);
    }
}
