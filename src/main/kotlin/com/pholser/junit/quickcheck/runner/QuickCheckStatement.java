package com.pholser.junit.quickcheck.runner;

import com.pholser.junit.quickcheck.internal.GeometricDistribution;
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

/**
 * Created by Mark on 08.04.2016.
 */

public class QuickCheckStatement extends PropertyStatement {

    public QuickCheckStatement(FrameworkMethod method, TestClass testClass, GeneratorRepository repo, GeometricDistribution distro, Logger seedLog) {
        super(method, testClass, repo, distro, seedLog);
    }

    @Override
    public List<PropertyParameterGenerationContext> parameters(int trials) {
        Map<String, Type> typeVariables = GenericsResolver.resolve(method.getDeclaringClass())
                .method(method.getMethod())
                .genericsMap();

        return Arrays.stream(method.getMethod().getParameters())
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