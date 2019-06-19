package com.mr.archer.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Created by feng on 2019/5/18
 */
public class CodeGenerator {
	/*
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	private static String tableName = "customer_legal";
	private static String entityName = "CustomerLegal";
	private static String javaOutputPath = "E:\\GitHub\\mr-archer-20190610\\archer-console\\src\\main\\java";
	private static String xmlOutputPath = "E:\\GitHub\\mr-archer-20190610\\archer-console\\src\\main\\resources";
	private static String jdbcUrl = "jdbc:mysql://47.101.152.45:3306/archer?characterEncoding=UTF-8&useSSL=false";
	private static String dbUser = "root";
	private static String dbPassword = "root1234";



	public static void main(String[] args) {
		doJava();
	}


	private static void doSample(){
		AutoGenerator mpg = new AutoGenerator();
		// 选择 freemarker 引擎，默认 Veloctiy
		//FreemarkerTemplateEngine te = new FreemarkerTemplateEngine();
		//mpg.setTemplateEngine(te);
		TemplateConfig te = new TemplateConfig();
		te.setXml(null);
		mpg.setTemplate(te);

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("jiang.feng");
		gc.setOutputDir("D:\\workspace\\archer\\archer-console\\src\\main\\java");
		gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
		gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
		// gc.setMapperName("%sDao");
		// gc.setXmlName("%sDao");
		// gc.setServiceName("%Service");
		// gc.setServiceImplName("%sServiceDiy");
		// gc.setControllerName("%sAction");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			// 自定义数据库表字段类型转换【可选】
			@Override
			public DbColumnType processTypeConvert(String fieldType) {
				System.out.println("转换类型：" + fieldType);
				// 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
				return super.processTypeConvert(fieldType);
			}
		});
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("root123");
		dsc.setUrl("jdbc:mysql://192.168.25.117:3306/archer?characterEncoding=UTF-8&useSSL=false");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
		//strategy.setTablePrefix(new String[]{"user_"});// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[]{"app_audit_log"}); // 需要生成的表
		strategy.setEntityLombokModel(false);
		// strategy.setExclude(new String[]{"test"}); // 排除生成的表
		// 自定义实体父类
		// strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.baomidou.demo.TestService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.baomidou.demo.TestController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuilderModel(true);

		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.mr.archer");
		pc.setController("controller");
		pc.setMapper("dao");

//        pc.setModuleName("test");
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//
//        // 自定义 xxList.jsp 生成
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 调整 xml 生成目录演示
//        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
		// 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		// TemplateConfig tc = new TemplateConfig();
		// tc.setController("...");
		// tc.setEntity("...");
		// tc.setMapper("...");
		// tc.setXml("...");
		// tc.setService("...");
		// tc.setServiceImpl("...");
		// 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
		// mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();
	}

	private static void doJava(){
		AutoGenerator mpg = new AutoGenerator();
		//TemplateConfig te = new TemplateConfig();
		//te.setXml(null);
		//mpg.setTemplate(te);

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("jiang.feng");
		gc.setOutputDir(javaOutputPath);
		gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
		gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setServiceName(entityName + "Service");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);

		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername(dbUser);
		dsc.setPassword(dbPassword);
		dsc.setUrl(jdbcUrl);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[]{tableName}); // 需要生成的表
		strategy.setSuperControllerClass("com.mr.archer.controller.BaseController");
		strategy.setEntityLombokModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.mr.archer");
		pc.setController("controller");
		pc.setMapper("dao");
		pc.setXml("dao");

		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}


	private static void doXml(){
		AutoGenerator mpg = new AutoGenerator();
		TemplateConfig tc = new TemplateConfig();
		tc.setController(null);
		tc.setEntity(null);
		tc.setMapper(null);
		tc.setService(null);
		tc.setServiceImpl(null);
		mpg.setTemplate(tc);

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("jiang.feng");
		gc.setOutputDir(xmlOutputPath);
		gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
		gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);

		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername(dbUser);
		dsc.setPassword(dbPassword);
		dsc.setUrl(jdbcUrl);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[]{tableName}); // 需要生成的表
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("mapper");
		pc.setXml("");
		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}
}
