package com.codingsaint.learning.kafkastreamspring.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@ToString
public class Quote {
    private String content;
    private Set<String> tags;
    private String author;
}
