package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.dto.DTODocumento;
import grupo1.aps2.dto.DTODocumento.DocumentoDTO;
import grupo1.aps2.service.relatorios.DocumentoTemplate;
import grupo1.aps2.service.relatorios.template.*;
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

        html.append("<body style='font-family:Arial, sans-serif; margin:20px;'>");
        html.append(genHeader(doc));
        html.append(genBody(doc));
        html.append(genFooter(doc));
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    private String genHeader(DocumentoTemplate doc) {
        Header header = doc.getHeader();
        Title title = doc.getTitle();
        String titulo = title.getTitulo() != null ? title.getTitulo() : "";
        String dataHora = header != null && header.getDataHora() != null
                ? header.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "";

        return "<head>"
                + "<meta charset=\"UTF-8\"/>"
                + "<h2>" + titulo + "</h2>"
                + "</head>"
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
                .append("<p><b>Nome: </b>").append(doc.getUsuario().getNome())
                .append("<br><b>Função: </b>").append(doc.getUsuario().getRole())
                .append("<br><b>Data: </b>").append(doc.getBody().getOriginalTimestamp()).append("</p>")
                .append("</div>");
        }

        Body itemBody = doc.getBody();
        if (itemBody != null) {
            body.append("<div><strong>NOME</strong> DESCRIÇÃO <em>(ESTADO)</em></div>");
            body.append("<ul>");
            for (BodyItem item : itemBody.getConteudo()) {
                body.append("<li>")
                        .append("<strong>")
                        .append(item.getNome())
                        .append("</strong> ")
                        .append(item.getDescricao());
                if (item.getEstadoItem() != null && !item.getEstadoItem().isBlank()) {
                    body.append(" <em>(")
                            .append(item.getEstadoItem())
                            .append(")</em>");
                }
                body.append("</li>");
            }
            body.append("</ul>");
        }
        return body.toString();
    }

    private String genFooter(DocumentoTemplate doc) {
        Footer footer = doc.getFooter();
        if (footer == null) return "";
        return "<div style='margin-top:2em;font-size:small;color:gray;'>"
                + (footer.getRodape() != null ? footer.getRodape() : "")
                + " - Valido até: " + (footer.getExpirationDate() != null? footer.getExpirationDate() : "")
                + "</div>";
    }
}
