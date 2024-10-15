package com.dartech.myschola.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(
            String type,
            String to,
            String subject,
            Map<String, String> params
    ) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(new InternetAddress("no-reply@myschola.com", "MySchola"));
        helper.setTo(to);
        helper.setSubject(subject);

        String htmlContent = loadHtmlTemplate("templates/"+ type + ".html");

        Set<String> keySet = params.keySet();

        for(String key : keySet) {
            htmlContent = htmlContent.replace("${" + key + "}", params.get(key));

        }

        htmlContent = htmlContent.replace("${first_name}", params.get("first_name"));

        helper.setText(htmlContent, true); // true indicates it's HTML content

        mailSender.send(message);
    }

    private String loadHtmlTemplate(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        StringBuilder htmlBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlBuilder.append(line).append("\n");
            }
        }

        return htmlBuilder.toString();
    }
}