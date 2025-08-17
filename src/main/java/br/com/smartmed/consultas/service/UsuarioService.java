package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.ConstraintException;
import br.com.smartmed.consultas.exception.DataIntegrityException;
import br.com.smartmed.consultas.model.UsuarioModel;
import br.com.smartmed.consultas.repository.UsuarioRepository;
import br.com.smartmed.consultas.rest.dto.UsuarioCadastroResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioCadastroResponse cadastrarUsuario(UsuarioModel novoUsuario) {
        try {
            if(usuarioRepository.existsByEmail(novoUsuario.getEmail())) {
                throw new ConstraintException("Já existe um usuario com esse email "
                        + novoUsuario.getEmail()
                        + " na base de dados!");
            }

            novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));

            UsuarioModel usuarioSalvo =  usuarioRepository.save(novoUsuario);

            UsuarioCadastroResponse response = new UsuarioCadastroResponse();
            response.setMensagem("Usuário cadastrado com sucesso");
            response.setUsuarioID(usuarioSalvo.getId());
            return response;



        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o usuario "
                    + novoUsuario.getNome() + " !");
        }
    }
}
