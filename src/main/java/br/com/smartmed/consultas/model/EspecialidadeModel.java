package br.com.smartmed.consultas.model;

import br.com.smartmed.consultas.rest.dto.EspecialidadeDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "especialidade")
public class EspecialidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 64, nullable = false, unique = true)
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "descricao",length = 255)
    private String descricao;

    /*
    public EspecialidadeDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EspecialidadeDTO.class);
    }

     */


}
