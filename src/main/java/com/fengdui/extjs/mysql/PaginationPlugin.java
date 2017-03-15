package com.fengdui.extjs.mysql;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class PaginationPlugin extends PluginAdapter {

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		addPage(topLevelClass, introspectedTable, "page");
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();
		XmlElement paginationSuffixElement = new XmlElement("sql");
		paginationSuffixElement.addAttribute(new Attribute("id", "MysqlSuffix"));
		XmlElement pageEnd = new XmlElement("if");
		pageEnd.addAttribute(new Attribute("test", "page != null"));
		pageEnd.addElement(new TextElement("<![CDATA[ limit #{page.begin} , #{page.length} ]]>"));
		paginationSuffixElement.addElement(pageEnd);
		parentElement.addElement(paginationSuffixElement);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("include");
		isNotNullElement.addAttribute(new Attribute("refid", "MysqlSuffix"));
		element.getElements().add(isNotNullElement);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	public boolean validate(List<String> arg0) {
		return true;
	}

	private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType(
				"cn.com.bsfit.frms.portal.base.util.Page"));
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType("cn.com.bsfit.frms.portal.base.util.Page"));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType("cn.com.bsfit.frms.portal.base.util.Page"), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType("cn.com.bsfit.frms.portal.base.util.Page"));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

}
