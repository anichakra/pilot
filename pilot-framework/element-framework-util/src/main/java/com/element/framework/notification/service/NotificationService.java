package me.anichakra.poc.pilot.framework.notification.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import me.anichakra.poc.pilot.framework.notification.domain.Mail;

/**
 * @author
 *
 */

public interface NotificationService {

    boolean sendEmail(String from, String to, String cc, String subject, String msgBody,
            boolean htmlContent);

    boolean sendEmail(Mail email, Map<String, Object> dataMap, Properties settings)
            throws MessagingException;

    boolean sendMailWithAttachment(Mail email, Map<String, Object> dataMap, Properties settings,
            String path, String docName) throws MessagingException;

    boolean sendMailWithMultipleAttachment(Mail email, Map<String, Object> dataMap,
            Properties settings, List<File> attachments) throws MessagingException;

}
