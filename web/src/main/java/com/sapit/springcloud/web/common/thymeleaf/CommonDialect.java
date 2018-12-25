package com.sapit.springcloud.web.common.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class CommonDialect extends AbstractDialect implements IExpressionObjectDialect {
	private final IExpressionObjectFactory JAVA8_TIME_EXPRESSION_OBJECTS_FACTORY = new CommonExpressionObjectFactory();

	public CommonDialect() {
		super("commonDialect");
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return JAVA8_TIME_EXPRESSION_OBJECTS_FACTORY;
	}

}
