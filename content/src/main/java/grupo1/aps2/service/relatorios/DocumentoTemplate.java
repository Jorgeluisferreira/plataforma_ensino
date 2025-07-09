package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.relatorios.template.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class DocumentoTemplate {

    public Header getHeader() {
        Header hd = new Header();
        hd.setDataHora(getDocumentGenerationTimestamp());
        hd.setTitulo("Areas Academy - ");
        return hd;
    }

    public Title getTitle() {
        return new Title(getDocumentTitle());
    }

    public Body getBody() {
        Body bd = new Body();
        bd.setConteudo(getBodyContent() != null ? getBodyContent() : List.of());
        bd.setTitulo(getBodyTitle() != null ? getBodyTitle() : "");
        bd.setOriginalTimestamp(getDocumentOriginalTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return bd;
    }

    public Footer getFooter() {
        Footer f = new Footer();
        f.setExpirationDate(getDocumentExpirationTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        f.setRodape("© 2025 Areas Academy");
        return f;
    }

    public abstract UsuarioDTO getUsuario();
    protected abstract String getDocumentTitle();
    protected abstract LocalDateTime getDocumentGenerationTimestamp();
    protected abstract LocalDateTime getDocumentOriginalTimestamp();
    protected abstract LocalDateTime getDocumentExpirationTimestamp();
    protected abstract List<BodyItem> getBodyContent();
    protected abstract String getBodyTitle();

    public abstract boolean isValid();
    public abstract void setUsuario(UsuarioDTO usuario);
    public abstract void setBodyContent(List<BodyItem> bodyContent);
}
