package org.example.proyecto.Shipment;

import org.example.proyecto.Shipment.Application.ShipmentController;
import org.example.proyecto.Shipment.Domain.ShipmentService;
import org.example.proyecto.Shipment.Dto.ShipmentRequestDto;
import org.example.proyecto.Shipment.Dto.ShipmentResponseDto;
import org.example.proyecto.Agreement.Domain.Agreement;
import org.example.proyecto.Agreement.Infrastructure.AgreementRepository;
import org.example.proyecto.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShipmentController.class)
@AutoConfigureMockMvc(addFilters = false)  // Deshabilita los filtros de seguridad si no los necesitas en el test
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    @MockBean
    private AgreementRepository agreementRepository;

    // Si hay un JwtService u otro servicio de seguridad, moca también
    @MockBean
    private JwtService jwtService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        // Cualquier configuración adicional que se necesite
    }

    @Test
    public void testGetAllShipments() throws Exception {
        when(shipmentService.getAllShipments()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(shipmentService, times(1)).getAllShipments();
    }

    @Test
    public void testCreateShipmentForAgreement() throws Exception {
        ShipmentRequestDto requestDto = new ShipmentRequestDto();
        requestDto.setAgreementId(1L);

        Agreement agreement = new Agreement();
        when(agreementRepository.findById(1L)).thenReturn(Optional.of(agreement));

        mockMvc.perform(post("/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(shipmentService, times(1)).createShipmentForAgreement(any(Agreement.class));
    }

    @Test
    public void testCreateShipmentForAgreement_AgreementNotFound() throws Exception {
        when(agreementRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"agreementId\": 1}"))
                .andExpect(status().isNotFound());

        verify(shipmentService, never()).createShipmentForAgreement(any(Agreement.class));
    }

    @Test
    public void testGetShipmentById() throws Exception {
        ShipmentResponseDto responseDto = new ShipmentResponseDto();
        responseDto.setId(1L);

        when(shipmentService.getShipmentById(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/shipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(shipmentService, times(1)).getShipmentById(anyLong());
    }

    @Test
    public void testUpdateShipment() throws Exception {
        ShipmentRequestDto requestDto = new ShipmentRequestDto();
        ShipmentResponseDto responseDto = new ShipmentResponseDto();
        responseDto.setId(1L);

        when(shipmentService.updateShipment(anyLong(), any(ShipmentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/shipments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(shipmentService, times(1)).updateShipment(anyLong(), any(ShipmentRequestDto.class));
    }

    @Test
    public void testDeleteShipment() throws Exception {
        doNothing().when(shipmentService).deleteShipment(anyLong());

        mockMvc.perform(delete("/shipments/1"))
                .andExpect(status().isNoContent());

        verify(shipmentService, times(1)).deleteShipment(anyLong());
    }
}
