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
        usuario.setActivo(true);
        Usuario guardado = usuarioRepository.save(usuario);
        return new UsuarioDto(guardado.getId(), guardado.getNombre(), guardado.getEmail());
    }

    @Override
    public List<UsuarioDto> listar(){
        // solo los activos: los eliminados logicamente quedan ocultos
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(u -> new UsuarioDto(u.getId(), u.getNombre(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsuarioDto> editar (Long id, UsuarioDto usuarioDto){
        Optional<Usuario> encontrado = usuarioRepository.findById(id);
        if (encontrado.isEmpty()){
            return Optional.empty();
        }
        Usuario usuario = encontrado.get();
        if (!usuario.getActivo()){
            // un usuario dado de baja no se puede editar
            return Optional.empty();
        }
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        Usuario actualizado = usuarioRepository.save(usuario);
        return Optional.of(
                new UsuarioDto(actualizado.getId(), actualizado.getNombre(), actualizado.getEmail())
        );
    }

    @Override
    public boolean eliminar (Long id){
        Optional<Usuario> encontrado = usuarioRepository.findById(id);
        if (encontrado.isEmpty()){
            return false;
        }
        Usuario usuario = encontrado.get();
        if (!usuario.getActivo()){
            // ya estaba dado de baja
            return false;
        }
        // eliminado logico: no se usa delete(), solo se apaga la bandera
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return true;
    }

}
