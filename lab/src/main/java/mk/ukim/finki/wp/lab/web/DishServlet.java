package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name="DishServlet", urlPatterns = "/dish")
public class DishServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;
    private final ChefService chefService;
    private final DishService dishService;

    public DishServlet(SpringTemplateEngine springTemplateEngine, ChefService chefService, DishService dishService) {
        this.springTemplateEngine = springTemplateEngine;
        this.chefService = chefService;
        this.dishService = dishService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.sendRedirect("/listChefs");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chefIdParam = req.getParameter("chefId");
        if (chefIdParam == null) {
            resp.sendRedirect("/listChefs");
            return;
        }

        Long chefId = Long.parseLong(chefIdParam);

        Chef chef = chefService.findById(chefId);
        if (chef == null) {
            resp.sendRedirect("/listChefs");
            return;
        }


        List<Dish> dishes = dishService.listDishes();

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("chef", chef);
        context.setVariable("dishes", dishes);


        springTemplateEngine.process("dishesList.html", context, resp.getWriter());
    }
}
