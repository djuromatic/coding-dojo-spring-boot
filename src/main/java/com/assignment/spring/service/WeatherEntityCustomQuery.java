package com.assignment.spring.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class WeatherEntityCustomQuery {

    public BooleanExpression celsiuseToFahrenheitQuery(NumberPath<Double> path, Double value) {
        BooleanExpression condition = Expressions.asNumber(1).eq(1);
        if (value != null) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.or(path.eq(getFahrenheit(value)));
            condition = condition.and(builder);
        }
        return condition;
    }

    private Double getFahrenheit(Double temperature) {
        return (double) Math.round((temperature * 1.8) + 32);
    }

}
