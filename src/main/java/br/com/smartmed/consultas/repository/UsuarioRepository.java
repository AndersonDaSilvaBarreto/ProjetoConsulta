package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    Optional<UsuarioModel> findByEmail(String pEmail);
    boolean existsByEmail(String pEmail);
}
