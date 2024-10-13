package org.example.proyecto.Agreement;

import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Domain.AgreementService;
import org.example.proyecto.Agreement.Domain.State;
import org.example.proyecto.Agreement.Dto.AgreementRequestDto;
import org.example.proyecto.Agreement.Dto.AgreementResponseDto;
import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Item.Infraestructure.ItemRepository;
import org.example.proyecto.Shipment.Domain.ShipmentService;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.event.agreement.AgreementAceptadoEvent;
import org.example.proyecto.event.agreement.AgreementCreadoEvent;
import org.example.proyecto.event.agreement.AgreementRechazadoEvent;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgreementServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ShipmentService shipmentService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AgreementRepository agreementRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AgreementService agreementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAgreement_Success() {
        AgreementRequestDto requestDto = new AgreementRequestDto();
        requestDto.setState(State.PENDING);
        requestDto.setItemIniId(1L);
        requestDto.setItemFinId(2L);
        requestDto.setUsuarioIniId(1L);
        requestDto.setUsuarioFinId(2L);

        Item itemIni = new Item();
        itemIni.setId(1L);
        itemIni.setName("Item Inicial");

        Item itemFin = new Item();
        itemFin.setId(2L);
        itemFin.setName("Item Final");

        Usuario usuarioIni = new Usuario();
        usuarioIni.setId(1L);
        usuarioIni.setEmail("iniciador@example.com");

        Usuario usuarioFin = new Usuario();
        usuarioFin.setId(2L);
        usuarioFin.setEmail("receptor@example.com");

        Agreement agreement = new Agreement();
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(usuarioIni);
        agreement.setRecipient(usuarioFin);
        agreement.setState(State.PENDING);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(itemIni));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(itemFin));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioIni));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuarioFin));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);

        AgreementResponseDto responseDto = new AgreementResponseDto();
        responseDto.setId(1L);
        responseDto.setState(State.PENDING);
        responseDto.setItemIniName("Item Inicial");
        responseDto.setItemFinName("Item Final");
        responseDto.setUserNameIni("iniciador@example.com");
        responseDto.setUserNameFin("receptor@example.com");

        when(modelMapper.map(any(Agreement.class), eq(AgreementResponseDto.class))).thenReturn(responseDto);

        AgreementResponseDto result = agreementService.createAgreement(requestDto);

        assertNotNull(result);
        assertEquals("Item Inicial", result.getItemIniName());
        assertEquals("Item Final", result.getItemFinName());
        assertEquals("iniciador@example.com", result.getUserNameIni());
        assertEquals("receptor@example.com", result.getUserNameFin());

        verify(eventPublisher, times(1)).publishEvent(any(AgreementCreadoEvent.class));
    }

    @Test
    public void testGetAgreementById_Success() {
        // Configuración del Agreement
        Agreement agreement = new Agreement();
        agreement.setId(1L);  // Asegúrate de que el id esté configurado correctamente
        agreement.setState(State.PENDING);

        // Mock Items
        Item itemIni = new Item();
        itemIni.setId(1L);
        itemIni.setName("Item Inicial");

        Item itemFin = new Item();
        itemFin.setId(2L);
        itemFin.setName("Item Final");

        // Mock Usuarios
        Usuario initiator = new Usuario();
        initiator.setId(1L);
        initiator.setEmail("initiator@example.com");

        Usuario recipient = new Usuario();
        recipient.setId(2L);
        recipient.setEmail("recipient@example.com");

        // Asignar Items y Usuarios al Agreement
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(initiator);
        agreement.setRecipient(recipient);

        // Mockear el comportamiento del repository
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));

        // Llamar al servicio
        AgreementResponseDto result = agreementService.getAgreementById(1L);

        // Verificar el resultado esperado
        assertNotNull(result);  // Asegúrate de que no sea null
        assertEquals(1L, result.getId());  // Verificar que el id sea el esperado
        assertEquals(State.PENDING, result.getState());
        assertEquals("Item Inicial", result.getItemIniName());
        assertEquals("Item Final", result.getItemFinName());
        assertEquals("initiator@example.com", result.getUserNameIni());
        assertEquals("recipient@example.com", result.getUserNameFin());

        // Verificar interacciones
        verify(agreementRepository, times(1)).findById(1L);
    }




    @Test
    public void testAcceptAgreement_Success() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreement.setState(State.PENDING);

        // Mock Items
        Item itemIni = new Item();
        itemIni.setId(1L);
        itemIni.setName("Item Inicial");

        Item itemFin = new Item();
        itemFin.setId(2L);
        itemFin.setName("Item Final");

        // Mock Usuarios
        Usuario initiator = new Usuario();
        initiator.setId(1L);
        initiator.setEmail("initiator@example.com");

        Usuario recipient = new Usuario();
        recipient.setId(2L);
        recipient.setEmail("recipient@example.com");

        // Asignar Items y Usuarios al Agreement
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(initiator);
        agreement.setRecipient(recipient);

        AgreementResponseDto responseDto = new AgreementResponseDto();
        responseDto.setId(1L);
        responseDto.setState(State.ACCEPTED);
        responseDto.setItemIniName("Item Inicial");
        responseDto.setItemFinName("Item Final");
        responseDto.setUserNameIni("initiator@example.com");
        responseDto.setUserNameFin("recipient@example.com");

        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);
        when(modelMapper.map(any(Agreement.class), eq(AgreementResponseDto.class))).thenReturn(responseDto);

        AgreementResponseDto result = agreementService.acceptAgreement(1L);

        assertNotNull(result);
        assertEquals(State.ACCEPTED, result.getState());
        assertEquals("Item Inicial", result.getItemIniName());
        assertEquals("Item Final", result.getItemFinName());
        assertEquals("initiator@example.com", result.getUserNameIni());
        assertEquals("recipient@example.com", result.getUserNameFin());

        verify(agreementRepository, times(1)).save(agreement);
        verify(eventPublisher, times(1)).publishEvent(any(AgreementAceptadoEvent.class));
        verify(shipmentService, times(1)).createShipmentForAgreement(agreement);
    }

    @Test
    public void testRejectAgreement_Success() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreement.setState(State.PENDING);

        // Mock Items
        Item itemIni = new Item();
        itemIni.setId(1L);
        itemIni.setName("Item Inicial");

        Item itemFin = new Item();
        itemFin.setId(2L);
        itemFin.setName("Item Final");

        // Mock Usuarios
        Usuario initiator = new Usuario();
        initiator.setId(1L);
        initiator.setEmail("initiator@example.com");

        Usuario recipient = new Usuario();
        recipient.setId(2L);
        recipient.setEmail("recipient@example.com");

        // Asignar Items y Usuarios al Agreement
        agreement.setItem_ini(itemIni);
        agreement.setItem_fin(itemFin);
        agreement.setInitiator(initiator);
        agreement.setRecipient(recipient);

        AgreementResponseDto responseDto = new AgreementResponseDto();
        responseDto.setId(1L);
        responseDto.setState(State.REJECTED);
        responseDto.setItemIniName("Item Inicial");
        responseDto.setItemFinName("Item Final");
        responseDto.setUserNameIni("initiator@example.com");
        responseDto.setUserNameFin("recipient@example.com");

        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));
        when(agreementRepository.save(any(Agreement.class))).thenReturn(agreement);
        when(modelMapper.map(any(Agreement.class), eq(AgreementResponseDto.class))).thenReturn(responseDto);

        AgreementResponseDto result = agreementService.rejectAgreement(1L);

        assertNotNull(result);
        assertEquals(State.REJECTED, result.getState());
        assertEquals("Item Inicial", result.getItemIniName());
        assertEquals("Item Final", result.getItemFinName());
        assertEquals("initiator@example.com", result.getUserNameIni());
        assertEquals("recipient@example.com", result.getUserNameFin());

        verify(agreementRepository, times(1)).save(agreement);
        verify(eventPublisher, times(1)).publishEvent(any(AgreementRechazadoEvent.class));
    }

    @Test
    public void testDeleteAgreement_Success() {
        doNothing().when(agreementRepository).deleteById(1L);

        agreementService.deleteAgreement(1L);

        verify(agreementRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateAgreement_InvalidState() {
        AgreementRequestDto requestDto = new AgreementRequestDto();
        requestDto.setState(State.ACCEPTED);  // Invalid state for creation

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            agreementService.createAgreement(requestDto);
        });

        assertEquals("No se puede crear un acuerdo directamente en estado ACCEPTED o REJECTED", exception.getMessage());
    }

    @Test
    public void testAcceptAgreement_InvalidState() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreement.setState(State.ACCEPTED);  // Already accepted

        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            agreementService.acceptAgreement(1L);
        });

        assertEquals("Solo los acuerdos en estado PENDING pueden ser aceptados", exception.getMessage());
    }

    @Test
    public void testRejectAgreement_InvalidState() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        agreement.setState(State.REJECTED);  // Already rejected

        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            agreementService.rejectAgreement(1L);
        });

        assertEquals("Solo los acuerdos en estado PENDING pueden ser rechazados", exception.getMessage());
    }
}
