package com.ronglian.kangrui.saas.research.auth.client.interceptor;

import com.ronglian.kangrui.saas.research.common.constant.CommonConstants;
import lombok.extern.java.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author ace
 */
@Component
@Log
public class OkHttpTokenInterceptor implements Interceptor {


	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
	    Response response = chain.proceed(request);
		if(HttpStatus.FORBIDDEN.value()==response.code()){
			if(response.body().string().contains(String.valueOf(CommonConstants.EX_CLIENT_INVALID_CODE))){
				log.info("Client Token Expire,Retry to request...");
				Request newRequest = chain.request()
						.newBuilder()
						.build();
				response = chain.proceed(newRequest);
			}
		}
	    return response;
	}

}
