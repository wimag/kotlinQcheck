package Utils;

import kotlin.Function;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.internal.KFunctionFromReferenceImpl;
import kotlin.reflect.jvm.internal.KFunctionImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * Created by Mark on 17.05.2016.
 * class to work with Function objects.
 * It can be either Lambda, or Function.
 */
public class FunctionHelper {

    /**
     * Reflect call to func. This method is necessary
     * if we want to call kotlin functional interface
     * from java runtime
     *
     * @param func - Functional interface
     * @param args - arguments reflect
     * @return - result of the call
     * @throws UnsupportedOperationException - if this
     *                                       function interface is not supported yet
     */
    @SuppressWarnings("unchecked")
    public static <R> R invoke(Function<R> func, Object... args) {
        try {
            if(func instanceof Lambda){
                return (R)getMethod(func).invoke(func, args);
            }else if(func instanceof KFunction){
                return ((KFunction<R>)func).call(args);
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {}
        return null;
    }

    public static <R> Method getMethod(Function<R> func) {
        if (func instanceof Lambda) {
            for (Method method : func.getClass().getMethods()) {
                if (method.getName().equals("invoke") && Modifier.isFinal(method.getModifiers())) {
                    method.setAccessible(true);
                    return method;
                }
            }
        } else if (func instanceof KFunction) {
            return kotlin.reflect.jvm.ReflectJvmMapping.getJavaMethod((KFunction) func);
        }
        throw new UnsupportedOperationException("This kind of funciton is not sopported yet");
    }

    public static <R> int getArity(Function<R> func){
        if(func instanceof Lambda){
            return ((Lambda)func).getArity();
        }else if(func instanceof KFunction){
            return ((KFunction)func).getParameters().size();
        }
        throw new UnsupportedOperationException("This kind of funciton is not sopported yet");
    }
}
