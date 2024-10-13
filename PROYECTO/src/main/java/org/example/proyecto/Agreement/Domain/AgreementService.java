package org.example.proyecto.Agreement.Domain;


import jakarta.transaction.Transactional;
import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;

import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.Item.Domain.Item;

import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Shipment.Domain.ShipmentService;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.event.agreement.AgreementCreadoEvent;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AgreementService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


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

    @Transactional
    public AgreementResponseDto createAgreement(AgreementRequestDto agreementRequestDto) {

        if (agreementRequestDto.getStatus() != Status.PENDING) {
            throw new IllegalArgumentException("No se puede crear un acuerdo directamente en estado ACCEPTED o REJECTED");
        }

        Agreement agreement = new Agreement();


        Item itemIni = itemRepository.findById(agreementRequestDto.getItemIniId())
                .orElseThrow(() -> new ResourceNotFoundException("Item inicial no encontrado"));

        Item itemFin = itemRepository.findById(agreementRequestDto.getItemFinId())
                .orElseThrow(() -> new ResourceNotFoundException("Item final no encontrado"));

        Usuario usuarioIni = usuarioRepository.findById(agreementRequestDto.getUsuarioIniId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario iniciador no encontrado"));

        Usuario usuarioFin = usuarioRepository.findById(agreementRequestDto.getUsuarioFinId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario receptor no encontrado"));


        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(usuarioIni);
        agreement.setRecipient(usuarioFin);


        agreement.setStatus(agreementRequestDto.getStatus());


        Agreement savedAgreement = agreementRepository.save(agreement);


        eventPublisher.publishEvent(new AgreementCreadoEvent(this, savedAgreement));


        AgreementResponseDto responseDto = modelMapper.map(savedAgreement, AgreementResponseDto.class);
        responseDto.setItemFinName(itemFin.getName());
        responseDto.setItemIniName(itemIni.getName());
        responseDto.setUserNameFin(usuarioFin.getEmail());
        responseDto.setUserNameIni(usuarioIni.getEmail());

        return responseDto;
    }

    public AgreementResponseDto getAgreementById(Long id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));


        AgreementResponseDto responseDto = new AgreementResponseDto();

        modelMapper.map(agreement, responseDto);
        responseDto.setItemFinName(agreement.getItem_fin().getName());
        responseDto.setItemIniName(agreement.getItem_ini().getName());
        responseDto.setUserNameFin(agreement.getRecipient().getEmail());
        responseDto.setUserNameIni(agreement.getInitiator().getEmail());
        return responseDto;
    }

    @Transactional
    public AgreementResponseDto acceptAgreement(Long id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        // Verificamos que el estado actual sea PENDING
        if (agreement.getStatus() != Status.PENDING) {
            throw new IllegalArgumentException("Solo los acuerdos en estado PENDING pueden ser aceptados");
        }

        // Cambiamos el estado a ACCEPTED
        agreement.setStatus(Status.ACCEPTED);

        // Guardamos el acuerdo actualizado en la base de datos
        Agreement savedAgreement = agreementRepository.save(agreement);

        // Creamos el Shipment asociado al acuerdo aceptado
        shipmentService.createShipmentForAgreement(savedAgreement);

        // Publicamos el evento de creación del acuerdo
        eventPublisher.publishEvent(new AgreementCreadoEvent(this, savedAgreement));

        // Retornamos el response DTO
        AgreementResponseDto responseDto = modelMapper.map(savedAgreement, AgreementResponseDto.class);
        responseDto.setItemFinName(savedAgreement.getItem_fin().getName());
        responseDto.setItemIniName(savedAgreement.getItem_ini().getName());
        responseDto.setUserNameFin(savedAgreement.getRecipient().getEmail());
        responseDto.setUserNameIni(savedAgreement.getInitiator().getEmail());

        return responseDto;
    }

    @Transactional
    public AgreementResponseDto rejectAgreement(Long id) {
        Agreement agreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        // Verificamos que el estado actual sea PENDING
        if (agreement.getStatus() != Status.PENDING) {
            throw new IllegalArgumentException("Solo los acuerdos en estado PENDING pueden ser rechazados");
        }

        // Cambiamos el estado a REJECTED
        agreement.setStatus(Status.REJECTED);

        // Guardamos el acuerdo actualizado en la base de datos
        Agreement savedAgreement = agreementRepository.save(agreement);



        // Retornamos el response DTO
        AgreementResponseDto responseDto = modelMapper.map(savedAgreement, AgreementResponseDto.class);
        responseDto.setItemFinName(savedAgreement.getItem_fin().getName());
        responseDto.setItemIniName(savedAgreement.getItem_ini().getName());
        responseDto.setUserNameFin(savedAgreement.getRecipient().getEmail());
        responseDto.setUserNameIni(savedAgreement.getInitiator().getEmail());

        return responseDto;
    }

    @Transactional
    public AgreementResponseDto updateAgreement(Long id, AgreementRequestDto agreementRequestDto) {
        Agreement existingAgreement = agreementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agreement not found"));

        if (existingAgreement.getStatus() == Status.REJECTED || existingAgreement.getStatus() == Status.ACCEPTED) {
            throw new IllegalArgumentException("No se puede cambiar el estado de un acuerdo que ya ha sido ACCEPTED o REJECTED");
        }
//sdfdsfds

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
