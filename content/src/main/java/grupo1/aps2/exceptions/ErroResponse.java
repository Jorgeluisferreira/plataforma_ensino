package grupo1.aps2.exceptions;

import java.util.Collection;

public record ErroResponse(String mensagem, Collection<String> erros) {
}
