package com.example.sinvoicedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.libra.sinvoice.LogHelper;
import com.libra.sinvoice.SinVoicePlayer;
import com.libra.sinvoice.SinVoiceRecognition;

public class MainActivity extends Activity implements SinVoiceRecognition.Listener, SinVoicePlayer.Listener {
	private final static String TAG = "MainActivity";
	private final static int MAX_NUMBER = 2;
	private final static int MSG_SET_RECG_TEXT = 1;
	private final static int MSG_RECG_START = 2;
	private final static int MSG_RECG_END = 3;

	private final static String CODEBOOK = "12345";
	private static String str1 = "HELLO", text;
	private static int count = 0;

	private Handler mHanlder;
	private SinVoicePlayer mSinVoicePlayer;
	private SinVoiceRecognition mRecognition;
	Intent intentService, SensorP;
	public SensorService myservice;// �󶨵�service����
	// public SensorProximity spservice ;//�󶨵�service����
	DataReceiver dateReceiver;
	String Data1, Data2, Data3, Data4;
	boolean isRec = false;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Log.v(TAG, "onStart()");
		dateReceiver = new DataReceiver();
		IntentFilter intentfilter = new IntentFilter();// ����IntentFilter����
		intentfilter.addAction("AAAAA");
		registerReceiver(dateReceiver, intentfilter);// ע��Broadcast Receiver
		super.onStart();
	}

	private class DataReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Data1 = intent.getStringExtra("MulAcceleration");
			Data2 = intent.getStringExtra("d");
			// Data3=intent.getStringExtra("level1");
			if (count == 0) {
				mSinVoicePlayer.play(text);
				count = 1;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intentService = new Intent(MainActivity.this, SensorService.class);
		// SensorP=new Intent(MainActivity.this,SensorProximity.class);

		mSinVoicePlayer = new SinVoicePlayer(CODEBOOK);
		mSinVoicePlayer.setListener(this);

		mRecognition = new SinVoiceRecognition(CODEBOOK);
		mRecognition.setListener(this);

		final EditText playTextView = (EditText) findViewById(R.id.playtext);
		TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
		mHanlder = new RegHandler(recognisedTextView);

		Button playStart = (Button) this.findViewById(R.id.start_play);
		playStart.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				str1 = playTextView.getText().toString();
				text = genText(str1);
				count = 0;
				bindService(intentService, conn, Context.BIND_AUTO_CREATE);
			}
		});

		Button playStop = (Button) this.findViewById(R.id.stop_play);
		playStop.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				mSinVoicePlayer.stop();
				count = 0;
				unbindService(conn);

			}
		});

		Button recognitionStart = (Button) this.findViewById(R.id.start_reg);
		recognitionStart.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				mRecognition.start();
			}
		});

		Button recognitionStop = (Button) this.findViewById(R.id.stop_reg);
		recognitionStop.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				mRecognition.stop();
			}
		});

	}

	public ServiceConnection conn = new ServiceConnection() {

		public void onServiceConnected(ComponentName arg0, IBinder service) {
			// TODO Auto-generated method stub
			myservice = ((SensorService.MyBinder) service).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			myservice = null;
		}
	};

	private String genText(String str) {
		StringBuffer sb = new StringBuffer();
		char cha;
		for (int i = 0; i < str.length(); i++) {
			cha = str.charAt(i);
			switch (cha) {
			case 'A':
				sb.append("12345");
				break;
			case 'B':
				sb.append("12435");
				break;
			case 'C':
				sb.append("13245");
				break;
			case 'D':
				sb.append("13425");
				break;
			case 'E':
				sb.append("14325");
				break;
			case 'F':
				sb.append("14235");
				break;
			case 'G':
				sb.append("21345");
				break;
			case 'H':
				sb.append("21435");
				break;
			case 'I':
				sb.append("23415");
				break;
			case 'J':
				sb.append("23145");
				break;
			case 'K':
				sb.append("24315");
				break;
			case 'L':
				sb.append("24135");
				break;
			case 'M':
				sb.append("32145");
				break;
			case 'N':
				sb.append("32415");
				break;
			case 'O':
				sb.append("31425");
				break;
			case 'P':
				sb.append("31245");
				break;
			case 'Q':
				sb.append("34125");
				break;
			case 'R':
				sb.append("34215");
				break;
			case 'S':
				sb.append("41325");
				break;
			case 'T':
				sb.append("41235");
				break;
			case 'U':
				sb.append("42315");
				break;
			case 'V':
				sb.append("42135");
				break;
			case 'W':
				sb.append("43215");
				break;
			case 'X':
				sb.append("43125");
				break;
			case 'Y':
				sb.append("45125");
				break;
			case 'Z':
				sb.append("45215");
				break;
			case ' ':
				sb.append("45235");
				break;
			case 'a':
				sb.append("12125");
				break;
			case 'b':
				sb.append("12135");
				break;
			case 'c':
				sb.append("12145");
				break;
			case 'd':
				sb.append("13125");
				break;
			case 'e':
				sb.append("13135");
				break;
			case 'f':
				sb.append("13145");
				break;
			case 'g':
				sb.append("14125");
				break;
			case 'h':
				sb.append("14135");
				break;
			case 'i':
				sb.append("14145");
				break;
			case 'j':
				sb.append("21215");
				break;
			case 'k':
				sb.append("21235");
				break;
			case 'l':
				sb.append("21245");
				break;
			case 'm':
				sb.append("23125");
				break;
			case 'n':
				sb.append("23135");
				break;
			case 'o':
				sb.append("34135");
				break;
			case 'p':
				sb.append("23215");
				break;
			case 'q':
				sb.append("23235");
				break;
			case 'r':
				sb.append("23245");
				break;
			case 's':
				sb.append("24125");
				break;
			case 't':
				sb.append("32125");
				break;
			case 'u':
				sb.append("24145");
				break;
			case 'v':
				sb.append("24215");
				break;
			case 'w':
				sb.append("31325");
				break;
			case 'x':
				sb.append("31235");
				break;
			case 'y':
				sb.append("32135");
				break;
			case 'z':
				sb.append("31315");
				break;
			case '0':
				sb.append("31415");
				break;
			case '1':
				sb.append("31435");
				break;
			case '2':
				sb.append("34145");
				break;
			case '3':
				sb.append("41215");
				break;
			case '4':
				sb.append("41245");
				break;
			case '5':
				sb.append("42125");
				break;
			case '6':
				sb.append("42145");
				break;
			case '7':
				sb.append("43135");
				break;
			case '8':
				sb.append("43145");
				break;
			case '9':
				sb.append("42325");
				break;
			default:
				break;

			}
		}
		return sb.toString();
	}

	private static class RegHandler extends Handler {
		private StringBuilder mTextBuilder = new StringBuilder();
		private StringBuilder mTextBuilder1 = new StringBuilder();
		private TextView mRecognisedTextView;

		public RegHandler(TextView textView) {
			mRecognisedTextView = textView;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SET_RECG_TEXT:
				char ch = (char) msg.arg1;
				mTextBuilder.append(ch);

				if (null != mRecognisedTextView) {
					// mRecognisedTextView.setText(mTextBuilder.toString());
				}

				break;

			case MSG_RECG_START:
				mTextBuilder.delete(0, mTextBuilder.length());
				mTextBuilder1.delete(0, mTextBuilder1.length());
				break;

			case MSG_RECG_END:
				LogHelper.d(TAG, "recognition end");
				for (int i = 0; i < mTextBuilder.toString().length();) {
					if (i + 5 > mTextBuilder.toString().length()) {
						break;
					}
					String a = mTextBuilder.toString().substring(i, i + 5);
					Log.v(TAG, "i" + i);
					switch (a) {
					case "12345":
						mTextBuilder1.append('A');
						break;
					case "12435":
						mTextBuilder1.append('B');
						break;
					case "13245":
						mTextBuilder1.append('C');
						break;
					case "13425":
						mTextBuilder1.append('D');
						break;
					case "14325":
						mTextBuilder1.append('E');
						break;
					case "14235":
						mTextBuilder1.append('F');
						break;
					case "21345":
						mTextBuilder1.append('G');
						break;
					case "21435":
						mTextBuilder1.append('H');
						break;
					case "23415":
						mTextBuilder1.append('I');
						break;
					case "23145":
						mTextBuilder1.append('J');
						break;
					case "24315":
						mTextBuilder1.append('K');
						break;
					case "24135":
						mTextBuilder1.append('L');
						break;
					case "32145":
						mTextBuilder1.append('M');
						break;
					case "32415":
						mTextBuilder1.append('N');
						break;
					case "31425":
						mTextBuilder1.append('O');
						break;
					case "31245":
						mTextBuilder1.append('P');
						break;
					case "34125":
						mTextBuilder1.append('Q');
						break;
					case "34215":
						mTextBuilder1.append('R');
						break;
					case "41325":
						mTextBuilder1.append('S');
						break;
					case "41235":
						mTextBuilder1.append('T');
						break;
					case "42315":
						mTextBuilder1.append('U');
						break;
					case "42135":
						mTextBuilder1.append('V');
						break;
					case "43215":
						mTextBuilder1.append('W');
						break;
					case "43125":
						mTextBuilder1.append('X');
						break;
					case "45125":
						mTextBuilder1.append('Y');
						break;
					case "45215":
						mTextBuilder1.append('Z');
						break;
					case "12125":
						mTextBuilder1.append('a');
						break;
					case "12135":
						mTextBuilder1.append('b');
						break;
					case "12145":
						mTextBuilder1.append('c');
						break;
					case "13125":
						mTextBuilder1.append('d');
						break;
					case "13135":
						mTextBuilder1.append('e');
						break;
					case "13145":
						mTextBuilder1.append('f');
						break;
					case "14125":
						mTextBuilder1.append('g');
						break;
					case "14135":
						mTextBuilder1.append('h');
						break;
					case "14145":
						mTextBuilder1.append('i');
						break;
					case "21215":
						mTextBuilder1.append('j');
						break;
					case "21235":
						mTextBuilder1.append('k');
						break;
					case "21245":
						mTextBuilder1.append('l');
						break;
					case "23125":
						mTextBuilder1.append('m');
						break;
					case "23135":
						mTextBuilder1.append('n');
						break;
					case "34135":
						mTextBuilder1.append('o');
						break;
					case "23215":
						mTextBuilder1.append('p');
						break;
					case "23235":
						mTextBuilder1.append('q');
						break;
					case "23245":
						mTextBuilder1.append('r');
						break;
					case "24125":
						mTextBuilder1.append('s');
						break;
					case "32125":
						mTextBuilder1.append('t');
						break;
					case "24145":
						mTextBuilder1.append('u');
						break;
					case "24215":
						mTextBuilder1.append('v');
						break;
					case "31325":
						mTextBuilder1.append('w');
						break;
					case "31235":
						mTextBuilder1.append('x');
						break;
					case "32135":
						mTextBuilder1.append('y');
						break;
					case "31315":
						mTextBuilder1.append('z');
						break;
					case "31415":
						mTextBuilder1.append('0');
						break;
					case "31435":
						mTextBuilder1.append('1');
						break;
					case "34145":
						mTextBuilder1.append('2');
						break;
					case "41215":
						mTextBuilder1.append('3');
						break;
					case "41245":
						mTextBuilder1.append('4');
						break;
					case "42125":
						mTextBuilder1.append('5');
						break;
					case "42145":
						mTextBuilder1.append('6');
						break;
					case "43135":
						mTextBuilder1.append('7');
						break;
					case "43145":
						mTextBuilder1.append('8');
						break;
					case "42325":
						mTextBuilder1.append('9');
						break;
					case "45235":
						mTextBuilder1.append(' ');
						break;
					default:
						mTextBuilder1.append('!');
						break;
					}

					i += 5;

				}
				mRecognisedTextView.setText(mTextBuilder1.toString());
				break;
			}
			super.handleMessage(msg);
		}
	}

	public void onRecognitionStart() {
		mHanlder.sendEmptyMessage(MSG_RECG_START);
	}

	public void onRecognition(char ch) {
		mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
	}

	public void onRecognitionEnd() {
		mHanlder.sendEmptyMessage(MSG_RECG_END);
	}

	public void onPlayStart() {
		LogHelper.d(TAG, "start play");
	}

	public void onPlayEnd() {
		LogHelper.d(TAG, "stop play");
	}

}
