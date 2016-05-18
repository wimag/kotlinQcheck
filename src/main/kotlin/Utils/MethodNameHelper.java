package Utils;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * Created by Mark on 08.04.2016.
 */
public class MethodNameHelper {
    @Nullable
    public static Method getMethod(String name){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //caller always is third on stack (getStachTrace() -> run() -> caller
        if (stackTraceElements.length < 3){
            return null;
        }
        String fileName = stackTraceElements[2].getClassName();
        Class<?> act = null;
        try {
            act = Class.forName(fileName);
        } catch (ClassNotFoundException e) {
            System.err.println("cant reflect method " + name);
        }
        return getMethod(name, act);
    }

    public static Method getMethod(String name, Class target){
        for(Method method: target.getDeclaredMethods()){
            if(method.getName().equals(name)){
                return method;
            }
        }
        return null;
    }
}
