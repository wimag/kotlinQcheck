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

    private static final ArrayList<Context> tests = new ArrayList<>();

    private static final ArrayList<Generator> gens = new ArrayList<>();

    public static void addGen(Generator generator){
        gens.add(generator);
    }

    public static void addTest(Context context){
        tests.add(context);
    }

    private static class Crypto2 {
        byte[] encrypt(byte[] plaintext){

            return plaintext;
        }

        byte[] decrypt(byte[] ciphertext){
            return ciphertext;
        }
    }


    @Property
    public void decryptReversesEncrypt(String plaintext) {

        Crypto2 crypto = new Crypto2();
        byte[] ciphertext = crypto.encrypt(plaintext.getBytes());
        Assert.assertEquals(
                plaintext,
                new String(crypto.decrypt(ciphertext)));
    }


}
