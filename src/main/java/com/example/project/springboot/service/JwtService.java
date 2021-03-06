package com.example.project.springboot.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@NoArgsConstructor
public class JwtService {
  final static String KEY = "mySecretKey";
  final static String ISSUER = "issuer";
  final static String SUBJECT = "subject";

  //generate jwt
  public String createJWT(String id, String issuer, String subject, String ttlmls) {

    Long ttlMillis = new Long(ttlmls);
    //The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //We will sign our JWT with our ApiKey secret
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder().setId(id)
        .setIssuedAt(now)
        .setSubject(subject)
        .setIssuer(issuer)
        .signWith(signatureAlgorithm, signingKey);

    //if it has been specified, let's add the expiration
    if (ttlMillis >= 0) {
      long expMillis = nowMillis + ttlMillis;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }

    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }


  //validate jwt
  //@Handler
  public void validate(String token) throws Exception {
    if (token == null || !token.startsWith("Bearer ") || StringUtils.isEmpty(token)) {
      throw new ServletException("Missing or invalid Authorization header");
    }

    final String jwt = token.substring(7);
    Claims claims = Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(KEY))
        .parseClaimsJws(jwt).getBody();
    if (!claims.getIssuer().equalsIgnoreCase(ISSUER) || !claims.getSubject().equalsIgnoreCase(SUBJECT)) {
      throw new IllegalAccessException("Not a valid user.");
      //throw not authorised exeption
    }
  }
}
