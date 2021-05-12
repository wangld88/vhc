package com.vhc.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionListener
  implements HttpSessionListener {
  private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
  private String timeout;

  public SessionListener(String timeout)
  {
    this.timeout = timeout;
  }

  public void sessionCreated(HttpSessionEvent event)
  {
    logger.debug("==== Session is created ====" + this.timeout);

    event.getSession().setMaxInactiveInterval(Integer.parseInt(this.timeout));
  }

  public void sessionDestroyed(HttpSessionEvent event)
  {
    logger.debug("==== Session is destroyed ====");
  }
}
