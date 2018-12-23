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
</pre>