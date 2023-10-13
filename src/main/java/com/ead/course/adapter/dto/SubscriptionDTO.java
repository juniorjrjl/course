package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubscriptionDTO(@JsonProperty("userId") @NotNull UUID userId){}
