/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import JavaClasses.User;
import JavaClasses.SQLStatements;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author felix
 */
public class UserEditController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String desc = request.getParameter("userDesc");
        String pass = request.getParameter("userPass");
        String email = request.getParameter("userMail");
        String changingPass = request.getParameter("changePasswordConfirm");
        System.out.println(email + " + " + desc + " + " + pass);
        HttpSession session = request.getSession();

        User usr = new User();
        usr.setDescription(desc);
        usr.setEmailAddress(email);
        if (pass == "") {
            User tempUsr = SQLStatements.SelectUser(usr);
            usr.setPassword(tempUsr.getPassword());
            if (SQLStatements.UpdateUser(usr)) {
                session.setAttribute("emailAddress", email);
                response.sendRedirect("index.jsp");
            }
        } else {
            if (!changingPass.equals("yes")) {
                session.setAttribute("userDesc", desc);
                session.setAttribute("userPass", pass);
                session.setAttribute("emailAddress", email);
                response.sendRedirect("changePassword.jsp");
            }
        }

        if (changingPass.equals("yes")) {
            String givenCurrentPass = request.getParameter("currentPass");
            System.out.println(givenCurrentPass);
            User tempUsr2 = new User();
            tempUsr2.setEmailAddress(email);
            tempUsr2 = SQLStatements.SelectUser(tempUsr2);
            System.out.println(tempUsr2.getPassword());
            if (givenCurrentPass.equals(tempUsr2.getPassword())) {
                usr.setPassword(pass);
                if (SQLStatements.UpdateUser(usr)) {
                    session.setAttribute("emailAddress", email);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Servlet UsersRegisterController</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1><script>alert(\"Data changed successfully\"); window.location='index.jsp';</script></h1>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
            } else {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet UsersRegisterController</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1><script>alert(\"Given password doesn't match the current password\"); window.location='index.jsp';</script></h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
