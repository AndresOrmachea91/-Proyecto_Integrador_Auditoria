package com.unifranz.proyectointegrador.application.service;

import com.unifranz.proyectointegrador.application.dto.UsuarioDto;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    UsuarioDto guardar (UsuarioDto usuarioDto);
    List<UsuarioDto> listar();
    Optional<UsuarioDto> editar (Long id, UsuarioDto usuarioDto);
}
