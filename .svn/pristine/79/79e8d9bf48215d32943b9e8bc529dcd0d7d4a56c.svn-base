package com.cyberway.core.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * DaoTest基类. 默认载入/context/*.xml 子类属性只须声明setter函数,就会自动根据类型进行注入
 * 将OpenSessionInTest,并默认在每个测试方法结束时进行回滚
 * 另,因为setup()函数包含了session管理流程，用户如果需要进行初始化设置，请重载{@link #onSetUpBeforeTransaction()}
 * 
 * @author caiw
 */
abstract public class BaseServiceTest extends AbstractTransactionalDataSourceSpringContextTests{

	public BaseServiceTest() {
		// 默认为Rollback，如果希望提交测试结果，取消下句的注释.
		// setDefaultRollback(false);
	}

	/**
	 * applicaitonContext配置文件 默认为context目录下所有文件
	 * 如果子函数需要限定载入的applicatonContext.xml,重载此函数
	 * 
	 * @see org.springframework.test.AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
	 */
	public String[] getConfigLocations() {
		return new String[] { "classpath*:context/**/*.xml" };
	}

	protected void onSetUpBeforeTransaction() throws Exception {
	}

}
