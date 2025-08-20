package br.com.smartmed.consultas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    @Email(message = "E-mail invalido")
    @NotNull(message = "O email não pode ser nulo!")
    @NotBlank(message = "O email não pode ser vazio!")
    private String email;

    @Column(name = "senha", nullable = false)
    @NotNull(message = "A senha não pode ser nula!")
    @NotBlank(message = "É obrigatório o preenchimento da senha!")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    public enum PerfilUsuario {
        ADMIN,
        MEDICO,
        RECEPCIONISTA
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", length = 20, nullable = false )
    @NotNull(message = "O perfil não pode ser nulo!")
    private PerfilUsuario perfil;


}
