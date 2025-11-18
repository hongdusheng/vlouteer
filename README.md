# 高考志愿智能推荐系统

## 一、项目简介
本项目是一个前后端分离的高考志愿填报与智能推荐系统，提供学生信息管理、院校及专业管理、志愿填报、智能推荐分析等功能，帮助考生根据成绩与偏好更合理地填报志愿。

## 二、功能模块
- 首页：展示系统概览与入口导航。
- 学生管理：维护学生基本信息、成绩等数据。
- 院校管理：
  - 院校列表：查看和维护高校信息。
  - 专业管理：管理院校开设专业及相关信息。
  - 院校查询：按条件检索院校。
- 志愿填报：
  - 志愿概览：查看当前志愿方案整体情况。
  - 填报志愿：为学生配置和编辑志愿列表。
  - 志愿管理：对历史志愿方案进行管理与维护。
- 智能推荐：
  - 志愿推荐：根据成绩与偏好推荐志愿方案。
  - 冲突检测：检查志愿方案中的冲突与不合理之处。
  - 录取概率：估算院校专业的录取概率。
  - 就业前景：基于专业的就业情况进行分析。
  - 分数线分析：按省份和院校分数线做对比分析。
  - 志愿综合分析：对整套志愿方案进行综合评估。
  - 院校专业匹配：根据学生能力和偏好匹配院校与专业。
- 系统管理：系统基础配置与用户管理等。

## 三、技术栈
- 前端：
  - Vue 3
  - Vite
  - Vue Router 4
  - Element Plus
  - Axios
- 后端：
  - Spring Boot 3
  - MyBatis / MyBatis-Spring-Boot
  - MySQL
  - Jakarta Servlet API

## 四、项目结构
```text
.
├─ vlouteer_front/          # 前端工程（Vue3 + Vite）
│  ├─ src/
│  │  ├─ views/             # 页面视图（学生、院校、志愿、推荐、系统等模块）
│  │  ├─ api/               # 前端接口封装
│  │  ├─ layout/            # 通用布局
│  │  └─ utils/             # 工具方法（如请求封装）
│  └─ vite.config.js        # Vite 配置
└─ volunteer_back/          # 后端工程（Spring Boot）
   ├─ src/main/java/...     # 业务代码
   ├─ src/main/resources/
   │  ├─ application.properties  # 应用与数据源配置
   │  ├─ mapper/                 # MyBatis 映射文件
   │  └─ static/                 # 静态资源与文档
   └─ pom.xml               # Maven 构建配置
```

## 五、环境要求
- Node.js 16+，npm
- JDK 17
- Maven 3.x
- MySQL 5.7+/8.x

## 六、快速开始

### 1. 克隆项目
```bash
git clone https://github.com/hongdusheng/vlouteer.git
cd vlouteer
```

### 2. 配置数据库
- 在 MySQL 中创建数据库，例如：`gaokao_adviser`。
- 导入建表脚本（任选其一）：
  - `vlouteer_front/src/assets/建表语句.txt`
  - 或 `volunteer_back/src/main/resources/static/建表语句.txt`
- 如有需要，可导入存储过程：`volunteer_back/src/main/resources/static/存储过程.txt`。
- 修改后端数据源配置：`volunteer_back/src/main/resources/application.properties`：
  - `spring.datasource.url=jdbc:mysql://<host>:<port>/gaokao_adviser`
  - `spring.datasource.username=<你的用户名>`
  - `spring.datasource.password=<你的密码>`

### 3. 启动后端服务
```bash
cd volunteer_back
mvn spring-boot:run
```
- 默认访问地址：`http://localhost:8080`。

### 4. 启动前端项目
```bash
cd vlouteer_front
npm install
npm run dev
```
- 默认访问地址（Vite 默认）：`http://localhost:5173`。

### 5. 生产构建
```bash
cd vlouteer_front
npm run build
```
- 打包后的前端文件位于 `vlouteer_front/dist/`。
- 如需由后端统一提供静态资源，可将打包后的 `dist` 拷贝到：
  - `volunteer_back/src/main/resources/static/dist`。

## 七、接口文档
- 前端接口说明：`vlouteer_front/src/assets/api-doc.md`。
- 后端接口文档（或示例）：`volunteer_back/src/main/resources/static/api-doc(1).md`。

## 八、其他说明
- 登录与权限：前端使用 localStorage 存储 `token`，路由守卫会在未登录时自动跳转登录页。
- 配置安全性：请在正式环境中修改 `application.properties` 中的数据库地址、用户名与密码，避免将真实密码提交到公共仓库。
- 如需补充更详细的业务说明或部署文档，可在本 README 基础上继续扩展。



## 九、系统架构与接口约定

- 系统架构：前端单页应用（Vue 3 + Vite）+ 后端 RESTful API（Spring Boot）+ MySQL 数据库。
- 接口访问方式：
  - 开发环境：前端通过 Axios 请求 `/api/...`，由 Vite 代理转发到 `http://localhost:8080/...`（见 `vite.config.js`）。
  - 生产环境：可在 Nginx / 网关中配置同样的反向代理，或将前端的 `baseURL` 直接指向后端真实地址。
- 统一响应结构：后端使用 `Result<T>` 封装返回值，一般包含 `code`、`message`、`data` 三部分，例如：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "...": "..."
  }
}
```

- 约定：
  - `code = 200`：请求成功；
  - `code = 401`：未登录或登录态失效，前端会清除 token 并跳转登录页；
  - `code = 500`：服务器内部错误，前端会弹出错误提示。

## 十、后端代码结构说明

后端主包路径为 `com.itcao.volunteer_back_system`，主要子包说明如下：

- `controller`：对外暴露的 REST 接口控制器，例如用户、院校、专业、志愿、推荐等模块。
- `service`：业务逻辑层，封装具体业务规则与流程。
- `mapper`：MyBatis 映射接口，对应 `resources/mapper` 下的 XML 文件。
- `pojo`：实体类/持久化对象，对应数据库中的表结构。
- `Dto`：数据传输对象，用于接口入参或复杂返回数据封装。
- `common`：通用封装，例如 `Result`、`PageResult` 等。
- `config`：全局配置（如跨域、拦截器配置等）。
- `filter`：过滤器相关配置（如登录校验、权限控制等）。

## 十一、开发与调试建议

- 日志查看：
  - 在 `application.properties` 中已开启 MyBatis SQL 日志输出，可在控制台查看执行的 SQL 语句，便于调试。
- 跨域与代理：
  - 本地开发依赖 Vite 代理 `/api` 到 `http://localhost:8080`，如修改后端端口或上下文路径，请同步调整 `vite.config.js` 中的 `target` 或后端 `server.port`。
- 接口联调：
  - 推荐先通过 Postman / Apifox 等工具直接调用后端接口（参考 `api-doc.md`），确认业务逻辑正常后再接入前端页面。

## 十二、部署示例（单机部署）

1. **打包前端**：
   ```bash
   cd vlouteer_front
   npm install
   npm run build
   ```
   - 生成的静态文件位于 `vlouteer_front/dist/`。
2. **拷贝静态资源到后端**（可选做法）：
   - 将 `vlouteer_front/dist` 目录整体拷贝到 `volunteer_back/src/main/resources/static/dist`，
   - 或在服务器上拷贝到与 Spring Boot 可执行 Jar 同级的静态资源目录，根据实际部署方式调整。
3. **打包后端并运行**：
   ```bash
   cd volunteer_back
   mvn clean package -DskipTests
   java -jar target/volunteer_back_system-0.0.1-SNAPSHOT.jar
   ```
   - 请确保部署环境中的 `application.properties` 中数据库连接信息、端口号已根据服务器环境修改。
4. **配置反向代理（以 Nginx 为例，非必需）**：

   - 可将所有 `/` 请求转发到后端应用，或将静态资源由 Nginx 直接托管、API 请求转发到 Spring Boot 服务。

## 十三、账号与数据初始化说明

- 初始账号：
  - 系统示例接口文档中使用了 `admin`、`student1` 等账号作为示例值，实际可根据建表 SQL 自行在用户表中插入管理员和学生账号。
  - 建议至少创建一个管理员账号（如 `admin`），用于登录后台管理界面。
- 权限角色：
  - 系统当前主要存在 `admin`（管理员）、`student`（学生）等角色，具体字段可参考用户相关接口与数据库表结构。
- 测试数据：
  - 可通过执行项目提供的建表脚本和手动插入少量院校、专业、学生以及志愿信息，方便在前端页面中直观查看各模块功能效果。
