package com.unifranz.proyectointegrador.infrastructure.persistence;

import com.unifranz.proyectointegrador.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    // NO eliminados: los usa GET /usuarios
    @Query("from Usuario u where u.eliminado = false")
    List<Usuario> listarNoEliminados();

    // SOLO eliminados (eliminado logico): los usa GET /usuarios/eliminados
    @Query("from Usuario u where u.eliminado = true")
    List<Usuario> listarEliminados();
}
