import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class UploadPhotoServlet extends HttpServlet {
    public void doPost ( HttpServletRequest request, HttpServletResponse response ) {
        String filename = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory ();
            ServletFileUpload upload = new ServletFileUpload ( factory );
            // 设置上传文件的大小限制1M
            factory.setSizeThreshold ( 1024 * 1024 );

            List items = null;
            try {
                items = upload.parseRequest ( request );
            } catch (FileUploadException f) {
                f.printStackTrace ();
            }

            Iterator iterator = items.iterator ();
            while (iterator.hasNext ()) {
                FileItem fileItem = (FileItem) iterator.next ();


                if (!fileItem.isFormField ()) { // 判断是常规字段还是提交文件,当fileItem.isFormField ()返回true表示常规字段

                    //根据时间戳创建头像文件
                    filename = System.currentTimeMillis () + ".png";

                    // 通过getRealPath获取上传文件夹,项目在web下时,会自动获取web/uploaded
                    String photoFolder = request.getServletContext ().getRealPath ( "uploaded" );

                    File file = new File ( photoFolder, filename );
                    file.getParentFile ().mkdirs ();

                    // 通过fileItem.getInputStream()获取浏览器上传的文件输入流
                    InputStream inputStream = fileItem.getInputStream ();

                    // 复制文件
                    FileOutputStream outputStream = new FileOutputStream ( file );
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read ( bytes ))) {
                        outputStream.write ( bytes, 0, length );
                    }

                    //显示文件内容,注意编码错误,出现乱码
                    System.out.println ( fileItem.getFieldName () + "@@##" ); // 字段名称
                    System.out.println ( fileItem.getString () + "@@$$" ); // 字段值

                    outputStream.close ();
                } else {
                    System.out.println ( fileItem.getFieldName () );
                    String value = fileItem.getString ();
                    value = new String ( value.getBytes ( "ISO-8859-1" ), "UTF-8" );
                    System.out.println ( value );
                }
            }

            String html = "<img width='64px' height='64px' src='uploaded/%s'>";
            response.setContentType ( "text/html" );
            PrintWriter printWriter = response.getWriter ();
            printWriter.format ( html, filename );

        } catch (FileNotFoundException f) {
            f.printStackTrace ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
