package br.com.ifdelivery.api.acesso;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import br.com.ifdelivery.modelo.acesso.Usuario;
import br.com.ifdelivery.modelo.acesso.UsuarioService;
import br.com.ifdelivery.modelo.seguranca.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final JwtService jwtService;

    private UsuarioService usuarioService;

    public AuthenticationController(JwtService jwtService, UsuarioService usuarioService) {

        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }


    @PostMapping
    public Map<Object, Object> signin(@RequestBody @Valid AuthenticationRequest data) {

        Usuario authenticatedUser = usuarioService.authenticate(data.getUsername(), data.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());

        return loginResponse;
    }
}
