package com.bibliotek.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bibliotek.domain.Estudiante;
import com.bibliotek.service.dto.EstudianteDTO;

@Service
public class EstudianteMapper {
	  public List<EstudianteDTO> estudiantesToEstudianteDTOs(List<Estudiante> estudiantes) {
	        return estudiantes.stream()
	            .filter(Objects::nonNull)
	            .map(this::estudianteToEstudianteDTO)
	            .collect(Collectors.toList());
	    }

	    public EstudianteDTO estudianteToEstudianteDTO(Estudiante estudiante) {
	        return new EstudianteDTO(estudiante);
	    }

	    public List<Estudiante> estudianteDTOsToEstudiantes(List<EstudianteDTO> estudianteDTOs) {
	        return estudianteDTOs.stream()
	            .filter(Objects::nonNull)
	            .map(this::estudianteDTOToEstudiante)
	            .collect(Collectors.toList());
	    }

	    public Estudiante estudianteDTOToEstudiante(EstudianteDTO estudianteDTO) {
	        if (estudianteDTO == null) {
	            return null;
	        } else {
	            Estudiante estudiante = new Estudiante();
	            estudiante.setId(estudianteDTO.getId());
	            estudiante.setIdBibliotecaEsta(estudianteDTO.getIdBibliotecaEsta());
	            estudiante.setUser(estudianteDTO.getUser());
	            estudiante.setAvisos(estudianteDTO.getAvisos());
	            estudiante.setBibliotecas(estudianteDTO.getBibliotecas());
	            estudiante.setReportes(estudianteDTO.getReportes());
	            estudiante.setCodigoQR(estudianteDTO.getCodigoQR());
	            return estudiante;
	        }
	    }



	    public Estudiante estudianteFromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Estudiante estudiante = new Estudiante();
	        estudiante.setId(id);
	        return estudiante;
	    }
}
