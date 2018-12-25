package com.sapit.springcloud.web.common.thymeleaf;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class CommonExpressionObjectFactory implements IExpressionObjectFactory {
	private static final String COMMON_EXPRESSION_OBJECT_NAME = "common";

	private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(COMMON_EXPRESSION_OBJECT_NAME)));

	@Override
	public Set<String> getAllExpressionObjectNames() {
		return ALL_EXPRESSION_OBJECT_NAMES;
	}

	@Override
	public Object buildObject(IExpressionContext context, String expressionObjectName) {
		if (COMMON_EXPRESSION_OBJECT_NAME.equals(expressionObjectName)) {
			return new CommonExpressionObject(context.getLocale());
		}
		return null;
	}

	@Override
	public boolean isCacheable(String expressionObjectName) {
		return expressionObjectName != null && COMMON_EXPRESSION_OBJECT_NAME.equals(expressionObjectName);
	}
}
