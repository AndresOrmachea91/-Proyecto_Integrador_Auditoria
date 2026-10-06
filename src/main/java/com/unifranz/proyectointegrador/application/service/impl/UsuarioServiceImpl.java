package com.unifranz.proyectointegrador.application.service.impl;

import com.unifranz.proyectointegrador.application.dto.UsuarioDto;
import com.unifranz.proyectointegrador.application.service.UsuarioService;
import com.unifranz.proyectointegrador.domain.Usuario;
import com.unifranz.proyectointegrador.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto guardar (UsuarioDto usuarioDto){
        Usuario usuario = new Usuario();
        // validacion
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        // fechaCreacion, creadoPor y eliminado los llena Auditable (@PrePersist)
        Usuario guardado = usuarioRepository.save(usuario);
        return aDtoConAuditoria(guardado);
    }

    @Override
    public List<UsuarioDto> listar(){
        // solo los NO eliminados (eliminado = false), con sus datos de auditoria
        return usuarioRepository.listarNoEliminados()
                .stream()
                .map(this::aDtoConAuditoria)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> listarEliminados(){
        // solo los eliminados logicamente (eliminado = true), siguen en la tabla
        return usuarioRepository.listarEliminados()
                .stream()
                .map(this::aDtoConAuditoria)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDto> editar (Long id, UsuarioDto usuarioDto){
        Optional<Usuario> encontrado = usuarioRepository.findById(id);
        if (encontrado.isEmpty()){
            return Optional.empty();
        }
        Usuario usuario = encontrado.get();
        if (usuario.getEliminado()){
            // un usuario eliminado no se puede editar
            return Optional.empty();
        }
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        // fechaModificacion y modificadoPor los llena Auditable (@PreUpdate)
        Usuario actualizado = usuarioRepository.saveAndFlush(usuario);
        return Optional.of(aDtoConAuditoria(actualizado));
    }

    @Override
    public boolean eliminar (Long id){
        Optional<Usuario> encontrado = usuarioRepository.findById(id);
        if (encontrado.isEmpty()){
            return false;
        }
        Usuario usuario = encontrado.get();
        if (usuario.getEliminado()){
            // ya estaba eliminado
            return false;
        }
        // eliminado logico: no se usa delete(), solo se marca la bandera
        usuario.setEliminado(true);
        usuarioRepository.save(usuario);
        return true;
    }

    private UsuarioDto aDtoConAuditoria(Usuario u){
        return new UsuarioDto(u.getId(), u.getNombre(), u.getEmail(),
                u.getFechaCreacion(), u.getFechaModificacion(),
                u.getCreadoPor(), u.getModificadoPor(), u.getEliminado());
    }

}
