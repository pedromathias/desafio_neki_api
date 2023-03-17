package br.com.desafio_neki.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio_neki.dtos.CredenciaisLoginDTO;
import br.com.desafio_neki.models.User;
import br.com.desafio_neki.securities.JWTUtil;
import br.com.desafio_neki.servicies.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Injecting Dependencies
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro de usuario
    @PostMapping("/registration")
    public Map<String, Object> registerHandler(@RequestBody User user){
        // Encriptando a senha usando o Bcrypt
        String encodedPass = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPass);

        user = userService.saveUser(user);

        // Gerando o token JWT a partir do e-mail do Usuario
        //String token = jwtUtil.generateToken(user.getEmail());

        // Gerando o token JWT a partir dos dados do Usuario        
        User usuarioResumido = new User();
        usuarioResumido.setUserLogin(user.getUserLogin());
        usuarioResumido.setUserId(user.getUserId());
        usuarioResumido.setLastLoginDate(user.getLastLoginDate());
        String token = jwtUtil.generateTokenWithUserData(usuarioResumido);

        // Retornando a resposta com o JWT
        return Collections.singletonMap("jwt-token", token);
    }

    // Login de usuario
    @PostMapping("/login")
    public Map<String, Object> loginHandler
    	(@RequestBody CredenciaisLoginDTO credenciaisLoginDTO){
        try {
            // Criando o token que sera usado no processo de autenticacao
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(credenciaisLoginDTO.getUserLogin(), 
                    		credenciaisLoginDTO.getUserPassword());

            // Autenticando as credenciais de login
            authManager.authenticate(authInputToken);

            // Se o processo de autenticacao foi concluido com sucesso - etapa anterior,
            // eh gerado o JWT
            //String token = jwtUtil.generateToken(body.getEmail());

            User user = userService.findByLogin(credenciaisLoginDTO.getUserLogin());
            User usuarioResumido = new User();
            usuarioResumido.setUserLogin(user.getUserLogin());
            usuarioResumido.setUserId(user.getUserId());
            usuarioResumido.setLastLoginDate(user.getLastLoginDate());
            // Gerando o token JWT a partir dos dados do Usuario
            String token = jwtUtil.generateTokenWithUserData(usuarioResumido);

            // Responde com o JWT
            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Credenciais Invalidas");
        }
    }
}