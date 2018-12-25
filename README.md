# spring-cloud
  spring cloud 学习项目 <br>
  spring-cloud.version: Greenwich.M3 <br>
  spring-boot.version: 2.1.0.RELEASE
# 没有db，哈哈。。。
# 结构说明:
	client(eureka feign client模块):
		client-sys(系统服务调用，包括：用户、组织机构、角色、菜单等服务调用)
		client-generate-code(代码生成服务调用)
	common(通用模块):
		common-client(eureka feign client的通用封装)
		common-redis(redis的通用封装)
		common-server(eureka server的通用封装，包括：数据库数据源、mybatis等)
		common-util(工具包)
	eureka-server-register-center(服务注册中心)
	moudle(各领域模型)
	server(eureka server模块)
		server-config(配置服务)
		server-generate-code(代码生成服务)
		server-sys(系统服务，包括：用户、组织机构、角色、菜单等服务)
	web(web展示层，只有页面展示和控制跳转，没有业务逻辑，没有数据库）

# 注意：
	1、JDK1.8、eclipse Neon；
	2、eureka server/clint 统一使用POST请求。对象参数只能有一个，要加注解@RequestBody；基本类型参数可以有多个，
	第一个加其后的加注解@RequestBody，其它的加@RequestParam。@RequestBody会转换成请求的body。@RequestParam为转换成url后面的参数。例子如下：
		@RequestMapping(value = "save", method = RequestMethod.POST)
		public void save(@RequestBody User entity, @RequestParam("i") int i) {
			userService.save(entity, currentLoginUser);
		}
		@RequestMapping(value = "save", method = RequestMethod.POST)
		public void save(@RequestBody String name, @RequestParam("i") int i) {
			userService.save(entity, currentLoginUser);
		}
