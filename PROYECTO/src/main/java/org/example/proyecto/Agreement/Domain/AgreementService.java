package org.example.proyecto.Agreement.Domain;


import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;

import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.Item.Domain.Item;

import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Item.dto.ItemResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.exception.ResourceNotFoundException;
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
                .map(agreement -> {
                    AgreementResponseDto responseDto = modelMapper.map(agreement, AgreementResponseDto.class);

                    // Seteamos manualmente las propiedades adicionales
                    responseDto.setItemFinName(agreement.getItem_fin().getName());  // Nombre del item final
                    responseDto.setItemIniName(agreement.getItem_ini().getName());  // Nombre del item inicial
                    responseDto.setUserNameFin(agreement.getRecipient().getEmail());  // Email del receptor
                    responseDto.setUserNameIni(agreement.getInitiator().getEmail());  // Email del iniciador

                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    public AgreementResponseDto createAgreement(AgreementRequestDto agreementRequestDto) {
        Agreement agreement = new Agreement();

        // Cargar los objetos Item y Usuario utilizando sus identificadores
        Item itemIni = itemRepository.findById(agreementRequestDto.getItemIniId())
                .orElseThrow(() -> new ResourceNotFoundException("Item inicial no encontrado"));

        Item itemFin = itemRepository.findById(agreementRequestDto.getItemFinId())
                .orElseThrow(() -> new ResourceNotFoundException("Item final no encontrado"));

        Usuario usuarioIni = usuarioRepository.findById(agreementRequestDto.getUsuarioIniId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario iniciador no encontrado"));

        Usuario usuarioFin = usuarioRepository.findById(agreementRequestDto.getUsuarioFinId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario receptor no encontrado"));

        // Asignar los objetos cargados al acuerdo
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(usuarioIni);
        agreement.setRecipient(usuarioFin);

        // Asignar el estado
        agreement.setStatus(agreementRequestDto.getStatus());

        // Guardar el acuerdo en la base de datos
        Agreement savedAgreement = agreementRepository.save(agreement);

        // Crear y retornar el DTO manualmente asignando los IDs
        AgreementResponseDto responseDto = new AgreementResponseDto();

        modelMapper.map(savedAgreement, responseDto);
        responseDto.setItemFinName(itemFin.getName());
        responseDto.setItemIniName(itemIni.getName());
        responseDto.setUserNameFin(usuarioFin.getEmail());
        responseDto.setUserNameIni(usuarioIni.getEmail());
        return responseDto;
    }

    public AgreementResponseDto getAgreementById(Long id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        // Crear y retornar el DTO manualmente asignando los IDs
        AgreementResponseDto responseDto = new AgreementResponseDto();

        modelMapper.map(agreement, responseDto);
        responseDto.setItemFinName(agreement.getItem_fin().getName());
        responseDto.setItemIniName(agreement.getItem_ini().getName());
        responseDto.setUserNameFin(agreement.getRecipient().getEmail());
        responseDto.setUserNameIni(agreement.getInitiator().getEmail());
        return responseDto;
    }

    public AgreementResponseDto updateAgreement(Long id, AgreementRequestDto agreementRequestDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        AgreementResponseDto responseDto = new AgreementResponseDto();

        modelMapper.map(existingAgreement, responseDto);
        responseDto.setItemFinName(existingAgreement.getItem_fin().getName());
        responseDto.setItemIniName(existingAgreement.getItem_ini().getName());
        responseDto.setUserNameFin(existingAgreement.getRecipient().getEmail());
        responseDto.setUserNameIni(existingAgreement.getInitiator().getEmail());
        return responseDto;
    }

    public void deleteAgreement(Long id) {
        agreementRepository.deleteById(id);
    }

}
