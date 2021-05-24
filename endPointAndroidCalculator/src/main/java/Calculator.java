import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class Calculator extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        String num1 = request.getParameter("num1");
        String num2 = request.getParameter("num2");
        String operator = request.getParameter("operator").replace(" ","+");

        String result = calculate(num1,num2,operator);
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        System.out.println(num1 + operator + num2 + "="+result);
        out.println("<h1>" + num1 + operator + num2 + "="+result +"</h1>");
        out.println("<p>"+ new Date().toString() +"</p>");
    }

    public static String calculate(String first_num, String second_num, String operator) {
        double num1 = Double.parseDouble(first_num);
        double num2 = Double.parseDouble(second_num);
        double sum = 0;
        if (operator.equals("+")) {
            sum = num1 + num2;
        }
        if (operator.equals("-")) {
            sum = num1 - num2;
        }
        if (operator.equals("×")) {
            sum = num1 * num2;
        }
        if (operator.equals("÷")) {
            sum = num1 / num2;
        }
        return doubleTrans(sum);
    }

    //when the num behind point is 0, just show the num. 3.0 shows 3
    public static String doubleTrans(double num)
    {
        if(num % 1.0 == 0){
            return String.valueOf((long)num);
        }
        return String.valueOf(num);
    }

}