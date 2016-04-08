package Utils;

import java.lang.reflect.Method;

/**
 * Created by Mark on 08.04.2016.
 */
public class MethodNameHelper {
    public static Method getMethod(String name) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //caller always is third on stack (getStachTrace() -> run() -> caller
        if (stackTraceElements.length < 3){
            return null;
        }
        String fileName = stackTraceElements[2].getClassName();

        try {
            Class<?> act = Class.forName(fileName);
            //TODO - call other, then string
            return act.getMethod(name, String.class);
        } catch (ClassNotFoundException e) {
            System.out.println("Can not get caller class");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
