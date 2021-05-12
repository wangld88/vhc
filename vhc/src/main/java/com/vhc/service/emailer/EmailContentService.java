package com.vhc.service.emailer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vhc.dto.TransactionDTO;
import com.vhc.dto.UserForm;
import com.vhc.core.model.Transaction;
//import com.vhc.dto.UserForm;
import com.vhc.core.model.User;
import com.vhc.core.service.TransactionService;
import com.vhc.core.service.UserService;


/**
 * This method is to prepare the content data for email templates.
 *
 * @author JW
 *
 */
@Service
public class EmailContentService {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	/*@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;*/

	private static final Logger logger = LoggerFactory.getLogger(EmailContentService.class);


	public UserForm getUserInfo(Long userid) {

		logger.info("Method - getUserInfo is invoked");

		if(userid == 0) {
			logger.error("[Email] passed userid is invalid");
			return null;
		}

		User user = userService.getById(userid);
		UserForm form = new UserForm(user);

		return form;
	}


	public TransactionDTO getTransactionInfo(Long txid) {

		logger.info("Method - getRegistrationInfo is invoked");

		Transaction tx = transactionService.getById(txid);

		TransactionDTO form = new TransactionDTO(tx);

		/*User user = userService.getById(userid);

		Answer answer = answerService.getByUser(user);

		form.setUser(user);

		form.setRegistration(regist);

		form.setAnswer(answer);*/

		return form;
	}

}
