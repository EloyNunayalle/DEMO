    package org.example.proyecto.Agreement.Dto;

    import lombok.Getter;
    import lombok.Setter;
    import org.example.proyecto.Agreement.Domain.State;


    @Getter
    @Setter
    public class AgreementResponseDto {
        private Long id;
        private State state;
        private String itemIniName;
        private String itemFinName;
        private String userNameIni;
        private String userNameFin;
    }