package com.tbc.demo.catalog.groovy;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.google.common.cache.*;
import com.tbc.demo.utils.MD5Generator;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.apache.tomcat.util.security.MD5Encoder;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.codehaus.groovy.syntax.Types.KEYWORD_GOTO;
import static org.codehaus.groovy.syntax.Types.KEYWORD_WHILE;

public class GroovyUtils {
    private static GroovyClassLoader groovyClassLoader;
    private static LoadingCache<String, Class> loadingCache;


    static {
        createGroovyClient();
        createGuavaCache();
    }

    private static String script = "package com.tbc.demo.catalog.groovy\n" +
            "\n" +
            "import com.alibaba.fastjson.JSONObject\n" +
            "import com.tbc.demo.entity.User\n" +
            "\n" +
            "/**\n" +
            " * idea 可直接新建groovy类型文件\n" +
            " */\n" +
            "class Script {\n" +
            "\n" +
            "    static String hello(String args) {\n" +
            "        def user = new User()\n" +
            "        return JSONObject.toJSONString(user) + args\n" +
            "    }\n" +
            "}\n";

    public static void main(String[] args) throws Exception {
        String run = run(script, "hello", "这个是入参");
        System.out.println(run);
    }

    /**
     * 解析成指定类手动执行方法
     *
     * @param script
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T parseToClass(String script) throws Exception {
        Class<T> aClass = parseClass(script);
        return aClass.newInstance();
    }

    /**
     * 执行使用默认类
     *
     * @param script
     * @param method
     * @param param
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T run(String script, String method, Object param) throws Exception {
        Class scriptClass = parseClass(script);
        GroovyObject scriptInstance = (GroovyObject) scriptClass.newInstance();
        return (T) scriptInstance.invokeMethod(method, param);
    }

    /**
     * 初始化数据
     */
    public static void init() {
        if (groovyClassLoader == null) {
            createGroovyClient();
        }
        if (loadingCache == null) {
            createGuavaCache();
        }
    }


    /**
     * 初始化客户端
     *
     * @return
     */
    private static void createGroovyClient() {
        // 创建SecureASTCustomizer
        final SecureASTCustomizer secure = new SecureASTCustomizer();
        // 禁止使用闭包
        secure.setClosuresAllowed(true);
        List<Integer> tokensBlacklist = new ArrayList<>();
        // 添加关键字黑名单 while和goto
        tokensBlacklist.add(KEYWORD_WHILE);
        tokensBlacklist.add(KEYWORD_GOTO);
        secure.setTokensBlacklist(tokensBlacklist);
        secure.setIndirectImportCheckEnabled(true);
        //设置导包黑名单防止导入危险包
        //secure.setImportsBlacklist(list);
        // statement 黑名单，不能使用while循环防止死循环
        List<Class<? extends Statement>> statementBlacklist = new ArrayList<>();
        statementBlacklist.add(WhileStatement.class);
        secure.setStatementsBlacklist(statementBlacklist);
        // 自定义CompilerConfiguration，设置AST
        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(secure);
        groovyClassLoader = new GroovyClassLoader();
        groovyClassLoader.hasCompatibleConfiguration(config);
    }


    /**
     * 创建本地缓存
     *
     * @return
     */
    public static void createGuavaCache() {
        loadingCache = CacheBuilder.newBuilder()
                // 设置并发级别为5，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(5)
                // 设置写缓存后10秒钟后过期
                .expireAfterWrite(1, TimeUnit.HOURS)
                // 设置缓存容器的初始容量为8
                .initialCapacity(30)
                // 设置缓存最大容量为10，超过10之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                // 指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(new CacheLoader<String, Class>() {
                    @Override
                    public Class load(String key) {
                        return null;
                    }
                });
    }

    /**
     * 解析类
     *
     * @param text
     * @return
     * @throws CompilationFailedException
     */
    private static Class parseClass(String text) throws CompilationFailedException {
        String md5Hex = DigestUtil.md5Hex(text.getBytes());
        try {
            return loadingCache.get(md5Hex);
        } catch (Exception e) {
            Class aClass = groovyClassLoader.parseClass(text);
            loadingCache.put(md5Hex, aClass);
            return aClass;
        }
    }
}
