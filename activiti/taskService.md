https://blog.csdn.net/qq_31678877/article/details/52937178 

act_ru_task：任务表

 act_ru_identitylink：权限表（流程定义和用户组（用户）之间的权限数据）

 act_ru_variable：参数表

 act_hi_attachment：任务附件（可以以流的方式存储到act_ge_bytearray表中（父表），外键CONTENT_ID_（没有强制物理关系））

 act_hi_comment：任务评论和事件记录表（由type决定，"event"：事件的记录，"comment"：流程的评论数据）

BPMN2.0

# 1.创建任务

```java
//获取任务服务组件
TaskService taskService = engine.getTaskService();

//保存第一个Task，不设置ID
Task task1 = taskService.newTask();
taskService.saveTask(task1);

//保存第二个Task，设置ID
Task task2 = taskService.newTask("审核任务");
taskService.saveTask(task2);
```



# 2.删除任务

```java
// 删除task（不包括历史数据和子任务）
taskService.deleteTask("1");

// 删除task（包括历史数据和子任务）
taskService.deleteTask("2", true);

// 删除多个task（不包括历史数据和子任务）
List<String> ids = new ArrayList<String>();
ids.add("3");
ids.add("4");
taskService.deleteTasks(ids);

//删除多个task（包括历史数据和子任务）
ids = new ArrayList<String>();
ids.add("5");
ids.add("6");
taskService.deleteTasks(ids, true);

// 再删除ID为3的task，此时3的历史数据也会被删除（如果有的话）
taskService.deleteTask("3", true);
```



# 3.设置任务持有人

```java
taskService.setOwner(task1.getId(), user.getId());

//该用户持有的任务数量
taskService.createTaskQuery().taskOwner(user.getId()).count()
```



# 4.设置任务受理人

```java
taskService.setAssignee(task1.getId(), user.getId());
//该用户受理的任务数量
taskService.createTaskQuery().taskAssignee(user.getId()).count()
```



# 5.任务候选组(绑定权限) act_ru_identitylink

```java
taskService.addCandidateGroup("task1", groupA.getId());
```



# 6.任务候选人(绑定权限) act_ru_identitylink

```java
taskService.addCandidateUser("task1", user.getId());
```



# 7.查询任务

```java
  //根据用户组查询任务
 List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupA.getId()).list();

  //根据用户查询任务
 tasks = taskService.createTaskQuery().taskCandidateUser(user.getId()).list(); 

  //查询权限数据（任务-->权限）
 List<IdentityLink> links = taskService.getIdentityLinksForTask(task.getId());

  //调用taskCandidateGroupIn
 List<String> groupIds = new ArrayList<String>();
 groupIds.add(groupA.getId());
 groupIds.add(groupB.getId());
 tasks = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).list();
```

 

# 8.添加任务权限数据

```java
  //调用addGroupIdentityLink方法
 taskService.addGroupIdentityLink(task1.getId(), groupA.getId(), IdentityLinkType.CANDIDATE); -->addCandidateGroup(); 任务候选组

 //调用addUserIdentityLink方法
 taskService.addUserIdentityLink(task1.getId(), user.getId(), IdentityLinkType.CANDIDATE); --> setOwner(); 任务持有人
 taskService.addUserIdentityLink(task1.getId(), user.getId(), IdentityLinkType.OWNER); -->addCandidateUser() 任务候选人
 taskService.addUserIdentityLink(task1.getId(), user.getId(), IdentityLinkType.ASSIGNEE); --> setAssignee(); 任务受理人
```







#  9.删除权限

```java
  //删除用户组权限
  taskService.deleteGroupIdentityLink(task1.getId(), groupA.getId(),
  IdentityLinkType.CANDIDATE);
  taskService.deleteCandidateGroup(task1.getId(), groupA.getId());

  //删除用户权限
  taskService.deleteUserIdentityLink(task1.getId(), user.getId(), IdentityLinkType.OWNER);
  taskService.deleteCandidateUser(task1.getId(), user.getId());
```





 

# 10.任务参数

```java
 参数作用域

  setVariable：整个流程中都可以使用

  setVariableLocal：当前任务中使用

 两个作用域不交集 确定

  taskService.setVariableLocal(task.getId(), "target", "欧洲");

  Object data3 = taskService.getVariableLocal(task.getId(), "target");

  //初始化参数

 Map<String,Object> vars = new HashMap<String, Object>();

 vars.put("days", 10);

 vars.put("target", "欧洲");

 taskService.setVariables(task1.getId(), vars);

  Date d = new Date();

 short s = 3;

 //设置各种基本类型参数

 taskService.setVariable(task1.getId(), "arg0", false);
 Object arg0 = taskService.getVariable(task1.getId(), "arg0");
 taskService.setVariable(task1.getId(), "arg1", d);
 taskService.setVariable(task1.getId(), "arg2", 1.5D);
 taskService.setVariable(task1.getId(), "arg3", 2);
 taskService.setVariable(task1.getId(), "arg4", 10L);
 taskService.setVariable(task1.getId(), "arg5", null);
 taskService.setVariable(task1.getId(), "arg6", s);
 taskService.setVariable(task1.getId(), "arg7", "test");
 taskService.setVariable(task1.getId(), "target", new TestVO("北京"));

  /**
  * 序列化对象
  */
  public class TestVO implements Serializable {
      private String name;
      public TestVO(String name) {
         this.name = name;
      }
      public String getName() {
          return this.name;
      }
  }
```



 

 



# 11.附件

```java
  // 部署流程描述文件
 Deployment dep = repositoryService.createDeployment()
 .addClasspathResource("bpmn/vacation.bpmn").deploy();

 // 查找流程定义
 ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
 .deploymentId(dep.getId()).singleResult();

 // 启动流程
 ProcessInstance pi = runtimeService
 .startProcessInstanceById(pd.getId());

 // 查找任务
 Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
 .singleResult();

 // 设置任务附件
 Attachment att1 = taskService.createAttachment("web url", task.getId(), pi.getId(), "Attachement1", 
 "163 web page", "http://www.163.com");

 // 创建图片输入流
 InputStream is = new FileInputStream(new File("resource/artifact/result.png"));

 // 设置输入流为任务附件
 Attachment att2 = taskService.createAttachment("web url", task.getId(), pi.getId(), "Attachement2", 
 "Image InputStream", is);

 // 根据流程实例ID查询附件
 List<Attachment> attas1 = taskService.getProcessInstanceAttachments(pi.getId());
 System.out.println("流程附件数量：" + attas1.size());

 // 根据任务ID查询附件
 List<Attachment> attas2 = taskService.getTaskAttachments(task.getId());
 System.out.println("任务附件数量：" + attas2.size());

 // 根据附件ID查询附件
 Attachment attResult = taskService.getAttachment(att1.getId());
 System.out.println("附件1名称：" + attResult.getName());

 // 根据附件ID查询附件内容 
 InputStream stream1 = taskService.getAttachmentContent(att1.getId());
 System.out.println("附件1的输入流：" + stream1);
 InputStream stream2 = taskService.getAttachmentContent(att2.getId());
 System.out.println("附件2的输入流：" + stream2);
```



 



# 12.事件记录（事件和评论）

```java
  // 部署流程描述文件
 Deployment dep = repositoryService.createDeployment().addClasspathResource("bpmn/vacation.bpmn").deploy();

 // 查找流程定义
 ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();

 // 启动流程
 ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());

 // 查找任务
 Task task = taskService.createTaskQuery().processInstanceId(pi.getId())
 .singleResult();

 // 调用各个记录事件的方法 
 taskService.addComment(task.getId(), pi.getId(), "this is comment message"); 
 taskService.addUserIdentityLink(task.getId(), "1", "user");
 taskService.deleteUserIdentityLink(task.getId(), "1", "user");
 taskService.addGroupIdentityLink(task.getId(), "1", "group");
 taskService.deleteGroupIdentityLink(task.getId(), "1", "group");
 Attachment atta = taskService.createAttachment("test", task.getId(), pi.getId(), "test", "test", "");
 taskService.deleteAttachment(atta.getId());

 // 查询Comment和Event
 List<Comment> comments = taskService.getTaskComments(task.getId());
 System.out.println("总共的评论数量：" + comments.size());
 List<Event> events = taskService.getTaskEvents(task.getId());
 System.out.println("总共的事件数量：" + events.size());

  // 查询事件与评论
 List<Comment> commonts1 = taskService.getProcessInstanceComments(pi.getId());
 System.out.println("流程评论（事件）数量：" + commonts1.size());
 commonts1 = taskService.getTaskComments(task.getId());
 System.out.println("
 任务评论数量：" + commonts1.size());
 List<Event> events = taskService.getTaskEvents(task.getId());
 System.out.println("事件数量：" + events.size());
```

 

# 13.任务声明(签收)

```java
  taskService.claim(task.getId(), userID);
  只能声明给同一个用户（可多次），不能再次声明，和setAssignee方法类似
```



# 14.任务完成

```java
complete参数为两个时，效果同setVariable
只有一个参数taskID时，会将完成的相关Task数据从数据表中删除，放到历史数据表中，然后让执行流继续往下，如果发现这个任务为流程中的最后一个任务，则会连同实例的数据也一并删除，并且按照历史配置来记录流程的历史数据。
//complete(String taskId)//当前流程节点设置完成并走向下一个节点
//complete(String taskId, Map<String,Object> variables) //当前流程节点设置完成并走向下一个节点,同时设置参数


 // 部署流程描述文件
 Deployment dep = repositoryService.createDeployment().addClasspathResource("bpmn/vacation2.bpmn").deploy();

 // 查找流程定义
 ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(dep.getId()).singleResult();

 // 启动流程
 ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId());

 // 查找任务
 Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

 // 调用complete方法完成任务，传入参数
 Map<String, Object> vars = new HashMap<String, Object>();
 vars.put("days", 2);
 taskService.complete(task.getId(), vars);

 // 再次查找任务
 task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

 //得到参数
 Integer days = (Integer)taskService.getVariable(task.getId(), "days");
 if (days > 5) {
 	System.out.println("大于5天，不批");
 } else {
 	System.out.println("小于5天，完成任务，流程结束");
 	taskService.complete(task.getId());
 }
```



