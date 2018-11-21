package com.redcapd.usermanager.entity;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.Key;


public class User {
    private int userId;



    private String username;
    private String password;
    private String salt;
    private String email;
    private int language_id;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //
    // Metodo di autenticazione
    //
    public String authenticate(String password){
        String hash = BCrypt.hashpw(password,this.getSalt());
        if(this.getSalt().equals(hash)){
            return generateToken();
        }
        return null;
    }


    //
    // Metodo di creazione del token
    //
    public String generateToken(){
        Key key = new AesKey("AltissimoLivello".getBytes());
        JwtClaims claims = new JwtClaims();
        claims.setClaim("userid",this.getUserId());
        claims.setClaim("username",this.getUsername());
        claims.setClaim("userrole","user");
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(claims.toJson());
        jwe.setCertificateChainHeaderValue();
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        try{
            String serializedJwe = jwe.getCompactSerialization();
            return serializedJwe;
        }
        catch (JoseException e){
            e.printStackTrace();
            return null;
        }
    }
}
