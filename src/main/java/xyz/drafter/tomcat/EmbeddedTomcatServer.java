package xyz.drafter.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author drafter
 * @date 2019/12/19
 * @desciption
 */
public class EmbeddedTomcatServer {

    // 手写嵌入式Tomcat
    public static void main(String[] args)  throws Exception {
        // 目录的绝对路径获取到
        String classPath = System.getProperty("user.dir");
        System.out.println(classPath);
        //D:\jcwl\work\tomcat-maven


        Tomcat tomcat = new Tomcat();
      //  tomcat.setPort(9090);
        Connector connector = tomcat.getConnector();
        connector.setPort(9091);

        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        Context context = tomcat.addContext(host,"/",classPath);
        if (context instanceof StandardContext){
            StandardContext standardContext = (StandardContext)context;
            standardContext.setDefaultContextXml("D:\\jcwl\\apache-tomcat-7.0.81\\conf\\web.xml");
            // 设置Servlet
            Wrapper wrapper = tomcat.addServlet("/","DemoServlet",new DemoServlet());
            wrapper.addMapping("/drafter");
        }
        tomcat.start();
        // 强制Tomcat Server等待，避免main线程执行结束后关闭
        tomcat.getServer().await();
    }
}
