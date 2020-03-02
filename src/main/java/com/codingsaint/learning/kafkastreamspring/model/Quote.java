package com.codingsaint.learning.kafkastreamspring.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
public class Quote {
    private String quote;
    private Set<String> categories;
}
