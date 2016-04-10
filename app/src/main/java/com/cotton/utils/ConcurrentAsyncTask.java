package com.cotton.utils;

import android.os.AsyncTask;
import android.os.Build;

import java.util.concurrent.ExecutionException;

public class ConcurrentAsyncTask {    
	    public static void execute(AsyncTask as, String... params) {
	        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {	            
	        	as.execute(params);
	        } else {
	            as.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
	        }
	    }
	    
	    public static boolean getExecute(AsyncTask as, String... params) throws InterruptedException, ExecutionException {	               
	    	if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {	            
	        	return (Boolean) as.execute(params).get();
	        } else {
	            return (Boolean) as.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params).get();
	        }
	}
}