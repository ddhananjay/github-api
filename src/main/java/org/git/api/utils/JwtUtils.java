package org.git.api.utils;

import org.springframework.beans.factory.annotation.Value;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JwtUtils {

    private static String gitAccessToken ;

    public static String getGitJWTTokenFromFile(String clientId) {
        String jwtToken = null;
        try {
            return refreshJwt(clientId);
          //  Path path = Paths.get("/Users/ddhananjay/git-jwt.txt");
            //jwtToken = new String(Files.readString(path, StandardCharsets.US_ASCII));
            //System.out.println("Jwt: " + jwtToken.trim());
        } catch (Exception e) {
            System.out.println("exception occured while getting JWT Token " + e.getMessage());
        }
        return jwtToken;
    }

    private static String refreshJwt(String clientId) throws Exception {

        Process tokenRefreshProcess = Runtime.getRuntime().exec("/Users/ddhananjay/gitTokenGenerate.sh  /Users/ddhananjay/git.pem");
        int stats = tokenRefreshProcess.waitFor();
        BufferedReader input = new BufferedReader(new InputStreamReader(tokenRefreshProcess.getInputStream()));
        return input.readLine();
    }


}
