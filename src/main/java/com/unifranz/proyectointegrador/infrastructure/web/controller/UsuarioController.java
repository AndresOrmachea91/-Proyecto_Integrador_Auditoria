package com.unifranz.proyectointegrador.infrastructure.web.controller;


import com.unifranz.proyectointegrador.application.dto.UsuarioDto;
import com.unifranz.proyectointegrador.application.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> guardarUsuario (@RequestBody UsuarioDto usuarioDto){
        UsuarioDto usuario = usuarioService.guardar(usuarioDto);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> editarUsuario (@PathVariable Long id,
                                                     @RequestBody UsuarioDto usuarioDto){
        return usuarioService.editar(id, usuarioDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> eliminarUsuario (@PathVariable Long id){
        boolean eliminado = usuarioService.eliminar(id);
        if (!eliminado){
            return ResponseEntity.status(404).body(Map.of(
                    "eliminado", false,
                    "mensaje", "No existe un usuario activo con id " + id
            ));
        }
        return ResponseEntity.ok(Map.of(
                "eliminado", true,
                "mensaje", "Usuario " + id + " dado de baja correctamente"
        ));
    }

}
