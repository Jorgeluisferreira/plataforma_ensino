package grupo1.aps2.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtService {

    // ⚠️ ATENÇÃO: A chave secreta precisa ter no mínimo 32 bytes para HS256
    private static final String SECRET_KEY = "sua-chave-super-secreta-com-no-minimo-32-bytes";

    private final Key key;

    public JwtService() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Valida o token JWT e retorna as claims contidas nele.
     *
     * @param token JWT recebido no header Authorization
     * @return Claims (conteúdo do token)
     * @throws RuntimeException se o token for inválido ou expirado
     */
    public Claims validarTokenERetornarClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }
}
