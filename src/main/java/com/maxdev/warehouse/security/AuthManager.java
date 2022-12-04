package com.maxdev.warehouse.security;

import com.google.common.hash.Hashing;
import com.maxdev.warehouse.models.Credential;
import com.maxdev.warehouse.models.Usercard;
import com.maxdev.warehouse.repo.CredentialsRepository;
import com.maxdev.warehouse.repo.UsercardsRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.websocket.AuthenticationException;
import org.jasypt.salt.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

@Component(value="Manager")
public class AuthManager {
    @Autowired
    CredentialsRepository cr;
    @Autowired
    UsercardsRepository ucr;
    public Random rnd;
    private StandardPBEStringEncryptor enc;
    private Cipher cipher;
    private SecretKeySpec key;
    private IvParameterSpec ivps;
    private Charset utf = StandardCharsets.ISO_8859_1;
    public void init(){
        rnd = new Random();
        enc = new StandardPBEStringEncryptor();
        enc.setPassword(cr.findById(1).get().getEmail());
        SaltGenerator sg = new SaltGenerator() {
            @Override
            public byte[] generateSalt(int lengthBytes) {
                return new byte[lengthBytes];
            }

            @Override
            public boolean includePlainSaltInEncryptionResults() {
                return false;
            }
        };
        enc.setSaltGenerator(sg);
        enc.initialize();

//        try {
//            cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            key = new SecretKeySpec(cr.findById(1).get().getEmail().getBytes(StandardCharsets.UTF_8),"AES");
//            ivps = new IvParameterSpec(new byte[16]);
////            String m = cr.findById(9).get().getEmail();
////            m = decrypt(m,key);
////            System.out.println(m);
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException /*| IllegalBlockSizeException | BadPaddingException | InvalidKeyException*/ e) {
//            e.printStackTrace();
//        }


    }


    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);

    }

    public String hash(String unhashed, String salt, short saltmode){
        String prepared;
        int saltlen = salt.length();
        switch (saltmode){
            case 1:
                prepared = salt.substring(0,saltlen/2) + salt.substring(0,saltlen/2) +
                        unhashed + salt.substring(saltlen/2) + salt.substring(saltlen/2);
                break;
            default:
                prepared = salt+unhashed+salt;
                break;
        }
        return Hashing.sha256().hashString(prepared, StandardCharsets.UTF_8).toString();
    }
    public String encrypt(String source, Key key) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE,key, ivps);
            byte[] sb = new byte[64];
            for(int i = 0; i<source.length(); ++i){
                sb[i]= (byte) source.charAt(i);
            }
            sb =  cipher.doFinal(sb);
            System.out.println(sb);
            return new String(sb,0,64,utf);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException  e) {
            e.printStackTrace();
        }
        return null;
    }
    public String decrypt(String code, Key key) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE,key,ivps);
            return new String(cipher.doFinal(code.getBytes(utf)), utf).trim();
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean validPwd(String unhashed){

        if (unhashed.length()<4)
            return false;
        char c;
        boolean[] terms = new boolean[3];
        /*
        hasLower
        hasUpper
        hasNumber
         */
        for(int i = 0; i < unhashed.length(); ++i){
            c = unhashed.charAt(i);
            if(c>='a'&&c<='z')
                terms[0] = true;
            else if (c>='A'&&c<='Z')
                terms[1] = true;
            else if (c>='0'&&c<='9')
                terms[2] = true;
            else
                return false;
        }
        short s = 0;
        for (boolean term : terms) {
            System.out.println(term);
            if (term)
                ++s;
        }
        return s >= 2;

    }

    public Authentication login(Authentication oldVar) throws AuthenticationException {
        Credential c = cr.findCredentialByEmail(enc.encrypt(oldVar.getLogin()));
        if(c==null) throw new AuthenticationException("Incorrect login") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };

        if(!c.getPwd().equals(hash(oldVar.getPwd(),c.getSalt(),c.getSaltmode() )))
            throw new AuthenticationException("Incorrect pwd") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };
        System.out.println(c.getEmail()+" just logged in!");
        return new Authentication(oldVar.getLogin(),oldVar.getPwd()).setValidWithRoles(c.getUsercard().getRoles());
    }
    @Transactional
    public Authentication signup(Authentication oldVar) throws AuthenticationException{
        Credential c = cr.findCredentialByEmail(enc.encrypt(oldVar.getLogin()));
        if(c!=null) throw new AuthenticationException("User with this email already exists") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };
        if(!validPwd(oldVar.getPwd()))
            throw new AuthenticationException("Invalid pwd format") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        Usercard uc = new Usercard();
//        uc.setFirstname(null);
//        uc.setLastname(null);
//        uc.setBirthday(null);
        uc.setRoles("ABCD");
        System.out.println(ucr.findById(1).get().getLastname());
        ucr.saveAndFlush(uc);
        c = new Credential();
        c.setEmail(enc.encrypt(oldVar.getLogin()));
        c.setSaltmode((short) 0);
        c.setSalt(generateString(rnd,"~!@#$%^&*()_+`1234567890-=[]{};:,./<>?",8));
        c.setPwd(hash(oldVar.getPwd(),c.getSalt(),c.getSaltmode()));
        c.setUsercard(uc);
        cr.save(c);
        return new Authentication(oldVar.getLogin(), oldVar.getPwd()).setValidWithRoles("AB--");
    }
}
