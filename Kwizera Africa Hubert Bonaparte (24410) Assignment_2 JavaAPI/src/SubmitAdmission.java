import java.io.IOException;
import javax.mail.MessagingException;
import java.io.InputStream;
import java.net.Authenticator;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;





@WebServlet("/SubmitAdmission")
@MultipartConfig
public class SubmitAdmission extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // Send an email confirmation
        sendConfirmationEmail(email);

        // Redirect to index.html
        response.sendRedirect("index.html");
    }

    private void sendConfirmationEmail(String recipientEmail) {
        final String senderEmail = "quizerahubert@gmail.com"; // Your Gmail email address
        final String senderPassword = "cfsquqgszradqxxl"; // Your Gmail password

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
            message.setText("Dear Applicant,\n\nYour admission submission is complete. Thank you for applying!");

            Transport.send(message);

            System.out.println("Confirmation email sent to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle email sending errors here
        }
    }
}
