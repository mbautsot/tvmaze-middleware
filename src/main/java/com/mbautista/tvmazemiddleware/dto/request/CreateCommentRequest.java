package com.mbautista.tvmazemiddleware.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(
    @NotBlank(message = "author no puede estar vacío") String author,
    @NotBlank(message = "comment no puede estar vacío") String comment,
    @NotNull(message = "rating es obligatorio")
    @DecimalMin(value = "1.0", message = "rating mínimo es 1.0")
    @DecimalMax(value = "10.0", message = "rating máximo es 10.0") Double rating) {
}
