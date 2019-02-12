package com.example.springjwt.exceptionjwt;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StringBaseLocalizedException extends RuntimeException {
	/**
	 * Can't find did based on phone number.
	 */
	private static final long serialVersionUID = 1L;
	protected final String string1;
	protected final String string2;

	protected StringBaseLocalizedException()
	{
		this.string1 = null;
		this.string2 = null;
	}
	
	protected StringBaseLocalizedException(final String string) 
	{
		this.string1 = string;
		this.string2 = null;
	}
	
	protected StringBaseLocalizedException(final String string1, final String string2) {
		this.string1 = string1;
		this.string2 = string2;
	}

	public String getMessage() {
		return getLocalizedMessage(Locale.getDefault());
	}

	public String getLocalizedMessage(Locale currentLocale) {
		ResourceBundle messages =
				ResourceBundle.getBundle("ExceptionBundle",currentLocale);
		List<Object> messageArguments = new ArrayList<Object>();
		if(string1 != null)
		{
			messageArguments.add(new String(string1));
		}
		else
		{
			messageArguments.add(new String("empty"));
		}
		
		if(string2 != null)
		{
			messageArguments.add(new String(string2));
		}
		
		final MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(currentLocale);
		formatter.applyPattern(messages.getString(this.getClass().getSimpleName()));
		return formatter.format(messageArguments.toArray());
	}


}

