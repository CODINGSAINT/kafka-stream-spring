package com.codingsaint.learning.kafkastreamspring.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Set;

@Data
public class Quote {
    private String quote;
    private Set<String> categories;
}
