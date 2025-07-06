package grupo1.aps2.security.jwt;

import grupo1.aps2.model.UsuarioEntity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashSet;

/**
 * A utility class to generate and print a JWT token string to stdout.
 */
@ApplicationScoped
public class GenerateJwtToken {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String ISSUER;

    /**
     * Generates and prints a JWT token.
     */
//    public static void main(String[] args) {
//        String token = Jwt.issuer(ISSUER)
//                .upn("jdoe@quarkus.io")
//                .groups(new HashSet<>(Arrays.asList("User", "Admin")))
//                .claim(Claims.birthdate.name(), "2001-07-13")
//                .sign();
//
//        System.out.println(token);
//        System.exit(0);
//    }

    public String generateUserToken(UsuarioEntity usuario) {
        return Jwt.issuer(ISSUER)
                .upn(usuario.getId().toString())
                .claim("id", usuario.getId().toString())
                .claim("nome", usuario.getNome())
                .claim("roles", usuario.getRoles())
                .groups(getUserGroups(usuario))
                .sign();
    }

    private HashSet<String> getUserGroups(UsuarioEntity usuario) {
        HashSet<String> hs = new HashSet<>();
        hs.add(usuario.getRoles());
        return hs;
    }
}
