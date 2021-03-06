# SpringWebfluxTree
Spring响应式编程技术研究

![](https://i.imgur.com/SMx98vh.png)

![](https://i.imgur.com/h7CJzh9.png)

<pre>
    在Java 7推出异步I/O库，以及Servlet3.1增加了对异步I/O的支持之后，Tomcat等Servlet容器也随
    后开始支持异步I/O，然后Spring WebMVC也增加了对Reactor库的支持，所以上边第4）步如果不是
    将spring-boot-starter-web替换为spring-boot-starter-WebFlux，而是增加reactor-core的依
    赖的话，仍然可以用注解的方式开发基于Tomcat的响应式应用。
</pre>

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

<pre>
WebFlux:

          WebFlux模块的名称是Spring-webFlux，名称中的Flux来源于Reactor中的类Flux,该模块中包含了
      对反应时Http,服务器推送事件和WebSocket的客户端和服务器端的支持。
          在服务器端,WebFlux支持两种不同的编程模型。
              1）SpringMVC中使用的基于Java注解的方式；
              2）第二种是基于Java8的lamda表达式的函数式编程模型。

          这两种编程模型只是在代码编写方式上存在不同，他们运行在同样的反应式底层架构上，因此在运行
      时是相同的。WebFlux需要底层提供运行时的支持，WebFlux可以运行在支持Servlet3.1非阻塞 IO API
      的Servlet容器上。或是其他异步运行时环境，如Netty, Undertow.
</pre>

<pre>
传统Spring处理请求的方式：
      Controller定义定义对Request的处理逻辑的方式，主要有两个点：
           1）方法定义处理逻辑；
           2）然后用@RequestMapping注解定义好这个方法对什么样url进行响应

在WebFlux的函数式开发模式中，我们用HandlerFunction和RouterFunction来实现上边这两点
      1）HandlerFunction相当于Controller中的具体处理方法，输入为请求，输出为装在Mono中的响应
      2）RouterFunction，顾名思义，路由，相当于@RequestMapping，用来判断什么样的url映射到
         那个具体的HandlerFunction，输入为请求，输出为装在Mono里边的Handlerfunction
</pre>

<pre>
各个数据库都开始陆续推出异步驱动，目前Spring Data支持的可以进行响应式数据访问的数据库有
      MongoDB、
      Redis、
      Apache Cassandra、
      CouchDB
</pre>

<pre>
Reactor类型：

      Flux<T>:
             Flux类似RxJava的Observable，它可以触发零到多个事件，并根据实际情况结束处理
         或触发错误。

      Mono<T>:
             Mono最多只触发一个事件，它跟RxJava的Single，Maybe类似，所以可以把Mono<Void>,
         用于在异步任务完成时发出通知。

      Reactive Streams 是规范，Reactor 实现了 Reactive Streams。Web Flux 以 Reactor 为
      基础，实现 Web 领域的反应式编程框架。

      其实，对于大部分业务开发人员来说，当编写反应式代码时，我们通常只会接触到 Publisher 这个
      接口，对应到 Reactor 便是 Mono 和 Flux。对于 Subscriber 和 Subcription 这两个接
      口，Reactor 必然也有相应的实现。但是，这些都是 Web Flux 和 Spring Data Reactive 这样
      的框架用到的。如果不开发中间件，通常开发人员是不会接触到的。

      比如，在 Web Flux，你的方法只需返回 Mono 或 Flux 即可。你的代码基本也只和 Mono 或 Flux 
      打交道。而 Web Flux 则会实现 Subscriber ，onNext 时将业务开发人员编写的 Mono 或 Flux 
      转换为 HTTP Response 返回给客户端。
</pre>

<pre>
服务器推送：

	我们可能会遇到一些需要网页与服务器端保持连接（起码看上去是保持连接）的需求，比如类似微信网页
    版的聊天类应用，比如需要频繁更新页面数据的监控系统页面或股票看盘页面。我们通常采用如下几种技术：
	
	短轮询：
        利用ajax定期向服务器请求，无论数据是否更新立马返回数据，高并发情况下可能会对服务器和带宽
        造成压力；
	长轮询：
        利用comet不断向服务器发起请求，服务器将请求暂时挂起，直到有新的数据的时候才返回，相对短
        轮询减少了请求次数；
	SSE：
        服务端推送（Server Send Event），在客户端发起一次请求后会保持该连接，服务器端基于该连
        接持续向客户端发送数据，从HTML5开始加入。
	Websocket：
        这是也是一种保持连接的技术，并且是双向的，从HTML5开始加入，并非完全基于HTTP，适合于频繁
        和较大流量的双向通讯场景。
</pre>

Response Streaming数据流

![](https://i.imgur.com/lN5CUDm.png)