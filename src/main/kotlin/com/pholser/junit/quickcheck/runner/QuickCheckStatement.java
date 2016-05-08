package com.pholser.junit.quickcheck.runner;

import context.Context;
import com.pholser.junit.quickcheck.internal.GeometricDistribution;
import com.pholser.junit.quickcheck.internal.ShrinkControl;
import com.pholser.junit.quickcheck.internal.generator.GeneratorRepository;
import com.pholser.junit.quickcheck.internal.generator.PropertyParameterGenerationContext;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import ru.vyarus.java.generics.resolver.GenericsResolver;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.fail;

/**
 * Created by Mark on 08.04.2016.
 */

public class QuickCheckStatement extends PropertyStatement {
    private final Context context;
    public QuickCheckStatement(FrameworkMethod method, TestClass testClass, GeneratorRepository repo, GeometricDistribution distro, Logger seedLog,
                               Context context) {
        super(method, testClass, repo, distro, seedLog);
        this.context = context;
    }

    //TODO - Override verify, so it adds params.


    @Override
    protected void verifyProperty(List<PropertyParameterGenerationContext> params, ShrinkControl shrinkControl) throws Throwable {
        Object[] optArgs = new Object[]{context};
        Object[] testArgs = argumentsFor(params);
        Object[] args = new Object[optArgs.length + testArgs.length];
        System.arraycopy(optArgs, 0, args, 0, optArgs.length);
        System.arraycopy(testArgs, 0, args, optArgs.length, testArgs.length);
        property(params, args, shrinkControl).verify();
    }

    @Override
    public void evaluate() throws Throwable {
        int trials = context.trials();
        ShrinkControl shrinkControl = new ShrinkControl(
                context.shrink(),
                context.maxShrinks(),
                context.maxShrinkDepth(),
                context.maxShrinkTime());

        List<PropertyParameterGenerationContext> params = parameters(trials);

        for (int i = 0; i < trials; ++i)
            verifyProperty(params, shrinkControl);

        if (successes == 0 && !assumptionViolations.isEmpty()) {
            fail("No values satisfied property assumptions. Violated assumptions: "
                    + assumptionViolations);
        }
    }

    @Override
    public List<PropertyParameterGenerationContext> parameters(int trials) {
        Map<String, Type> typeVariables = GenericsResolver.resolve(context.getTestMethod().getDeclaringClass())
                .method(context.getTestMethod())
                .genericsMap();

        return Arrays.stream(context.getTestMethod().getParameters())
                .map(p -> parameterContextFor(p, trials, typeVariables))
                .map(p -> new PropertyParameterGenerationContext(
                        p,
                        repo,
                        distro,
                        new SourceOfRandomness(new Random()),
                        seedLog))
                .collect(toList());
    }

}