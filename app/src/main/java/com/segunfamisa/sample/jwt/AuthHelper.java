package com.segunfamisa.sample.jwt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.auth0.jwt.JWTVerifier;
import com.segunfamisa.sample.jwt.network.Token;

import java.util.Map;

public class AuthHelper {
    /**
     * Secret used to verify the JWT token
     */
    private static final String SECRET= "ngEurope rocks!";

    /**
     * Key for username in the jwt claim
     */
    private static final String JWT_KEY_USERNAME = "username";

    private static final String PREFS = "prefs";
    private static final String PREF_TOKEN = "pref_token";
    private SharedPreferences mPrefs;

    private static AuthHelper sInstance;

    private AuthHelper(@NonNull Context context) {
        mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sInstance = this;
    }

    public static AuthHelper getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AuthHelper(context);
        }
        return sInstance;
    }

    public void setIdToken(@NonNull Token token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_TOKEN, token.getIdToken());
        editor.apply();
    }

    @Nullable
    public String getIdToken() {
        return mPrefs.getString(PREF_TOKEN, null);
    }

    public boolean isLoggedIn() {
        String token = getIdToken();
        return token != null;
    }

    /**
     * Gets the username of the signed in user
     * @return - username of the signed in user
     */
    public String getUsername() {
        if (isLoggedIn()) {
            return decodeUsername(getIdToken());
        }
        return null;
    }

    @Nullable
    private String decodeUsername(String token) {
        JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            Map<String, Object> claims = verifier.verify(token);
            if (claims != null && claims.containsKey(JWT_KEY_USERNAME)) {
                return claims.get(JWT_KEY_USERNAME).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
