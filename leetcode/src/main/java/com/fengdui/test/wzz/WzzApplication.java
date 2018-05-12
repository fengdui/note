package com.fengdui.test.wzz;

import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.prepare.PlannerImpl;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.net.URLClassLoader;

@SpringBootApplication
public class WzzApplication {
	private CalciteSchema internalSchema = CalciteSchema.createRootSchema(true, false);
	private SchemaPlus rootSchema = internalSchema.plus();

	private SqlParser.Config getSqlParserConfig(){
		return CalciteConfig.DEFAULT;
	}

	public void parseSql() {
		FrameworkConfig frameworkConfig = Frameworks.newConfigBuilder()
				.defaultSchema(rootSchema)
				.parserConfig(getSqlParserConfig)
				.costFactory(new DataSetCostFactory())
				.typeSystem(RelDataTypeSystem.DEFAULT)
				.operatorTable(getSqlOperatorTable)
				// set the executor to evaluate constant expressions
				.executor(new ExpressionReducer(config))
				.build();
		PlannerImpl planner = new PlannerImpl();
	}

	public static void main(String[] args) {
	}
}
