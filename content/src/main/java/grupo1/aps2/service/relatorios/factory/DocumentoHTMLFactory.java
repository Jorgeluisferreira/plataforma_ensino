package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.dto.DTODocumento;
import grupo1.aps2.dto.DTODocumento.DocumentoDTO;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import grupo1.aps2.service.relatorios.template.Body;
import grupo1.aps2.service.relatorios.template.BodyItem;
import grupo1.aps2.service.relatorios.template.Footer;
import grupo1.aps2.service.relatorios.template.Header;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class DocumentoHTMLFactory implements DocumentoFactory {


    @Override
    public DocumentoDTO create(DocumentoTemplate doc) throws WebApplicationException {
        if (doc == null) {
            throw new WebApplicationException("DocumentoTemplate não pode ser nulo", 400);
        }
        if (!doc.isValid()) {
            throw new WebApplicationException("DocumentoTemplate inválido", 400);
        }

        String htmlString = generateHtml(doc);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(htmlString.getBytes(StandardCharsets.UTF_8));
            baos.flush();
            return new DocumentoDTO(baos, getMediaType());
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar HTML: " + e.getMessage(), 500);
        }
    }

    @Override
    public String getFileFormatAbreviation() {
        return "html";
    }

    @Override
    public String getMediaType() {
        return "text/html";
    }

    public String generateHtml(DocumentoTemplate doc) throws WebApplicationException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang=\"pt-BR\">");

        html.append(genHeader(doc));
        html.append(genBody(doc));
        html.append(genFooter(doc));
        html.append("</html>");

        return html.toString();
    }

    private String genHeader(DocumentoTemplate doc) {
        Header header = doc.getHeader();
        String titulo = header != null && header.getTitulo() != null ? header.getTitulo() : "";
        String dataHora = header != null && header.getDataHora() != null
                ? header.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";

        return "<meta charset=\"UTF-8\"/>"
                + "<title>" + titulo + "</title>"
                + "<style>body { font-family: Arial, sans-serif; }</style>"
                + "<div style='font-size:small;color:gray;'>Gerado em: " + dataHora + "</div>";

    }

    private String genTitle(DocumentoTemplate doc) {
        String title = doc.getTitle().getTitulo() != null ? doc.getTitle().getTitulo() : "";
        return "<h1>" + title + "</h1>";
    }

    private String genBody(DocumentoTemplate doc) {
        StringBuilder body = new StringBuilder();

        if (doc.getUsuario() != null) {

            body.append("<div>")
                .append("<h4>Documento referente à: </h4>")
                .append("<p>Nome: ").append(doc.getUsuario().getNome())
                .append("<br>Função: ").append(doc.getUsuario().getRole()).append("</p>")
                .append("</div>");
        }

        Body itemBody = doc.getBody();
        if (itemBody != null) {
            for (BodyItem item : itemBody.getConteudo()) {
                body.append("<div><strong>")
                        .append(item.getNome())
                        .append("</strong> ")
                        .append(item.getDescricao());
                if (item.getEstadoItem() != null && !item.getEstadoItem().isBlank()) {
                    body.append(" <em>(")
                            .append(item.getEstadoItem())
                            .append(")</em></div>");
                }
            }
        }
        return body.toString();
    }

    private String genFooter(DocumentoTemplate doc) {
        Footer footer = doc.getFooter();
        return "<footer style='margin-top:2em;font-size:small;color:gray;'>"
                + (footer != null && footer.getRodape() != null ? footer.getRodape() : "")
                + "</footer>";
    }
}
