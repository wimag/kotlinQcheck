package Utils;

import java.lang.reflect.Method;

/**
 * Created by Mark on 08.04.2016.
 */
public class MethodNameHelper {
    public static Method getMethod(String name, Class<?>... params) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //caller always is third on stack (getStachTrace() -> run() -> caller
        if (stackTraceElements.length < 3){
            return null;
        }
        String fileName = stackTraceElements[2].getClassName();

        try {
            Class<?> act = Class.forName(fileName);
            //TODO - call other, then string
            for(Method method: act.getDeclaredMethods()){
                if(method.getName().equals(name)){
                    return method;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Can not get caller class");
        }
        return null;
    }
}
