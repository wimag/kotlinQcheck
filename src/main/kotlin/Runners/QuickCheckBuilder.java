package Runners;

import com.pholser.junit.quickcheck.Property;
import org.junit.runner.RunWith;

/**
 * Created by Mark on 07.04.2016.
 */

@RunWith(QuickCheckRunner.class)
public class QuickCheckBuilder {
    @Property
    public void proxy(String ignored) {}
}
