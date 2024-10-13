    package org.example.proyecto.Agreement.Dto;

    import lombok.Getter;
    import lombok.Setter;
    import org.example.proyecto.Agreement.Domain.Agreement;
    import org.example.proyecto.Agreement.Domain.Status;

    import java.time.LocalDateTime;


    @Getter
    @Setter
    public class AgreementResponseDto {
        private Long id;
        private Status status;
        private String itemIniName;
        private String itemFinName;
        private String userNameIni;
        private String userNameFin;
    }