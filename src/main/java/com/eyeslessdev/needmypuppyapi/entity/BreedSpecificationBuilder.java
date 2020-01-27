package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BreedSpecificationBuilder {

    private final List<SearchCriteria> params;

    public BreedSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public BreedSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<SearchCriteria> build() {
        if (params.size() == 0) {
            return null;
        }

        List<BreedSpecification> specs = params.stream()
                .map(BreedSpecification::new)
                .collect(Collectors.toList());

        Specification<SearchCriteria> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
