package grupo1.aps2.exceptions;

public record MensagemErro(String mensagem) {

    // AUTHORIZATION
    public static String USUARIO_NAO_AUTENTICADO = "Usuário não autorizado";
    public static String USUARIO_NAO_AUTORIZADO = "Usuário não autorizado";
    public static String INVALID_AUTH_HEADER = "Authorization header não encontrado ou inválido";
}
