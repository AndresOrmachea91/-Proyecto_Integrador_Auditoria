package com.unifranz.proyectointegrador.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// supertabla de auditoria: no es una tabla propia, sus columnas
// se heredan en cada entidad que haga "extends Auditable"
@MappedSuperclass
@Getter
@Setter
public abstract class Auditable {

    // updatable = false: una vez creado ya no se puede modificar
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    @Column(updatable = false)
    private String creadoPor;

    private String modificadoPor;

    // en auditoria siempre se usa eliminado logico
    @Column(nullable = false)
    private Boolean eliminado = false;

    @PrePersist
    public void antesDeCrear() {
        this.fechaCreacion = LocalDateTime.now();
        this.creadoPor = "sistema";
        this.eliminado = false;
    }

    @PreUpdate
    public void antesDeActualizar() {
        this.fechaModificacion = LocalDateTime.now();
        this.modificadoPor = "sistema";
    }
}
