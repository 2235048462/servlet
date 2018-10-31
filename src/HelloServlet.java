import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

public class HelloServlet extends HttpServlet {
    public void init ( ServletConfig config ) {
        System.out.println ( "init of Hello Servlet..." );
    }

    public void doGet ( HttpServletRequest request, HttpServletResponse response ) {

        /**request.getHeader() 获取浏览器传递过来的头信息。
         比如getHeader("user-agent") 可以获取浏览器的基本资料，这样就能判断是firefox、IE、chrome、或者是safari浏览器
         request.getHeaderNames() 获取浏览器所有的头信息名称，根据头信息名称就能遍历出所有的头信息
         **/

        /**访问HelloServlet获取如下头信息:
         host: 主机地址
         user-agent: 浏览器基本资料
         accept: 表示浏览器接受的数据类型
         accept-language: 表示浏览器接受的语言
         accept-encoding: 表示浏览器接受的压缩方式，是压缩方式，并非编码
         connection: 是否保持连接
         cache-control: 缓存时限*/
        Enumeration <String> headerNames = request.getHeaderNames ();
        while (headerNames.hasMoreElements ()) {
            String header = headerNames.nextElement ();
            String value = request.getHeader ( header );
            System.out.printf ( "%s\t%s%n", header, value );
        }
        try {
            //不使用缓存,使用缓存加快页面的加载,降低服务端的负担,但看到过时的消息
            response.setDateHeader ( "Expires", 0 );
            response.setHeader ( "Cache-Control", "no-cache" );
            response.setHeader ( "pragma", "no-cache" );

            /*进行解码和编码, request.setCharacterEncoding ( "UTF-8" ), 仅仅发送到浏览器的内容使用UTF - 8 编码
            response.setContentType ( "text/html;charset=UTF-8" ) 返回中文响应,
            不仅发送到浏览器的内容使用UTF - 8 编码, 且通知浏览器使用UTF - 8 编码方式进行显示*/
            // response.setContentType ( "text/html;charset=UTF-8" );
            response.setCharacterEncoding ( "UTF-8" );

            response.getWriter ().println ( "<h1>Hello Servlet!</h1>" );
            response.getWriter ().println ( new Date ().toLocaleString () );
            // response.setContentType ( "text/lol" ); // 浏览器无法识别"text/lol"格式
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}