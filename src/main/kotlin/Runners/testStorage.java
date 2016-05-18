package Runners;

import com.pholser.junit.quickcheck.generator.Generator;
import context.Context;

import java.util.ArrayList;

/**
 * Created by Mark on 18.05.2016.
 */
public class TestStorage {
    private static final Storage instance = new Storage();

    public TestStorage(){}

    public static Storage getInstance(){
        return instance;
    }

    public static class Storage{
        private final ArrayList<Context> tests = new ArrayList<>();

        private final ArrayList<Generator> gens = new ArrayList<>();

        public void addGen(Generator generator){
            gens.add(generator);
        }

        public void addTest(Context context){
            tests.add(context);
        }

        public ArrayList<Context> getTests(){
            return tests;
        }

        public ArrayList<Generator> getGenerators() {
            return gens;
        }
    }
}
