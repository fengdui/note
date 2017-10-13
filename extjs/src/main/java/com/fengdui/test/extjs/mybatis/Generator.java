package com.fengdui.extjs.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 调用MyBatisGenerator生成pojo
 * Created by 崇建 on 2016-5-25.
 */
public class Generator {

    static Logger logger = LoggerFactory.getLogger(Generator.class);

    public static void main(String[] args) throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException, URISyntaxException {
        List<String> warnings = new ArrayList<>();
        File configFile = new File(Generator.class.getClassLoader().getResource("dsTestgeneratorConfig_mysql.xml").toURI());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String error : warnings) {
            logger.warn(error);
        }
    }
}
