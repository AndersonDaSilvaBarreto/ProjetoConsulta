package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.repository.RecepcionistaRepository;
import br.com.smartmed.consultas.rest.dto.PaginacaoRespostaDTO;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import br.com.smartmed.consultas.rest.dto.RecepcionistaRequestFiltros;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecepcionistaService {
    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public RecepcionistaDTO obterPorId(int id) {
         RecepcionistaModel recepcionista = recepcionistaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Recepcionista com id " + id + "não encontrado!"));
         return modelMapper.map(recepcionista, RecepcionistaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<RecepcionistaDTO> obterTodos() {
        List<RecepcionistaModel> recepcionistas = recepcionistaRepository.findAll();
        return recepcionistas.stream().map( recepcionista -> modelMapper.map(recepcionista, RecepcionistaDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public RecepcionistaDTO salvar(RecepcionistaModel novoRecepcionista) {
        try {
            if(recepcionistaRepository.existsByCpf(novoRecepcionista.getCpf())) {
                throw new ConstraintException("Já existe um recepcionista com esse CPF" + novoRecepcionista.getCpf() + "na base de dados" );
            }
            return modelMapper.map(recepcionistaRepository.save(novoRecepcionista), RecepcionistaDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o recepcionista " + novoRecepcionista.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public RecepcionistaDTO atualizar(RecepcionistaModel recepcionistaExistente) {
        try {
            if(!recepcionistaRepository.existsByCpf(recepcionistaExistente.getCpf())) {
                throw new ConstraintException("O recepcionista com esse CPF " + recepcionistaExistente.getCpf() + " não existe na base de dados!");
            }
            return modelMapper.map(recepcionistaRepository.save(recepcionistaExistente), RecepcionistaDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o recepcionista " + recepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
    @Transactional
    public void deletar(RecepcionistaModel recepcionistaExistente) {
        try {
            if(!recepcionistaRepository.existsByCpf(recepcionistaExistente.getCpf())) {
                throw new ConstraintException("Recepcionista inexistente na base de dados!");
            }
            recepcionistaRepository.delete(recepcionistaExistente);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o recepcionista " + recepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + recepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional(readOnly = true)
    public PaginacaoRespostaDTO<RecepcionistaDTO> buscarComFiltros(RecepcionistaRequestFiltros filtro) {

        Sort.Direction direction = Sort.Direction.ASC;

        if(filtro.getDirecao() != null) {
            direction = Sort.Direction.fromString(filtro.getDirecao());
        }

        String ordenarPor = filtro.getOrdenarPor() != null ? filtro.getOrdenarPor() : "nome";

        Pageable pageable = PageRequest.of(
                filtro.getPagina(),
                filtro.getTamanhoPagina(),
                Sort.by(direction, ordenarPor)
        );


        Boolean statusBooleano = null;
        if (filtro.getStatus() != null && !filtro.getStatus().isBlank()) {
            if ("ATIVO".equalsIgnoreCase(filtro.getStatus())) {
                statusBooleano = true;
            } else if ("INATIVO".equalsIgnoreCase(filtro.getStatus())) {
                statusBooleano = false;
            }

        }

        Page<RecepcionistaDTO> paginaDeDTOs = recepcionistaRepository.listagemDeRecepcionistasComFiltro(
                statusBooleano,
                filtro.getDataInicio(),
                filtro.getDataFim(),
                pageable
        );
        
        return new PaginacaoRespostaDTO<>(
                paginaDeDTOs.getContent(),
                paginaDeDTOs.getNumber(),
                paginaDeDTOs.getTotalPages(),
                paginaDeDTOs.getTotalElements()
        );

}
}
