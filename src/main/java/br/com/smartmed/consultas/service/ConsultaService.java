package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.repository.RecepcionistaRepository;
import br.com.smartmed.consultas.rest.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private RecepcionistaRepository recepcionistaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ConsultaDTO obterPorId(Long id) {
        ConsultaModel consulta = consultaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + id + " não encontrado."));
        return modelMapper.map(consulta, ConsultaDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterTodos() {
        List<ConsultaModel> consultas = consultaRepository.findAll();
        return consultas.stream().map(consulta -> modelMapper.map(consulta,ConsultaDTO.class)).collect(Collectors.toList());
    }

    @Transactional

    public ConsultaDTO salvar(ConsultaModel novaConsulta) {
        try {
            if(consultaRepository.existsById(novaConsulta.getId())) {
                throw new ConstraintException("Já existe uma consulta com esse ID " + novaConsulta.getId() + " na base de dados!");
            }
            return modelMapper.map(consultaRepository.save(novaConsulta), ConsultaDTO.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a consulta " + novaConsulta.getId() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a consulta " + novaConsulta.getId() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public ConsultaDTO atualizar(ConsultaModel consultaExistente) {
        try {
            if(!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("A consulta com esse ID " + consultaExistente.getId() + " não existe na base de dados!");
            }
            return modelMapper.map(consultaRepository.save(consultaExistente), ConsultaDTO.class);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a consulta " + consultaExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a consulta " + consultaExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(ConsultaModel consultaExistente) {
        try {
            if(!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("Consulta inexistente na base de dados!");
            }
            consultaRepository.delete(consultaExistente);

        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a consulta " + consultaExistente.getId() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a consulta " + consultaExistente.getId() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a consulta " + consultaExistente.getId() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a deletar " + consultaExistente.getId() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a consulta" + consultaExistente.getId() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional(readOnly = true)
    public List<ConsultaHistoricoDTO> buscarHistorico(ConsultaHistoricoInputDTO filtro) {
        // Ele já retorna o DTO pronto, por isso não tá usando o map
        return consultaRepository.buscarHistoricoComFiltros(filtro);
    }

    @Transactional
    public ConsultaCancelamentoResponse cancelar(ConsultaCancelamentoRequest request) {

        if (request.getMotivo() == null || request.getMotivo().isBlank()) {
            throw new IllegalArgumentException("O motivo do cancelamento é obrigatório.");
        }

        ConsultaModel consulta = consultaRepository.findById(request.getConsultaID())
                .orElseThrow(() -> new RuntimeException("Consulta com ID " + request.getConsultaID() + " não encontrada."));

        if (!"AGENDADA".equalsIgnoreCase(consulta.getStatus())) {
            throw new RuntimeException("Apenas consultas com status 'AGENDADA' podem ser canceladas.");
        }

        if (consulta.getDataHoraConsulta().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar uma consulta que já ocorreu.");
        }

        consultaRepository.cancelamentoConsulta(consulta.getId(), request.getMotivo());

        return new ConsultaCancelamentoResponse("Consulta cancelada com sucesso", "CANCELADA");
    }



    @Transactional
    public AgendarConsultaResponse agendarConsultaAutomatica(AgendarConsultaRequest request) {

        if (request.getConvenioID() == null && request.getFormaPagamentoID() == null) {
            throw new IllegalArgumentException("Informe um convênio ou uma forma de pagamento.");
        }

        List<MedicoModel> medicos = request.getEspecialidadeID() != null
                ? medicoRepository.findByEspecialidadeID(request.getEspecialidadeID())
                : medicoRepository.findAll();

        for (MedicoModel medico : medicos) {
            List<String> horariosPadrao = agendaService.gerarHorariosPadrao();

            for (String horarioStr : horariosPadrao) {
                LocalTime hora = LocalTime.parse(horarioStr);
                LocalDate data = request.getDataHoraInicial().toLocalDate();
                LocalDateTime tentativa = LocalDateTime.of(data, hora);

                if (tentativa.isBefore(request.getDataHoraInicial())) continue;

                boolean marcado = medico.marcarConsulta(data, hora, 30);

                if (marcado) {
                    BigDecimal valorConsulta = medico.getValorConsultaReferencia();
                    if (request.getConvenioID() != null) {
                        valorConsulta = valorConsulta.multiply(BigDecimal.valueOf(0.5));
                    }

                    ConsultaModel consulta = new ConsultaModel();
                    consulta.setDataHoraConsulta(tentativa);
                    consulta.setStatus("AGENDADA");
                    consulta.setValor(valorConsulta);
                    consulta.setObservacoes(null);
                    consulta.setPacienteID(request.getPacienteID());
                    consulta.setMedicoID(medico.getId());
                    consulta.setFormaPagamentoID(request.getFormaPagamentoID() != null ? request.getFormaPagamentoID() : 0);
                    consulta.setConvenioID(request.getConvenioID() != null ? request.getConvenioID() : 0);
                    consulta.setRecepcionistaID(null);

                    consultaRepository.save(consulta);

                    return new AgendarConsultaResponse(
                            consulta.getId(),
                            consulta.getDataHoraConsulta(),
                            consulta.getMedicoID(),
                            consulta.getPacienteID(),
                            consulta.getValor(),
                            consulta.getStatus()
                    );
                }
            }
        }

        throw new RuntimeException("Nenhum horário disponível encontrado.");
    }
    @Transactional
    public ReagendamentoDeConsultaResponse reagendamentoDeConsulta(ReagendamentoDeConsultaRequest request) {

        ConsultaModel consultaParaCancelar = consultaRepository
                .findById(request.getConsultaID())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada") );
        //Verifica se a data é no passado
        if(request.getNovaDataHora().isBefore(LocalDateTime.now().plusMinutes(1))) {
            throw new RuntimeException("A consulta não pode ser marcada para uma data do passado");
        }
        //

        //Verifica se médico tem horário disponível
        MedicoModel medico = medicoRepository.findById(consultaParaCancelar.getMedicoID())
                .orElseThrow(() -> new RuntimeException("Medico(a) não encontrado(a)") );
        LocalDate novaData = request.getNovaDataHora().toLocalDate();
        LocalTime novoHorario = request.getNovaDataHora().toLocalTime();

        boolean disponivel = medico.marcarConsulta(novaData, novoHorario, 30);
        if(!disponivel) {
            throw new RuntimeException("O médico não tem disponibilidade nesta nova data e horario");
        }
        //--
        consultaRepository.cancelamentoConsulta(request.getConsultaID(), request.getMotivo());
        ConsultaModel novaConsulta = new ConsultaModel();
        novaConsulta.setPacienteID(consultaParaCancelar.getPacienteID());
        novaConsulta.setMedicoID(consultaParaCancelar.getMedicoID());
        novaConsulta.setRecepcionistaID(consultaParaCancelar.getRecepcionistaID());
        novaConsulta.setValor(consultaParaCancelar.getValor());
        novaConsulta.setStatus("AGENDADA");
        novaConsulta.setDataHoraConsulta(request.getNovaDataHora());
        novaConsulta.setFormaPagamentoID(consultaParaCancelar.getFormaPagamentoID());
        novaConsulta.setConvenioID(consultaParaCancelar.getConvenioID());
        novaConsulta.setObservacoes("Consulta remarcada por motivo de " + request.getMotivo());
        consultaRepository.save(novaConsulta);

        ReagendamentoDeConsultaResponse response = new ReagendamentoDeConsultaResponse();
        response.setMensagem("Consulta reagendada com sucesso");
        response.setNovaDataHora(novaConsulta.getDataHoraConsulta());

        consultaParaCancelar.setObservacoes("Consulta reagendada para a data e horário " + request.getNovaDataHora());
        atualizar(consultaParaCancelar);
        return response;


    }
    @Transactional
    public CadastrarConsultaRecepcionistaResponse cadastrarConsultaPorRecepcionista(CadastrarConsultaRecepcionistaRequest request) {
        if(recepcionistaRepository.existsById(request.getRecepcionistaID())) {
            throw new RuntimeException("O recepcionista não existe!");
        }
        if(recepcionistaRepository.existsByIdAndAtivoTrue(request.getRecepcionistaID())) {
            throw new RuntimeException("O recepcionista não está ativo!");
        }
        MedicoModel medico = medicoRepository.findById(request.getMedicoID())
                .orElseThrow(() -> new RuntimeException("Medico(a) não encontrado(a)") );

        LocalDate data = request.getDataHora().toLocalDate();
        LocalTime hora = request.getDataHora().toLocalTime();
        boolean disponivel = medico.marcarConsulta(data,hora, request.getDuracaoMinutos());
        if(!disponivel) {
            throw new RuntimeException("O médico não tem disponibilidade nesta nova data e horario");
        }

        ConsultaModel novaConsulta = new ConsultaModel();
        novaConsulta.setDataHoraConsulta(request.getDataHora());
        novaConsulta.setPacienteID(request.getPacienteID());
        novaConsulta.setMedicoID(request.getMedicoID());
        novaConsulta.setConvenioID(request.getConvenioID());
        novaConsulta.setFormaPagamentoID(request.getFormaPagamentoID());
        novaConsulta.setRecepcionistaID(request.getRecepcionistaID());
        novaConsulta.setStatus("AGENDADA");
        if(request.getConvenioID() != null) {
            novaConsulta.setValor(
                    medico.getValorConsultaReferencia()
                            .multiply(BigDecimal.valueOf(0.5))
                            .setScale(2, RoundingMode.HALF_UP)
            );

        }else {
            novaConsulta.setValor(
                    medico.getValorConsultaReferencia()
            );
        }
        consultaRepository.save(novaConsulta);
        CadastrarConsultaRecepcionistaResponse response = new CadastrarConsultaRecepcionistaResponse();
        response.setMensagem("Consulta agendada com sucesso");
        response.setStatus(novaConsulta.getStatus());
        return response;
    }

}


