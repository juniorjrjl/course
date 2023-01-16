package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record SubscriptionDTO(@JsonProperty("userId") @NotNull UUID userId){}
