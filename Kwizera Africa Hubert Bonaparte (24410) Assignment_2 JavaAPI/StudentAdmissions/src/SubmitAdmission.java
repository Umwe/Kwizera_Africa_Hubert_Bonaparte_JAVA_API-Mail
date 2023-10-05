import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






@WebServlet("/SubmitAdmission")
@MultipartConfig
public class SubmitAdmission extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // Send an email 
        sendConfirmationEmail(email);

       //Redirect
        response.sendRedirect("home.html");
    }

    private void sendConfirmationEmail(String recipientEmail) {
        final String senderEmail = "quizerahubert@gmail.com"; 
        final String senderPassword = "cfsquqgszradqxxl"; 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Admission Submission Confirmation");
            message.setText("Dear Applicant,\n\nYour admission submission is complete. Thank you for applying Soon you will get further feedback!");

            Transport.send(message);

            System.out.println("Confirmation email sent to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            
        }
    }
}
