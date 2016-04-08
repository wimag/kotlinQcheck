package Runners;

import Utils.MethodNameHelper;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;


import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Mark on 07.04.2016.
 */

@RunWith(QuickCheckRunner.class)
public class QuickCheckBuilder {

    public static final ArrayList<Method> tests = new ArrayList<>();

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
        System.out.println("asd");
        byte[] ciphertext = crypto.encrypt(plaintext.getBytes());
        Assert.assertEquals(
                plaintext,
                new String(crypto.decrypt(ciphertext)));
    }


}
