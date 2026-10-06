package com.unifranz.proyectointegrador.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// los campos que quedan en null no se envian en el JSON,
// asi cada constructor (sobrecarga) muestra solo lo que necesita
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;

    // datos de auditoria
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String creadoPor;
    private String modificadoPor;
    private Boolean eliminado;

    // sobrecarga 1: datos para crear un usuario
    public UsuarioDto(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // sobrecarga 2: datos basicos para listar
    public UsuarioDto(Long id, String nombre, String email) {
        this(nombre, email);
        this.id = id;
    }

    // sobrecarga 3: datos basicos + auditoria
    public UsuarioDto(Long id, String nombre, String email,
                      LocalDateTime fechaCreacion, LocalDateTime fechaModificacion,
                      String creadoPor, String modificadoPor, Boolean eliminado) {
        this(id, nombre, email);
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.creadoPor = creadoPor;
        this.modificadoPor = modificadoPor;
        this.eliminado = eliminado;
    }
}
