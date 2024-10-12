package org.example.proyecto.Agreement.Domain;

import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;
import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgreementService {

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AgreementResponseDto> getAllAgreements() {
        return agreementRepository.findAll().stream()
                .map(agreement -> modelMapper.map(agreement, AgreementResponseDto.class))
                .collect(Collectors.toList());
    }

    public AgreementResponseDto createAgreement(AgreementRequestDto agreementRequestDto) {
        Agreement agreement = new Agreement();
        mapRequestDtoToAgreement(agreementRequestDto, agreement);
        Agreement savedAgreement = agreementRepository.save(agreement);
        return modelMapper.map(savedAgreement, AgreementResponseDto.class);
    }

    public AgreementResponseDto getAgreementById(int id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found"));
        return modelMapper.map(agreement, AgreementResponseDto.class);
    }

    public AgreementResponseDto updateAgreement(int id, AgreementRequestDto agreementRequestDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found"));
        mapRequestDtoToAgreement(agreementRequestDto, existingAgreement);
        Agreement updatedAgreement = agreementRepository.save(existingAgreement);
        return modelMapper.map(updatedAgreement, AgreementResponseDto.class);
    }

    public void deleteAgreement(int id) {
        agreementRepository.deleteById(id);
    }

    private void mapRequestDtoToAgreement(AgreementRequestDto dto, Agreement agreement) {
        agreement.setStatus(dto.getStatus());
        agreement.setTradeDate(dto.getTradeDate());

    }
}
