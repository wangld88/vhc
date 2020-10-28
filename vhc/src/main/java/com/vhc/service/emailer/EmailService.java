package com.vhc.service.emailer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.vhc.dto.TransactionDTO;
//import com.vhc.dto.UserForm;
//import com.vhc.exception.CommonErrorData;
//import com.vhc.exception.ServiceRunTimeException;
import com.vhc.core.model.Template;
import com.vhc.core.model.User;
import com.vhc.core.service.TemplateService;
import com.sun.mail.smtp.SMTPMessage;


/**
 * Email processing service, which will generate email based on template
 *
 * @author JW
 *
 */
@Service("emailService")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateService templateService;

	/*@Autowired
	private RegistrationService registrationService;*/

	@Autowired
	private EmailContentService emailContentService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	public void sendMail(String from, String to, String subject, String body) {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(body);

		logger.info("Sending...");

		mailSender.send(mail);

		logger.info("Done!");
	}

	public void sendMailByTemplate(int templateid, User user) {

		SimpleMailMessage mail = new SimpleMailMessage();

		Template t = templateService.getById(templateid);


		mail.setSubject(t.getSubject());
		mail.setFrom("noreply@emhc.ca");
		mail.setTo(user.getEmail());
		mail.setCc(t.getCc());
		mail.setBcc(t.getBcc());
		String body = t.getTextcontent();
		mail.setText(body);

		logger.info("Sending...");

		mailSender.send(mail);

		logger.info("Email sending is Done!");
	}


	public boolean sendEmail (int templateid, String param, String extraText) {//throws ServiceRunTimeException {

		Template tpl = templateService.getById(templateid);

		System.out.println("template: "+tpl);
		String subject = tpl.getSubject();

		String htmlTemp = tpl.getHtmlcontent();

		String textTemp = tpl.getTextcontent();

		String[] attachments = null;

		/*if (tpl.getAttachment() != null && !tpl.getAttachment().isEmpty()) {
			attachments = tpl.getAttachment().split(";");
		}*/

		String service = tpl.getServicename();

		String method = tpl.getMethodname();

		MimeMessage msg = mailSender.createMimeMessage();

		Class<?>[] paramInt = new Class[1];
		paramInt[0] = Integer.TYPE;

		Class<?>[] paramString = new Class[1];
		paramString[0] = String.class;

		Class<?> noparams[] = {};

		String[] params = param.split(":");
		Class<?>[] paramLong = new Class[params.length];
		Long[] lParams = new Long[params.length];

		Method mtd = null;
		Object entity = null;

		try {
			logger.info("service NAME IS: "+service);
			Class<?> cls = Class.forName("com.vhc.service." + service);

			switch(method) {
				/*case "getUserInfo":

					mtd = cls.getDeclaredMethod(method, Integer.class);
					entity = (UserForm) mtd.invoke(emailContentService, Integer.parseInt(param));
					logger.info("Student Info: "+entity.toString());

					break;*/
				case "getTransactionInfo":

					mtd = cls.getDeclaredMethod(method, Integer.class);
					entity = (TransactionDTO) mtd.invoke(emailContentService, Integer.parseInt(param));
					logger.info("Student Registration Info: "+entity.toString());

					break;
				default:
					break;
			}

			Class<?> clazz = entity.getClass();
			String recipient = "";

			for(Field field : clazz.getDeclaredFields()) {
				String value = "";
				String name = field.getName();
				String mtdname = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);//.toLowerCase();
				String type = field.getType().getName();
				String original = "\\{\\+" + name + "\\+\\}";
				String fullname = field.toGenericString();
				logger.info("!!!Replacement item: " + original + ", method name: " + mtdname + ", Type: " + type + ", :"+field.getModifiers()+", :"+field.getAnnotations().length);

				if(!fullname.contains("static")) {
					Object obj = entity.getClass().getDeclaredMethod(mtdname, noparams).invoke(entity, null);
					if (obj != null) {
						if ( type.equals("java.lang.String")) {
							value = (String) obj;
						} else if (type.equals("java.lang.Date")) {
							value = ((java.util.Date) obj).toString();
						} else {
							value = obj.toString();
						}
						if(mtdname.equalsIgnoreCase("getOrgemail")) { //getEmail
							recipient = value;
						}
					}
					recipient = recipient.replace(original, value);
					htmlTemp = htmlTemp.replaceAll(original, value);
					textTemp = textTemp.replaceAll(original, value);
					subject = subject.replaceAll(original, value);
				}
			}

			htmlTemp = htmlTemp + extraText;
			textTemp = textTemp + extraText;

			htmlTemp = htmlTemp.replaceAll("(\\\r\\\n|\\\n\\\r|\\\r|\\\n)", "<BR />");
			//textTemp = textTemp.replaceAll("(\\r\\n|\\n\\r|\\r|\\n)", "<BR />");//"<BR>", System.lineSeparator());
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			logger.info("Email sent From: " + tpl.getSender());
			helper.addTo(recipient);

			if(tpl.getCc() != null) {
				helper.addCc(tpl.getCc());
			}

			if(tpl.getBcc() != null) {
				helper.addBcc(tpl.getBcc());
			}
			helper.setSubject(subject);
			helper.setFrom(tpl.getSender());
			helper.setReplyTo(tpl.getSender());

			if (attachments != null && attachments.length > 0) {
				for (int i = 0; i < attachments.length; i++) {
					String filename = attachments[i];
					String filePath = "";
					FileSystemResource file = new FileSystemResource(filePath + filename);
					helper.addAttachment(filename, file);
				}
			}

			if (textTemp != null) {
				if (htmlTemp != null) {
					helper.setText(textTemp, htmlTemp);
				} else {
					helper.setText(textTemp);
				}
				//helper.setText(textTemp);
			}

		/*} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Class lookup");
			ced.setCause("Fail to locate the given class. "); //e.getCause().toString()
			ced.setMessage(ce.getMessage());
			throw new ServiceRunTimeException(ced, ce);
		} catch (IllegalAccessException iae) {
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Class lookup");
			ced.setCause("Fail to access the given class. "); //e.getCause().toString()
			ced.setMessage(iae.getMessage());
			throw new ServiceRunTimeException(ced, iae);
		} catch (NoSuchMethodException nme) {
			nme.printStackTrace();
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Class lookup");
			ced.setCause("Fail to locate the given method. "); //e.getCause().toString()
			ced.setMessage(nme.getMessage());
			throw new ServiceRunTimeException(ced, nme);
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Data Retrieve");
			ced.setCause("Fail to retrieve data for preparing MIME Message. "); //e.getCause().toString()
			ced.setMessage(ite.getMessage());
			throw new ServiceRunTimeException(ced, ite);
		} catch (MessagingException e) {
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Mail Send");
			ced.setCause("Fail to construct MIME Message. "); //e.getCause().toString()
			ced.setMessage(e.getMessage());
			throw new ServiceRunTimeException(ced, e);
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
			CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Data Retrieve");
			ced.setCause("Method call failed with wrong arguments. "); //e.getCause().toString()
			ced.setMessage(iae.getMessage());
			throw new ServiceRunTimeException(ced, iae);*/
		} catch (Exception e) {
			e.printStackTrace();
			/*CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Data Retrieve");
			ced.setCause("There is a technical difficulty. "); //e.getCause().toString()
			ced.setMessage(e.getMessage());
			logger.info("Exception caught in EmailService: " + e.getMessage());
			throw new ServiceRunTimeException(ced, e);*/
		}

		try{
			SMTPMessage smtpMessage = new SMTPMessage(msg);
			/*if (message.getEnvelopeFrom() != null && message.getEnvelopeFrom().length() > 0) {
				smtpMessage.setEnvelopeFrom(message.getEnvelopeFrom());
			}*/
			((JavaMailSenderImpl) mailSender).send(smtpMessage);

			//logger.info("Email sent TO: " + ((Address) smtpMessage.getRecipients(RecipientType.TO)[0]).toString() + ", BCC: " + ((Address) smtpMessage.getRecipients(RecipientType.BCC)[0]).toString());

		}
		catch(Exception e){
			/*for (Iterator failedRecipients = Arrays.asList(tpl.getReceiver().split(",")).iterator(); failedRecipients.hasNext(); ) {
				failedEmailAddresses.add((String) failedRecipients.next());
			}*/
			e.printStackTrace();
			/*CommonErrorData ced = new CommonErrorData();
			ced.setErrorName("Mail Send");
			ced.setCause("Fail to send email Message. "); //e.getCause().toString()
			ced.setMessage(e.getMessage());
			logger.info("Exception caught in EmailService: " + e.getMessage());

			throw new ServiceRunTimeException(ced, e);*/
		}

		return true;
	}
}