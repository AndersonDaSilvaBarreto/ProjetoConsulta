package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicoCadastroRequest {
    @NotNull(message = "O nome não pode ser nulo.")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Length(min = 8, max = 8, message = "O crm tem exatamente 8 números" )
    @NotNull(message = "O crm não pode ser nulo.")
    @NotBlank(message = "O crm é obrigatório")
    private String crm;

    @Length(min = 11, max = 11, message = "O campo deve ter exatamente 11 números. Ex: 7991130366")
    @NotNull(message = "O telefone não pode ser nulo.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @Email(message = "E-mail invalido")
    private String email;

    @NotNull(message = "Ativo não pode ser nulo")
    private boolean ativo;


    private BigDecimal valorConsultaReferencia;

    @NotNull(message = "Especialidade id é obrigatório")
    private Integer especialidadeID;


}
