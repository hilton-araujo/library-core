package com.biblioteca.gestao_biblioteca.dtos.response;

import java.time.LocalDateTime;

public record ReplyDTO(
        String id,
        String author,
        String text,
        LocalDateTime timestamp
) {}

