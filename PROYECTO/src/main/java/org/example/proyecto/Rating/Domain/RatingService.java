package org.example.proyecto.Rating.Domain;

import org.example.proyecto.Item.Domain.Item;
import org.example.proyecto.Rating.Infrastructure.RatingRepository;
import org.example.proyecto.Rating.dto.RatingRequestDto;
import org.example.proyecto.Rating.dto.RatingResponseDto;
import org.example.proyecto.Usuario.Domain.Usuario;
import org.example.proyecto.Usuario.infrastructure.UsuarioRepository;
import org.example.proyecto.auth.domain.AuthenticationService;
import org.example.proyecto.auth.utils.AuthorizationUtils;
import org.example.proyecto.exception.ResourceNotFoundException;
import org.example.proyecto.exception.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    
    public RatingResponseDto crearRating(RatingRequestDto requestDTO) {
        Rating rating = new Rating();

        Usuario usuarioCalificado = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario calificado no encontrado"));
        Usuario raterUsuario = usuarioRepository.findById(requestDTO.getRaterUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario que califica no encontrado"));

        rating.setUsuario(usuarioCalificado);
        rating.setRaterUsuario(raterUsuario);
        rating.setRating(requestDTO.getRating());
        rating.setComment(requestDTO.getComment());
        rating.setCreatedAt(LocalDateTime.now());

        rating = ratingRepository.save(rating);

        return convertirAResponseDTO(rating);
    }
    
    public List<RatingResponseDto> listarRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }


    public List<RatingResponseDto> obtenerRatingsPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Rating> ratings = ratingRepository.findByUsuario(usuario);
        return ratings.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }
    public void deleteItem(Long itemId) {
        Rating rating = ratingRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("raiting not found"));

        if (!authorizationUtils.isAdminOrResourceOwner(rating.getUsuario().getId())) {
            throw new UnauthorizeOperationException("You do not have permission to delete this item.");
        }

        ratingRepository.delete(rating);
    }


    private RatingResponseDto convertirAResponseDTO(Rating rating) {
        RatingResponseDto responseDTO = modelMapper.map(rating, RatingResponseDto.class);
        responseDTO.setRaterUsuarioNombre(rating.getRaterUsuario().getFirstname() + " " + rating.getRaterUsuario().getLastname());
        responseDTO.setUsuarioNombre(rating.getUsuario().getFirstname() + " " + rating.getUsuario().getLastname());
        return responseDTO;
    }

}