import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class RegisterServlet extends HttpServlet {

    protected void service ( HttpServletRequest request, HttpServletResponse response ) {
        /** request.getParameter(): 是常见的方法，用于获取单值的参数
         request.getParameterValues(): 用于获取具有多值的参数，比如注册时候提交的 "hobits"，可以是多选的。
         request.getParameterMap(): 用于遍历所有的参数，并返回Map类型。
         **/
        System.out.println ( "获取单值参数name:" + request.getParameter ( "name" ) );
        String[] hobits = request.getParameterValues ( "hobits" );
        System.out.println ( "获取具有多值的参数hobits:" + Arrays.asList ( hobits ) );

        System.out.println ( "通过getParameterMap遍历所有参数:" );
        Map <String, String[]> parameters = request.getParameterMap ();

        Set <String> paramterNames = parameters.keySet ();
        for (String param : paramterNames) {
            String[] value = parameters.get ( param );
            System.out.println ( param + ":" + Arrays.asList ( value ) );
        }
    }
}
