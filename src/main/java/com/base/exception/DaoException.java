/*
 * Created on 2006-1-19
 *
 * <p>Title: ����Ͷ�ʴٽ�����������Ϣϵͳ��Ŀ</p>
 *
 * <p>Description: DAO exception ����,����DAOʵ�����쳣���ɴ������� </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: �к���Ԫ</p>
 * 
 */
package com.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * <p>
 * Description: DAO exception 基类,所有DAO实现类异常均由此类派生
 * </p>
 * 
 * @version 1.0
 */
public class DaoException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 8014536628694723159L;
  private Throwable nestedThrowable = null;

  public DaoException() {
    super();
  }

  public DaoException(String msg) {
    super(msg);
  }

  public DaoException(Throwable nestedThrowable) {
    this.nestedThrowable = nestedThrowable;
  }

  public DaoException(String msg, Throwable nestedThrowable) {
    super(msg);
    this.nestedThrowable = nestedThrowable;
  }

  @Override
  public void printStackTrace() {
    super.printStackTrace();
    if (nestedThrowable != null) {
      nestedThrowable.printStackTrace();
    }
  }

  @Override
  public void printStackTrace(PrintStream ps) {
    super.printStackTrace(ps);
    if (nestedThrowable != null) {
      nestedThrowable.printStackTrace(ps);
    }
  }

  @Override
  public void printStackTrace(PrintWriter pw) {
    super.printStackTrace(pw);
    if (nestedThrowable != null) {
      nestedThrowable.printStackTrace(pw);
    }
  }
}