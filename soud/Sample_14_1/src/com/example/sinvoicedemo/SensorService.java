package com.example.sinvoicedemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class SensorService extends Service implements SensorEventListener{
	public final String TAG = "SensorService";
	private final IBinder binder = new MyBinder(); 
    private SensorManager sm = null;
		CommandReceiver cmdReceiver;
	    boolean isAcu,isPro;
	    float MulAcceleration=0;
		double d=0;
		String level1;
		Thread thread; 

		public void onCreate() {
			cmdReceiver=new CommandReceiver();
		    Log.i("TAG", "onCreate=1");
			super.onCreate();
		}
		public class MyBinder extends Binder{  
			public SensorService getService(){  
		    return SensorService.this;  
		    }	     
		} 

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			Log.i("-----------SensorService---------------","����������" ); 
		   IntentFilter intentFilter=new IntentFilter();
		   intentFilter.addAction("BBBBB");
		   registerReceiver(cmdReceiver, intentFilter);
			
			sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
			sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_UI);
 
			return binder;
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
		
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
				float[] v = event.values;  
	            d=v[0];
	            Log.v(TAG, "d"+d);
	            isPro=true;
			}
			if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
				float[] values=event.values;
				MulAcceleration=values[0];
				Log.v(TAG, "MulAcceleration"+MulAcceleration);
				isAcu=true;
			}
			if(isPro&&isAcu){
			if(MulAcceleration<2.0&&d==0.0){
	    		  Intent intent=new Intent();
	    		  intent.setAction("AAAAA");
	    		  intent.putExtra("MulAcceleration",MulAcceleration+"");
	    		  intent.putExtra("d",d+"");
	    		  sendBroadcast(intent);
			}
			}

		
		}
	
		
		//接受广播
		  private class CommandReceiver extends BroadcastReceiver{

		  @Override
		   public void onReceive(Context context, Intent intent) {
			  
			 int cmd=intent.getIntExtra("cmd",-1);
		    Log.i(TAG, cmd+"");
		    if(cmd==0){
		    	isAcu=false;
		    	isPro=false;
		      stopSelf();
		    }
		   }
		   
		 }

	
		//重写 onDestroy 方法
		public void onDestroy() {
			Log.v(TAG, "onDestroy()");
			super.onDestroy();
			sm.unregisterListener(this);
			sm = null;
			this.unregisterReceiver(cmdReceiver);// 取消BroadcastReceiver
			stopSelf();

		}


				
}
