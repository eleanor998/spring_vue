package com.tbc.demo.catalog.maven;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MavenUtils {
    /**
     * maven 依赖
     */
    private static final String DEPENDENCY = "<!-- https://mvnrepository.com/artifact/cn.hutool/hutool-all -->\n" +
            "<dependency>\n" +
            "    <groupId>cn.hutool</groupId>\n" +
            "    <artifactId>hutool-all</artifactId>\n" +
            "    <version>5.8.10</version>\n" +
            "</dependency>\n";

    /**
     * 本地jar路径
     */
    private static final String JAR_PATH = "C:\\Users\\Andy Zhang\\Desktop\\hutool-all-5.8.10.jar";


    public static void main(String[] args) throws IOException, InterruptedException {
        String dependencyCmd = getDependencyCmd(DEPENDENCY, JAR_PATH);
        System.out.println(dependencyCmd);
    }

    /**
     * 打包到本地
     *
     * @param dependency maven xml 依赖
     * @param path       本地jar路径
     * @return
     */
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
