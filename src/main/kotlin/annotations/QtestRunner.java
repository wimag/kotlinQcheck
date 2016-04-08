package annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ognl.OgnlException;
/**
 * Created by Mark on 07.04.2016.
 */
public class QtestRunner {
    public static void run(){

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //caller always is third on stack (getStachTrace() -> run() -> caller
        if (stackTraceElements.length < 3){
            return;
        }
        String fileName = stackTraceElements[2].getClassName();
        try {
            Class<?> act = Class.forName(fileName);
            processAnnotations(act);
        } catch (ClassNotFoundException e) {
            System.out.println("Can not get caller class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void processAnnotations(Class<?> act) throws IllegalAccessException {
        Method methods[] = getMethods(act);
        for(Method method: methods){
            if(method.isAnnotationPresent(QTest.class)){
                System.out.printf("Running test %s\n", method.getName());
                try {
                    method.invoke(null);
                } catch (IllegalAccessException e) {
                    System.out.printf("Cannot access test method %s\n", method.getName());
                    throw e;
                } catch (InvocationTargetException e) {
                    System.out.println("Test FAILED:");
                    e.printStackTrace();
                    continue;
                }
                System.out.println("Test PASSED");
            }
        }
    }

    private static Method[] getMethods(Class<?> act) {
        //TODO - recursive method extraction for nested/inner classes
        return act.getMethods();
    }
}
