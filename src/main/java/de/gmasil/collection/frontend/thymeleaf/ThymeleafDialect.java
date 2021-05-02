package de.gmasil.collection.frontend.thymeleaf;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

@Component
public class ThymeleafDialect extends AbstractDialect implements IExpressionObjectDialect {
    @Autowired
    private ThymeleafUtils utils;

    protected ThymeleafDialect() {
        super("Gmasil Dialect");
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Collections.singleton("utils");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return utils;
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

}
