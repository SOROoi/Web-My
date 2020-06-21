package wxt.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class MyConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		// TODO Auto-generated method stub
		if (source == null) {
			throw new RuntimeException("生日为空");
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("输入格式错误，请按该格式输入：1970-1-1");
		}
	}

}
