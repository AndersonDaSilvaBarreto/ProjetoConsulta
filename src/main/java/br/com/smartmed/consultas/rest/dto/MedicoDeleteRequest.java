package br.com.smartmed.consultas.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicoDeleteRequest {
    @Length(min = 8, max = 8, message = "O crm tem exatamente 8 números" )
    @NotNull(message = "O crm não pode ser nulo.")
    @NotBlank(message = "O crm é obrigatório")
    private String crm;
}
