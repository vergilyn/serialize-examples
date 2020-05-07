# fasterxml-jackson-examples

+ [\[github\] fasterxml jackson](https://github.com/FasterXML/jackson)
+ [jackson-docs](https://github.com/FasterXML/jackson-docs)

- [Java JSON Jackson Introduction](http://www.studytrails.com/java/json/java-jackson-introduction/)
- [Jackson详细介绍](https://blog.csdn.net/u012129558/article/details/78772615)

jackson 开源项目应用可以参考 spring-data-xx (推荐 spring-data-elasticsearch，代码相对简单)。

## FAQ

### 1. jackson is thread safe?

> [Jackson线程安全问题](https://blog.csdn.net/baichoufei90/article/details/102913336)  
> 虽然Jackson ObjectMapper实例线程安全，**但最好不要在多线程场景使用一个ObjectMapper，因为这样可能造成大多线程等待的情况，效率低下。**  
> 建议使用`ObjectPool`。  
> 但我看hadoop源码里面每次都是新创建ObjectMapper实例来使用`new ObjectMapper()`

> [单例模式和多例模式的性能测试，每个模式都测试两轮（单线程，多线程)](https://blog.csdn.net/luckyman98/article/details/104082170)  
> **不管是多线程还是单线程，Jackson的ObjectMapper在单例模式下性能优于多例模式。**

