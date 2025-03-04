package com.biblioteca.gestao_biblioteca.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDTO(
        String id,
        String author,
        String text,
        LocalDateTime timestamp,
        List<ReplyDTO> replies
) {}
