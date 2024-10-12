    package org.example.proyecto.Agreement.Dto;

    import lombok.Getter;
    import lombok.Setter;
    import org.example.proyecto.Agreement.Domain.Agreement;

    import java.time.LocalDateTime;


    @Getter
    @Setter
    public class AgreementResponseDto {
        private Long id;
        private Agreement.Status status;
        private LocalDateTime tradeDate;
        private Long itemIniId;
        private Long itemFinId;
        private Long usuarioIniId;
        private Long usuarioFinId;
    }