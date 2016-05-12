package Runners;

import com.pholser.junit.quickcheck.generator.Generator;
import context.Context;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.pholser.junit.quickcheck.runner.QuickCheckStatement;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark on 07.04.2016.
 */
public class QuickCheckRunner extends JUnitQuickcheck {

    private List<QTestEntry> qtests = new ArrayList<>();
    /**
     * Invoked reflectively by JUnit.
     *
     * @param clazz class containing properties to verify
     * @throws InitializationError if there is a problem with the properties class
     */
    public QuickCheckRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        Iterable<Generator> gens = getGenerators(clazz);
        if (gens != null) {
            for(Generator<?> gen: gens){
                getRepo().register(gen);
            }
        }
        if(getTests(clazz) != null){
            for(Context context : getTests(clazz)){
                qtests.add(new QTestEntry(new FrameworkMethod(context.getVerifyMethod()), context));
            }
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
            tests.addAll(qtests.stream().map(QTestEntry::getMethod).collect(Collectors.toList()));
        }
        return tests;

    }

    @Override
    public Statement methodBlock(FrameworkMethod method) {
        for(QTestEntry entry: qtests){
            if(entry.getMethod().equals(method)){
                return new QuickCheckStatement(method, getTestClass(), getRepo(), getDistro(), getSeedLog(), entry.context);
            }
        }
        return super.methodBlock(method);
    }


    private List<Context> getTests(Class<?> clazz){
        try {
            Field tests = clazz.getDeclaredField("tests");
            tests.setAccessible(true);
            return (List<Context>)tests.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Generator> getGenerators(Class<?> clazz){
        try {
            Field tests = clazz.getDeclaredField("gens");
            tests.setAccessible(true);
            return (List<Generator>)tests.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class QTestEntry{
        private final FrameworkMethod method;
        private final Context context;

        public QTestEntry(FrameworkMethod method, Context context) {
            this.method = method;
            this.context = context;
        }

        public FrameworkMethod getMethod() {
            return method;
        }

        public Context getContext() {
            return context;
        }

    }

}
