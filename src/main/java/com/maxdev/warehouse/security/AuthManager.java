package com.maxdev.warehouse.security;

import com.google.common.hash.Hashing;
import com.maxdev.warehouse.models.Credential;
import com.maxdev.warehouse.models.Usercard;
import com.maxdev.warehouse.repo.CredentialsRepository;
import com.maxdev.warehouse.repo.UsercardsRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.websocket.AuthenticationException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Component(value="Manager")
public class AuthManager {
    @Autowired
    CredentialsRepository cr;
    @Autowired
    UsercardsRepository ucr;
    public Random rnd;
    private StandardPBEStringEncryptor enc;
    private HashMap<String, Authentication> sessionsTable;
    private int selfCleanerTimer;

    public void init(){
        selfCleanerTimer = 1023;
        sessionsTable = new HashMap<String, Authentication>();
//        sessionsTable.add();
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

    }
public Authentication checkSession(String remoteAddress){
        if(--selfCleanerTimer==0)
            selfClean();
        return sessionsTable.get(remoteAddress);
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
        if(--selfCleanerTimer==0)
            selfClean();
        Authentication newVar = sessionsTable.get(oldVar.getRemoteAddr());
        if(newVar!=null&& newVar.isActual()) {
            return newVar;
        }
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
        newVar = new Authentication(oldVar.getLogin(), oldVar.getPwd(),oldVar.getRemoteAddr()).setValidWithRoles(c.getUsercard().getRoles());
        sessionsTable.put(newVar.getRemoteAddr(),newVar);
        return newVar;
    }
    @Transactional
    public Authentication signup(Authentication oldVar) throws AuthenticationException{
        if(--selfCleanerTimer==0)
            selfClean();
        Authentication newVar = null;
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
        newVar = new Authentication(oldVar.getLogin(), oldVar.getPwd(), oldVar.getRemoteAddr()).setValidWithRoles("AB--");
        sessionsTable.put(newVar.getRemoteAddr(),newVar);
        return newVar;
    }
    private void selfClean(){
        Iterator<Map.Entry<String, Authentication>> it = sessionsTable.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Authentication> me = (Map.Entry<String,Authentication>) it.next();
            Authentication a = me.getValue();
            if(!a.isActual()){
                it.remove();
            }
        }
        selfCleanerTimer = 1024;
    }
    public String encrypt(String email){
        return enc.encrypt(email);
    }

}
