package grupo1.aps2.service.relatorios;

import grupo1.aps2.dto.DTOUsuario.UsuarioDTO;
import grupo1.aps2.service.relatorios.template.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class DocumentoTemplate {

    public Header getHeader() {
        Header hd = new Header();
        hd.setDataHora(getDocumentTimestamp());
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
        return bd;
    }

    public Footer getFooter() {
        return new Footer("© 2025 Areas Academy");
    }

    public abstract UsuarioDTO getUsuario();
    protected abstract String getDocumentTitle();
    protected abstract LocalDateTime getDocumentTimestamp();
    protected abstract List<BodyItem> getBodyContent();
    protected abstract String getBodyTitle();

    public abstract boolean isValid();
}
