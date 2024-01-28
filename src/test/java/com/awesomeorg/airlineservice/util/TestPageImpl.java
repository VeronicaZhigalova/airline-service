package com.awesomeorg.airlineservice.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.core.util.Json;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class TestPageImpl<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TestPageImpl(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int number,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") Long totalElements,
                        @JsonProperty("pageable") JsonNode pageable,
                        @JsonProperty("last") boolean last,
                        @JsonProperty("totalPages") int totalPages,
                        @JsonProperty("sort") JsonNode sort,
                        @JsonProperty("first") boolean first,
                        @JsonProperty("numberOfElements") int numberOfElements,
                        @JsonProperty("empty") boolean empty) {


        super(content, PageRequest.of(number, size), totalElements);
    }
    public TestPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
