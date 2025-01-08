package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.OrderRequestDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.EmployeeResponseDTO;
import com.biblioteca.gestao_biblioteca.dtos.response.OrderResponseDTO;
import com.biblioteca.gestao_biblioteca.functions.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.*;
import com.biblioteca.gestao_biblioteca.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderTypeRepository orderTypeRepository;
    private final TaskRepository taskRepository;
    private final SimulatedEmailService simulatedEmailService;
    private final ClientRepository clientRepository;
    private final WorkflowRepository workflowRepository;
    private final StageRepository stageRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, OrderTypeRepository orderTypeRepository,
                        TaskRepository taskRepository, SimulatedEmailService simulatedEmailService,
                        ClientRepository clientRepository, WorkflowRepository workflowRepository,
                        StageRepository stageRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.orderTypeRepository = orderTypeRepository;
        this.taskRepository = taskRepository;
        this.simulatedEmailService = simulatedEmailService;
        this.clientRepository = clientRepository;
        this.workflowRepository = workflowRepository;
        this.stageRepository = stageRepository;
        this.bookRepository = bookRepository;
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public void createOrder(OrderRequestDTO dto) {
        try {
            List<Book> books = new ArrayList<>();

            Client client = clientRepository.findByCode(dto.clientCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

            OrderType orderType = getOrderTypeByCode(dto.orderTypeCode())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workflow não encontrado para o tipo de pedido."));

            Workflow workflow = workflowRepository.findByOrderType(orderType)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workflow não encontrado para este tipo de pedido."));

            if (workflow.getOrderType() == null || workflow.getOrderType().getDesignation() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de pedido não associado a workflow");
            }

            Order order = new Order();
            order.setCode(GeneratorCode.generateCode());
            order.setDesignation(dto.designation());
            order.setDescription(dto.description());
            order.setClientId(client);
            order.setOrderType(orderType);

            for (String bookCode : dto.bookCode()) {
                Book book = bookRepository.findByCode(bookCode)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + bookCode));
                books.add(book);
            }
            order.setBooks(books);

            create(order);

            List<Stage> stages = stageRepository.findByWorkflow(workflow);
            List<Task> tasks = taskRepository.findByStageIn(stages);

            sendEmailsToAssignedEmployees(tasks, order);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar pedido", e);
        }
    }

    public List<OrderResponseDTO> listar(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDTO> dto = new ArrayList<>();

        for (Order order : orders){
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                    order.getId(),
                    order.getCode(),
                    order.getDesignation(),
                    order.getDescription(),
                    order.getClientId().getName(),
                    order.getOrderType().getDesignation()
            );
            dto.add(orderResponseDTO);
        }
        return dto;
    }

    public Optional<OrderType> getOrderTypeByCode(String code) {
        return orderTypeRepository.findByCode(code);
    }

    private void sendEmailsToAssignedEmployees(List<Task> tasks, Order order) {
        for (Task task : tasks) {

            for (Employee employee : task.getEmployee()) {
                if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
                    String subject = "Novo Pedido para Processamento";
                    String body = buildEmailBody(order, task);

                    simulatedEmailService.sendEmail(employee.getEmail(), subject, body);
                }
            }
        }
    }

    private String buildEmailBody(Order order, Task task) {
        return "Um novo pedido foi registrado no sistema:\n\n" +
                "Tipo de Pedido: " + order.getOrderType().getDesignation() + "\n" +
                "Cliente: " + order.getClientId().getName() + "\n" +
                "Tarefa: " + task.getDesignation() + "\n" +
                "Código da Tarefa: " + task.getCode() + "\n\n" +
                "Por favor, acesse o sistema para processar esta tarefa relacionada ao pedido.";
    }

}
