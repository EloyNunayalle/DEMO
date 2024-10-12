package org.example.proyecto.Agreement.Domain;


import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;

import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.Item.Domain.Item;

import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgreementService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

        // Cargar los objetos Item y Usuario utilizando sus identificadores
        Item itemIni = itemRepository.findById(agreementRequestDto.getItemIniId())
                .orElseThrow(() -> new RuntimeException("Item inicial no encontrado"));

        Item itemFin = itemRepository.findById(agreementRequestDto.getItemFinId())
                .orElseThrow(() -> new RuntimeException("Item final no encontrado"));

        Usuario usuarioIni = usuarioRepository.findById(agreementRequestDto.getUsuarioIniId())
                .orElseThrow(() -> new RuntimeException("Usuario iniciador no encontrado"));

        Usuario usuarioFin = usuarioRepository.findById(agreementRequestDto.getUsuarioFinId())
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));

        // Asignar los objetos cargados al acuerdo
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(usuarioIni);
        agreement.setRecipient(usuarioFin);

        // Asignar el estado y la fecha de intercambio
        agreement.setStatus(agreementRequestDto.getStatus());
        agreement.setTradeDate(agreementRequestDto.getTradeDate());

        // Guardar el acuerdo en la base de datos
        Agreement savedAgreement = agreementRepository.save(agreement);

        // Crear y retornar el DTO manualmente asignando los IDs
        AgreementResponseDto responseDto = new AgreementResponseDto();
        responseDto.setId(savedAgreement.getId());
        responseDto.setStatus(savedAgreement.getStatus());
        responseDto.setTradeDate(savedAgreement.getTradeDate());
        responseDto.setItemIniId(savedAgreement.getItem_ini().getId());
        responseDto.setItemFinId(savedAgreement.getItem_fin().getId());
        responseDto.setUsuarioIniId(savedAgreement.getInitiator().getId());
        responseDto.setUsuarioFinId(savedAgreement.getRecipient().getId());

        return responseDto;
    }

    public AgreementResponseDto getAgreementById(Long id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found"));

        // Crear y retornar el DTO manualmente asignando los IDs
        AgreementResponseDto responseDto = new AgreementResponseDto();
        responseDto.setId(agreement.getId());
        responseDto.setStatus(agreement.getStatus());
        responseDto.setTradeDate(agreement.getTradeDate());
        responseDto.setItemIniId(agreement.getItem_ini().getId());
        responseDto.setItemFinId(agreement.getItem_fin().getId());
        responseDto.setUsuarioIniId(agreement.getInitiator().getId());
        responseDto.setUsuarioFinId(agreement.getRecipient().getId());

        return responseDto;
    }

    public AgreementResponseDto updateAgreement(Long id, AgreementRequestDto agreementRequestDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found"));
        mapRequestDtoToAgreement(agreementRequestDto, existingAgreement);
        Agreement updatedAgreement = agreementRepository.save(existingAgreement);
        return modelMapper.map(updatedAgreement, AgreementResponseDto.class);
    }

    public void deleteAgreement(Long id) {
        agreementRepository.deleteById(id);
    }

    private void mapRequestDtoToAgreement(AgreementRequestDto dto, Agreement agreement) {
        agreement.setStatus(dto.getStatus());
        agreement.setTradeDate(dto.getTradeDate());

    }
}
