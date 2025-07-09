package grupo1.aps2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayOutputStream;

public class DTODocumento {

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public static class DocumentoDTO {
        ByteArrayOutputStream baos;
        String mediaType;
    }
}
