package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.models.Order;
import com.biblioteca.gestao_biblioteca.models.Task;
import com.biblioteca.gestao_biblioteca.models.Workflow;
import com.biblioteca.gestao_biblioteca.repository.OrderRepository;
import com.biblioteca.gestao_biblioteca.repository.TaskRepository;
import com.biblioteca.gestao_biblioteca.repository.WorkflowRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WorkflowRepository workflowRepository;
    private final TaskRepository taskRepository;
    private final SimulatedEmailService simulatedEmailService;

    public OrderService(OrderRepository orderRepository,
                        WorkflowRepository workflowRepository,
                        TaskRepository taskRepository,
                        SimulatedEmailService simulatedEmailService) {
        this.orderRepository = orderRepository;
        this.workflowRepository = workflowRepository;
        this.taskRepository = taskRepository;
        this.simulatedEmailService = simulatedEmailService;
    }

    public void createOrder(Order order) {
        orderRepository.save(order);

        Workflow workflow = workflowRepository.findByOrderType(order.getOrderType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workflow não encontrado para o tipo de pedido."));

        List<Task> tasks = taskRepository.findByStageWorkflow(workflow);

        for (Task task : tasks) {
            if (task.getEmployee() != null) {
                String subject = "Novo Pedido para Processamento";
                String body = "Um novo pedido foi registrado no sistema:\n\n" +
                        "Tipo de Pedido: " + order.getOrderType().getDesignation() + "\n" +
                        "Cliente: " + order.getClientId() + "\n\n" +
                        "Por favor, acesse o sistema para processar este pedido.";

                simulatedEmailService.sendEmail(task.getEmployee().getEmail(), subject, body);
            }
        }
    }
}
