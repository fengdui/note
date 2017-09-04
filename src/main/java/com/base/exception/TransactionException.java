package com.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TransactionException extends Exception {
  private static final long serialVersionUID = 7985658799424168444L;
  private Throwable nestedThrowable = null;

	public TransactionException() {
	  super();
	}
	
	public TransactionException(String msg) {
	  super(msg);
	}

	public TransactionException(Throwable nestedThrowable) {
	  this.nestedThrowable = nestedThrowable;
	}

	public TransactionException(String msg, Throwable nestedThrowable) {
	  super(msg);
	  this.nestedThrowable = nestedThrowable;
	}

	public void printStackTrace() {
	  super.printStackTrace();
	  if (nestedThrowable != null) {
	    nestedThrowable.printStackTrace();
	  }
	}

	public void printStackTrace(PrintStream ps) {
	  super.printStackTrace(ps);
	  if (nestedThrowable != null) {
	    nestedThrowable.printStackTrace(ps);
	  }
	}

	public void printStackTrace(PrintWriter pw) {
	  super.printStackTrace(pw);
	  if (nestedThrowable != null) {
	    nestedThrowable.printStackTrace(pw);
	  }
	}	
}