package org.example.proyecto.Agreement.Application;

import org.example.proyecto.Agreement.Domain.AgreementService;
import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agreements")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @GetMapping
    public List<AgreementResponseDto> getAllAgreements() {
        return agreementService.getAllAgreements();
    }

    @PostMapping
    public AgreementResponseDto createAgreement(@Valid @RequestBody AgreementRequestDto agreementRequestDto) {
        return agreementService.createAgreement(agreementRequestDto);
    }

    @GetMapping("/{id}")
    public AgreementResponseDto getAgreementById(@PathVariable Long id) {
        return agreementService.getAgreementById(id);
    }

    @PutMapping("/{id}")
    public AgreementResponseDto updateAgreement(@PathVariable Long id, @Valid @RequestBody AgreementRequestDto agreementRequestDto) {
        return agreementService.updateAgreement(id, agreementRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAgreement(@PathVariable Long id) {
        agreementService.deleteAgreement(id);
    }
}