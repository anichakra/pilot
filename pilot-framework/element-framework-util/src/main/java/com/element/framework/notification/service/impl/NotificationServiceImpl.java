package me.anichakra.poc.pilot.framework.notification.service.impl;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import me.anichakra.poc.pilot.framework.notification.domain.Mail;
import me.anichakra.poc.pilot.framework.notification.service.NotificationService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@PropertySource("classpath:email.properties")
public class NotificationServiceImpl implements NotificationService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Environment env;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public boolean sendEmail(String from, String to, String cc, String subject, String msgBody,
            boolean htmlContent) {
        boolean isSuccessFull = false;
        String messageText = "";
        String templateName = "blankTemplate.ftl";
        Map<String, Object> dataMap = new HashMap<String, Object>();

        try {

            mailSender.setHost(env.getProperty("element.notofication.host"));
            LOGGER.debug("HostName : " + mailSender.getHost());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);

            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate(templateName);
            dataMap.put("msgBody", msgBody);
            messageText = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            if (htmlContent == true) {
                helper.setText(messageText, true);
            } else {
                helper.setText(messageText);
            }
            mailSender.send(mimeMessage);
            isSuccessFull = true;
        } catch (Exception ex) {
            LOGGER.debug(ex);
        }

        return isSuccessFull;
    }

    public boolean sendEmail(Mail email, Map<String, Object> dataMap, Properties settings)
            throws MessagingException {

        boolean isSuccessFull = false;
        String messageText = "";
        String templateName = email.getTemplateName();

        try {
            mailSender.setHost(settings.getProperty("host"));
            LOGGER.debug("HostName : " + mailSender.getHost());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(email.getFrom());
            if (email.getToList().size() > 0) {
                helper.setTo(email.getToList().toArray(new String[email.getToList().size()]));
            }

            if (email.getCcList().size() > 0) {
                helper.setCc(email.getCcList().toArray(new String[email.getCcList().size()]));
            }

            helper.setSubject(email.getSubject());
            /*
             * Map<String, Object> dataMap = new HashMap<String, Object>(); dataMap.put("userName",
             * "<b>User Name</b>"); dataMap.put("userName2", "User name 2");
             */

            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate(templateName);

            messageText = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap);

            helper.setText(messageText, true);
            mailSender.send(mimeMessage);
            isSuccessFull = true;
        } catch (Exception ex) {
            System.out.println("Exception in sendEmail " + ex);
        }

        return isSuccessFull;

    }

    @Override
    public boolean sendMailWithAttachment(Mail email, Map<String, Object> dataMap,
            Properties settings, String path, String docName) throws MessagingException {
        boolean isSuccessFull = false;
        String messageText = "";

        try {
            mailSender.setHost(settings.getProperty("host"));
            LOGGER.debug("HostName : " + mailSender.getHost());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(email.getFrom());

            File file = new File("temp.pdf");
            file.deleteOnExit();
            String urlGetDocuments = env.getProperty("urlGetDocuments");
            path = urlGetDocuments + path;
            URL url = new URL(path);
            FileUtils.copyURLToFile(url, file);
            helper.addAttachment(docName, file);

            messageText = email.getMsgBody();

            if (email.getToList().size() > 0) {
                helper.setTo(email.getToList().toArray(new String[email.getToList().size()]));
            }

            if (email.getCcList().size() > 0) {
                helper.setCc(email.getCcList().toArray(new String[email.getCcList().size()]));
            }

            helper.setSubject(email.getSubject());
            helper.setText(messageText, true);
            mailSender.send(mimeMessage);
            isSuccessFull = true;
        } catch (Exception ex) {
            LOGGER.debug("Exception in sendEmail" + ex);
        }

        return isSuccessFull;
    }

    @Override
    public boolean sendMailWithMultipleAttachment(Mail email, Map<String, Object> dataMap,
            Properties settings, List<File> attachments) throws MessagingException {
        boolean isSuccessFull = false;
        String messageText = "";

        try {
            mailSender.setHost(settings.getProperty("host"));
            LOGGER.debug("HostName : " + mailSender.getHost());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(email.getFrom());

            File file = new File("temp.pdf");
            file.deleteOnExit();

            for (File file1 : attachments) {
                FileSystemResource fr = new FileSystemResource(file1);
                helper.addAttachment(file1.getName(), fr);
            }

            messageText = email.getMsgBody();

            if (email.getToList().size() > 0) {
                helper.setTo(email.getToList().toArray(new String[email.getToList().size()]));
            }

            if (email.getCcList().size() > 0) {
                helper.setCc(email.getCcList().toArray(new String[email.getCcList().size()]));
            }

            helper.setSubject(email.getSubject());
            helper.setText(messageText, true);
            mailSender.send(mimeMessage);
            isSuccessFull = true;
        } catch (Exception ex) {
            LOGGER.debug("Exception in sendEmail" + ex);
        }

        return isSuccessFull;
    }

}
