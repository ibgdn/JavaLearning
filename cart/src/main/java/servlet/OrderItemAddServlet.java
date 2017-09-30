package servlet;

import bean.OrderItem;
import bean.Product;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderItemAddServlet", urlPatterns = {"/addOrderItem"})
public class OrderItemAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int num = Integer.parseInt(request.getParameter("num"));
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = new ProductDAO().getProduct(pid);

        OrderItem orderItem = new OrderItem();
        orderItem.setNum(num);
        orderItem.setProduct(product);

        List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("ois");

        if(orderItems == null){
            orderItems = new ArrayList<OrderItem>();
            request.getSession().setAttribute("ois",orderItems);
        }

        // 判断是否是同一种类的产品
        boolean found = false;
        for (OrderItem orderi : orderItems) {
            if(orderi.getProduct().getId() == orderItem.getProduct().getId()){
                orderi.setNum(orderi.getNum() + orderItem.getNum());
                found = true;
                break;
            }
        }

        if(!found){
            orderItems.add(orderItem);
        }

        response.sendRedirect("/listOrderItem");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}