package annotations;

import Runners.QuickCheckBuilder;
import Runners.TestStorage;
import generators.QGenerator;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            processQGen(act);
            processAnnotations(act);
            runTests();
        } catch (ClassNotFoundException e) {
            System.out.println("Can not get caller class");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void processQGen(Class<?> act){
        for(Method method: getMethods(act)){
            if(method.isAnnotationPresent(QGen.class)){
                String name = method.getAnnotation(QGen.class).name();
                if(name.equals("N/A")){
                    name = method.getName();
                }
                TestStorage.getInstance().addGen(new QGenerator(method, name));
            }
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
                }
            }
        }
    }

    private static void runTests(){
        Result res = JUnitCore.runClasses(QuickCheckBuilder.class);
        System.out.printf("%d Tests Evaluated \n", res.getRunCount() - 1);
        if(res.getFailureCount() == 0){
            System.out.println("All Tests Passed");
            return;
        }
        System.err.printf("%d Tests Failed \n", res.getFailureCount());
    }

    private static Method[] getMethods(Class<?> act) {
        //TODO - recursive method extraction for nested/inner classes
        return act.getMethods();
    }
}
