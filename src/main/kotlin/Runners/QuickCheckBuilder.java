package Runners;

import com.pholser.junit.quickcheck.generator.Generator;
import context.Context;
import com.pholser.junit.quickcheck.Property;
import org.junit.Assert;
import org.junit.runner.RunWith;


import java.util.ArrayList;

/**
 * Created by Mark on 07.04.2016.
 */

@RunWith(QuickCheckRunner.class)
public class QuickCheckBuilder {
    @Property
    public void proxy(String ignored) {}
}
