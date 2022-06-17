package com.example.biometricthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTActivity extends AppCompatActivity {

    private TextView tvJWT;
    private TextView tvJWS;
    private Button btnAtras;

    private String SECRET = "DelapatriaelaltonombreengloriosoesplendorconservemosyensusarasdenuevojuremosmMorirantesqueesclavosvivir";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jwtactivity);

        tvJWT = (TextView) findViewById(R.id.btnJWT);
        tvJWS = (TextView) findViewById(R.id.tvJWS);
        btnAtras = (Button) findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JWTActivity.this, BiometricsActivity.class);
                startActivity(i);
            }
        });

        //Generacion de JSOn Web Token
        String jwt = crearJWT("mouse","JC", "cliente",300000);
        Log.d("JWT", jwt);

        leerJWT(jwt);



    }

    private void leerJWT(String jwt){
        Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();

        Log.d("JWT", "Subject: " + claims.getSubject());
        Log.d("JWT", "Issuer: " + claims.getIssuer());
        Log.d("JWT", "Level: " + claims.get("lvl"));
        Log.d("JWT", "Exp: " + claims.getExpiration());

        String a = String.valueOf(claims.getSubject().toString());

        /*String subject =claims.getSubject();
        String issuer =claims.getIssuer();
        String lvl =claims.get("lvl").toString();
        String expiration =claims.getExpiration().toString();

        String leidoFinal = subject + ", " + issuer + ", " + lvl + ", " + expiration;*/

        //tvJWT.setText(a);
    }

    /**
     * @param issuer Proveedor que emitio el JWT
     * @param subject Usuario en nombre del cual fue emitido el JWT
     * @param level Nivel del usuario
     * @param ttlMillis Tiempo de vida del token en milisegundos
     * @return String JWT
     */
    public String crearJWT(String issuer, String subject, String level, long ttlMillis) {

        Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        //Momento de creación del token
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //Tiempo de expiración
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        //header
        Map<String,Object> header = new HashMap();
        header.put("typ", "JWT");

        String jws = Jwts.builder()
                .setHeaderParams(header)
                //claim personalizado
                .claim("lvl",level)
                //claim standar
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(exp)
                .setIssuedAt(now)
                //firma
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
        tvJWS.setText(jws.toString());
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println(jws.toString());
        return jws;
    }
}