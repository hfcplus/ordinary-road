# 命令

在dos，或者idea中的Terminal中输入有的命令必须有 JAVA_HOME的环境变量，有些不需要(v, clean, validate)

在idea中的点点点不需要，我们一边用的最多的就是点点点，写这个就是为了应付面试

## 生命周期

|   命令   |                        解释                        |
| :------: | :------------------------------------------------: |
|  clean   |                        清除                        |
| validate |                检测pom是否正确完整                 |
| compile  |              编译，并且生成target文件              |
|   test   |  将test文件夹下的所有文件测试文件跑一遍，包含编译  |
| package  |             在target文件夹中生成jar包              |
|  verify  |  对集成测试的结果执行任何检查，以确保满足质量标准  |
| install  |        生成jar包，并且将jar包保存到本地仓库        |
|  deploy  | 最终的包复制到远程存储库以与其他开发人员和项目共享 |

## 解决jar包冲突

### jar包冲突

>在同一工程中引用了版本不同的依赖

### 解决方案 maven提供

* 最短路径优先

  ![image-20220310170124407](maven.assets/image-20220310170124407.png)

  > 没有其他声明，工程A中的Student类只会是D_2.jar包中的Student

* 路径相同，使用工程pom文件中先声明的

  ![image-20220310170347773](maven.assets/image-20220310170347773.png)

  ```xml
  <dependencies>
      <dependency>
          <groupId>com.hfcplus</groupId>
          <artifactId>B</artifactId>
          <version>1.0</version>
      </dependency>
          <dependency>
          <groupId>com.hfcplus</groupId>
          <artifactId>E</artifactId>
          <version>1.0</version>
      </dependency>
  <dependencies>
  ```

  >因为B的声明在E的前面，所以会使用B中包含D_1.jar中的Student类

* 手动排除指定jar包

  ![image-20220310170124407](maven.assets/image-20220310170124407.png)

  ```xml
  <dependencies>
      <dependency>
          <groupId>com.hfcplus</groupId>
          <artifactId>C</artifactId>
          <version>1.0</version>
          <!-- 手动排除不需要的jar包 -->
          <exclusions>
              <exclusion>
                  <groupId>com.hfcplus</groupId>
                  <artifactId>D</artifactId>
              </exclusion>
          </exclusions>
      </dependency>
          <dependency>
          <groupId>com.hfcplus</groupId>
          <artifactId>E</artifactId>
          <version>1.0</version>
      </dependency>
  <dependencies>
  ```

  >手动排除D_1.jar,所有自会调用D_2.jar包中的Student类