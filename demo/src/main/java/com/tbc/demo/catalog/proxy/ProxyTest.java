package com.tbc.demo.catalog.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 三种常规代理演示
 */
public class ProxyTest {
    public static void main(String[] args) {
        //静态代理演示
        UserService userService = new UserImpl();
        UserService staticProxy = new StaticProxy(userService);
        staticProxy.save("静态代理");
        //JDK动态代理
        DynamicProxy dynamicProxy = new DynamicProxy();
        UserService dynamicuser = dynamicProxy.newProxyInstance(userService);
        dynamicuser.save("JDK动态代理");
        //CGLib 动态代理
        CGlibProxyUser cGlibProxyUser = new CGlibProxyUser();
        User cglibUser = cGlibProxyUser.newProxyInstance(new User());
        cglibUser.save("cglib动态 代理");
    }
}

interface UserService {
    void save(String name);
}

//静态代码开始
class UserImpl implements UserService {

    @Override
    public void save(String name) {
        System.out.println("保存成功" + name);
    }
}

class StaticProxy implements UserService {
    private UserService userService;

    public StaticProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void save(String name) {
        System.out.println("静态代理开始");
        userService.save("张三");
        System.out.println("静态代理结束");
    }
}
//静态代理代码结束


//JDK 动态代理开始
class DynamicProxy implements InvocationHandler {
    private Object targetObject;

    //获取代理理对象
    public <T> T newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        //绑定关系，也就是和具体的哪个实现类关联
        return (T) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy  被代理理的对象
     * @param method 要调⽤用的⽅方法
     * @param args   传参
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("save")) {
            System.out.println("JDK动态代理开始");
            method.invoke(targetObject, args);
            System.out.println("JDK动态代理结束");
        }
        return null;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

}
//JDK 动态代理结束


class CGlibProxyUser implements MethodInterceptor {

    private Object targetObject;

    //获取代理理对象,绑定代理
    public <T> T newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        return (T) Enhancer.create(targetObject.getClass(), this);
    }

    /**
     * 重写需要代理的代理方法
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invoke = null;
        if (method.getName().equals("save")) {
            System.out.println("CGLib 动态代理开始");
            invoke = methodProxy.invokeSuper(new User(), objects);
            System.out.println("CGLib 动态代理结束");
        }

        return invoke;
    }


}

class User {
    public void save(String userName) {
        System.out.println("CGlib 动态代理" + userName);
    }
}

