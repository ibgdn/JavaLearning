package servlet;

import bean.Order;
import bean.OrderItem;
import bean.User;
import dao.OrderDAO;
import dao.OrderItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderCreateServlet", urlPatterns = {"/createOrder"})
public class OrderCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect("/login.jsp");
            return;
        }

        Order order = new Order();
        order.setUser(user);

        new OrderDAO().insert(order);

        List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("ois");
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
            new OrderItemDAO().insert(orderItem);
        }

        orderItems.clear();

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("订单创建成功！");
    }
}
