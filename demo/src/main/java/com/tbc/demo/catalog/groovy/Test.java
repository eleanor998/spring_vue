package com.tbc.demo.catalog.groovy;

public class Test {
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
    private static String script1 = "package com.tbc.demo.catalog.groovy\n" +
            "\n" +
            "import com.tbc.demo.catalog.asynchronization.model.User\n" +
            "\n" +
            "class GroovyServiceImpl implements GroovyService {\n" +
            "\n" +
            "    User getUser(String name, String age) {\n" +
            "        def user = new User()\n" +
            "        user.setAge(age as Integer)\n" +
            "        user.setUserName(name)\n" +
            "        return user\n" +
            "\n" +
            "    }\n" +
            "}\n";

    public static void main(String[] args) throws Exception {
        //直接执行
        String run = GroovyUtils.run(script, "hello", "这个是入参");
        System.out.println(run);

        //转成实体
        GroovyService groovyService = GroovyUtils.parseToClass(script1);
        System.out.println(groovyService.getUser("张三", "18"));
    }
}
