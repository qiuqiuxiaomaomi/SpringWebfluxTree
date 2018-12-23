# SpringWebfluxTree
Spring响应式编程技术研究

![](https://i.imgur.com/SMx98vh.png)

![](https://i.imgur.com/h7CJzh9.png)

<pre>
左侧是传统的基于Servlet的Spring Web MVC框架，右侧是5.0版本新引入的基于Reactive Streams
的Spring WebFlux框架，从上到下依次是Router Functions，WebFlux，Reactive Streams三个
新组件。

     1)Router Functions: 对标@Controller，@RequestMapping等标准的Spring MVC注解，提供
       一套函数式风格的API，用于创建Router，Handler和Filter。
     2)WebFlux: 核心组件，协调上下游各个组件提供响应式编程支持。
     3)Reactive Streams: 一种支持背压（Backpressure）的异步数据流处理标准，主流实现有
       RxJava和Reactor，Spring WebFlux默认集成的是Reactor。

     在Web容器的选择上，Spring WebFlux既支持像Tomcat，Jetty这样的的传统容器（前提是支持
     Servlet 3.1 Non-Blocking IO API），又支持像Netty，Undertow那样的异步容器。不管是
     何种容器，Spring WebFlux都会将其输入输出流适配成Flux<DataBuffer>格式，以便进行统一处理。

     值得一提的是，除了新的Router Functions接口，Spring WebFlux同时支持使用老的Spring MVC
     注解声明Reactive Controller。和传统的MVC Controller不同，Reactive Controller操作的
     是非阻塞的ServerHttpRequest和ServerHttpResponse，而不再是Spring MVC里
     的HttpServletRequest和HttpServletResponse。
</pre>