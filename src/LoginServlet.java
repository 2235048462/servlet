import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 无论访问多少次LoginServlet,LoginServlet构造方法只会执行一次
    public LoginServlet () {
        System.out.println ( "LoginServlet 构造方法被调用..." );
    }

    // 无论访问多少次LoginServlet,init初始化只会执行一次
    public void init ( ServletConfig config ) {
        System.out.println ( "init(ServletConfig...)" );
    }

    // 销毁destroy()
    public void destroy () {
        System.out.println ( "destroy()..." );
    }

    // doGet(),doPost()执行前都会执行service(),且doGet(),doPost()都可通过service()重写实现
    protected void doPost ( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        String name = request.getParameter ( "name" );
        String password = request.getParameter ( "password" );

        /**
         * request.getRequestURL(): 浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有)" +
         * request.getRequestURI(): 浏览器发出请求的资源名部分，去掉了协议和主机名" +
         * request.getQueryString(): 请求行中的参数部分，只能显示以get方式发出的参数，post方式的看不到
         * request.getRemoteAddr(): 浏览器所处于的客户机的IP地址
         * request.getRemoteHost(): 浏览器所处于的客户机的主机名
         * request.getRemotePort(): 浏览器所处于的客户机使用的网络端口
         * request.getLocalAddr(): 服务器的IP地址
         * request.getLocalName(): 服务器的主机名
         * request.getMethod(): 得到客户机请求方式一般是GET或者POST
         **/

        System.out.println ( "浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有):" + request.getRequestURL () );
        System.out.println ( "浏览器发出请求的资源名部分，去掉了协议和主机名:" + request.getRequestURI () );
        System.out.println ( "请求行中的参数部分，只能显示以get方式发出的参数，post方式的看不到:" + request.getQueryString () );
        System.out.println ( "浏览器所处于的客户机的IP地址:" + request.getRemoteAddr () );
        System.out.println ( "浏览器所处于的客户机的主机名:" + request.getRemoteHost () );
        System.out.println ( "浏览器所处于的客户机使用的网络端口:" + request.getRemotePort () );
        System.out.println ( "服务器的IP地址:" + request.getLocalAddr () );
        System.out.println ( "服务器的主机名:" + request.getLocalName () );
        System.out.println ( "得到客户机请求方式一般是GET或者POST:" + request.getMethod () );

        /* 对name进行解码和编码,等同request.setCharacterEncoding("UTF-8")放在request.getParameter()之前,
         仅仅发送到浏览器的内容使用UTF-8编码*/
        byte[] bytes = name.getBytes ( "ISO-8859-1" );
        name = new String ( bytes, "UTF-8" );

        // String html = null;
        if ("admin".equals ( name ) && "123".equals ( password )) {
            // html = "<font color='green'>success</font>";
            request.getRequestDispatcher ( "success.html" ).forward ( request, response ); // 跳转至成功页面
        } else {
            // html = "<font color='red'>fail</font>";
            // 302表示临时跳转,如下:
            //response.sendRedirect ( "fail.html" ); // 跳转至失败页面
            // 301表示永久性跳转
            response.setStatus ( 301 );
            response.setHeader ( "Location" ,"fail.html");
        }

        // 返回中文响应,不仅发送到浏览器的内容使用UTF-8编码,且通知浏览器使用UTF-8编码方式进行显示
        response.setContentType ( "text/html;charset=UTF-8" );
        // PrintWriter printWriter = response.getWriter ();
        // printWriter.println ( html );

        System.out.println ( "name:" + name );
        System.out.println ( "password:" + password );
    }
}
