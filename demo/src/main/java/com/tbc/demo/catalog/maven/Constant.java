package com.tbc.demo.catalog.maven;

public interface Constant {


    /**
     * 公司idz=正则
     */
    String GROUP_ID_REGEX = "(?<=<groupId>).*(?=<\\/groupId>)";


    /**
     * 公司id
     */
    String GROUP_ID = "GROUP_ID";
    /**
     * jar Id regex
     */
    String ARTIFACT_ID_REGEX = "(?<=<artifactId>).*(?=<\\/artifactId>)";

    /**
     * jar Id
     */
    String ARTIFACT_ID = "ARTIFACT_ID";

    /**
     * version regex
     */
    String VERSION_REGEX = "(?<=<version>).*(?=<\\/version>)";

    /**
     * version
     */
    String VERSION = "VERSION";

    /**
     * version
     */
    String JAR_PATH = "JAR_PATH";


    /**
     * 命令
     */
    String CMD = "mvn install:install-file \"-Dfile=" + JAR_PATH + "\" \"-DgroupId=" + GROUP_ID + "\" \"-DartifactId=" + ARTIFACT_ID + "\"  \"-Dversion=" + VERSION + "\"  \"-Dpackaging=jar\"\n";


}
