package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    boolean existsByEmail(String pEmail);
}
