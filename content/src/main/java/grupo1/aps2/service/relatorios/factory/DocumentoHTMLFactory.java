package grupo1.aps2.service.relatorios.factory;

import grupo1.aps2.service.relatorios.Documento;
import jakarta.ws.rs.WebApplicationException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class DocumentoHTMLFactory implements DocumentoFactory {

    @Override
    public ByteArrayOutputStream create(Documento doc) throws WebApplicationException {
        if (doc == null) {
            throw new WebApplicationException("Documento não pode ser nulo", 400);
        }
        if (!doc.isValid()) {
            throw new WebApplicationException("Documento inválido", 400);
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head>")
                .append("<meta charset=\"UTF-8\"/>")
                .append("<title>").append(doc.getTitulo()).append("</title>")
                .append("<style>body { font-family: Arial, sans-serif; }</style>")
                .append("</head><body>");

        html.append("<h1>").append(doc.getTitulo()).append("</h1>");

        if (doc.getUsuario() != null) {
            html.append("<h2>Usuário</h2>")
                    .append("<p>Nome: ").append(doc.getUsuario().getNome()).append("</p>")
                    .append("<p>Função: ").append(doc.getUsuario().getRole()).append("</p>");
        }

        if (doc.getCursos() != null && !doc.getCursos().isEmpty()) {
            html.append("<h2>"+doc.getTituloSessaoCursos()+"</h2><ul>");
            doc.getCursos().forEach(curso -> {
                html.append("<li>")
                        .append(curso.getCurso().getNome())
                        .append(" - ")
                        .append(curso.getCurso().getDescricao())
                        .append(" - ")
                        .append(curso.getStatus())
                        .append("</li>");
            });
            html.append("</ul>");
        }

        html.append("</body></html>");

        String htmlString = html.toString();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(htmlString.getBytes(StandardCharsets.UTF_8));
            baos.flush();
            return baos;
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao gerar HTML: " + e.getMessage(), 500);
        }
    }
}
