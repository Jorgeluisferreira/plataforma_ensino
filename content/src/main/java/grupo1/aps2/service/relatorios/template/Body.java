package grupo1.aps2.service.relatorios.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Body {
    private List<BodyItem> conteudo;
    private String titulo;
    private String originalTimestamp;

        public Stream<BodyItem> getConteudoStream() {
        return conteudo != null ? conteudo.stream() : Stream.empty();
    }
}
