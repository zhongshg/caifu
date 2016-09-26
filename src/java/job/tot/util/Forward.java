package job.tot.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Forward {

    public static void forward(HttpServletRequest request, HttpServletResponse response, String url) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
