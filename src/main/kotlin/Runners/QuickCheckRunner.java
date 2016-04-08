package Runners;

import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.runner.QuickCheckStatement;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 07.04.2016.
 */
public class QuickCheckRunner extends JUnitQuickcheck {

    private List<FrameworkMethod> qtests = new ArrayList<>();
    /**
     * Invoked reflectively by JUnit.
     *
     * @param clazz class containing properties to verify
     * @throws InitializationError if there is a problem with the properties class
     */
    public QuickCheckRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        try {
            System.out.println(clazz.getDeclaredField("tests").get(null));
            for(Method method: (List<Method>)clazz.getDeclaredField("tests").get(null)){
                qtests.add(new FrameworkMethod(method));
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        super.validateTestMethods(errors);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
         List<FrameworkMethod> tests = super.computeTestMethods();
        if(qtests != null){
            tests.addAll(qtests);
        }
        return tests;

    }

    @Override
    public Statement methodBlock(FrameworkMethod method) {
        if(qtests.contains(method)){
            return new QuickCheckStatement(method, getTestClass(), getRepo(), getDistro(), getSeedLog());
        }
        return super.methodBlock(method);
    }


}
