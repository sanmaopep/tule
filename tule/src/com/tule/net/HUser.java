package com.tule.net;

import java.util.List;

import com.tule.Config;
import com.tule.net.bean.User;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * 用户表的相关类
 * @author MaoYiWei
 * @date 2015年8月1日
 */
public class HUser extends Net{

	/**
	 * 用户登录
	 * @param context
	 * @param username
	 * @param password
	 * @param callback
	 */
	public static void Login(Context context,
			String username,
			String password,
			final LoginCallback callback){
		
		User myuser=new User();
		myuser.setUsername(username);
		myuser.setPassword(password);
		myuser.login(context, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				callback.Success();
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				callback.Fail(arg0,arg1);
			}
		});
	}
	
	public interface LoginCallback{
		public void Success();
		public void Fail(int faliurecode,String message);
	}
	
	/**
	 * 用户注册
	 * @param context
	 * @param username
	 * @param password
	 * @param phone
	 * @param callback
	 */
	public static void Register(Context context,
			String username,
			String password,
			String phone,
			final RegisterCallback callback
			){
		User bu = new User();
		bu.setUsername(username);
		bu.setPassword(password);
		bu.setMobilePhoneNumber(phone);
		bu.signUp(context, new SaveListener() {
			@Override
			public void onSuccess() {
				callback.Success();
			}

			@Override
			public void onFailure(int arg0, String msg) {
				callback.Fail(arg0, msg);
			}
		}
		);
		
	}
	public interface RegisterCallback{
		public void Success();
		public void Fail(int faliurecode,String message);
	}

	/**
	 * 发送短信验证码
	 * @param context
	 * @param phone
	 * @param callback
	 */
	public static void sendCheckNum(Context context,
			String phone,
			final SendCheckNumCallback callback
			){
		BmobSMS.requestSMSCode(context, phone, Config.SMS_MODEL, new RequestSMSCodeListener() {

		    @Override
		    public void done(Integer smsId,BmobException ex) {
		        // TODO Auto-generated method stub
		        if(ex==null){//
		            Log.i("bmob","短信发送成功，短信id："+smsId);//用于查询本次短信发送详情
		            callback.Success();
		      
		        }else{
		            Log.i("bmob","errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
		            callback.Fail();
		        }
		    }
		});
	}
	public interface SendCheckNumCallback{
		public void Success();
		public void Fail();
	}
	
	/**
	 * 验证短信验证码是否正确
	 * @param context
	 * @param phone
	 * @param verifynum
	 * @param callback
	 */
	public static void VerifySMS(Context context,
			String phone,
			String verifynum,
			final VerifySMSCallback callback
			){BmobSMS.verifySmsCode(context,phone,verifynum, new VerifySMSCodeListener() {

			    @Override
			    public void done(BmobException ex) {
			        // TODO Auto-generated method stub
			        if(ex==null){//短信验证码已验证成功
			            Log.i("smile", "验证通过");
			            callback.Success();
			        }else{
			            Log.i("smile", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
			            callback.Fail();
			        }
			    }
			});
		
	}
	public interface VerifySMSCallback{
		public void Success();
		public void Fail();
	}
	
	/**
	 * 重置密码
	 * @param context
	 * @param password
	 * @param phone
	 * @param callback
	 */
	public static void resetPass(final Context context,
			final String password,
			final String phone,
			final resetPassCallback callback
			){
		BmobQuery<User>	query=new BmobQuery<User>();
		query.addWhereEqualTo("mobilePhoneNumber", phone);
		query.findObjects(context, new FindListener<User>() {
			
			@Override
			public void onSuccess(List<User> arg0) {
				if(arg0.size()==1){
					Log.i("smile", "找到该用户");
					User user=arg0.get(0);
					user.setPassword(password);
					user.update(context, user.getObjectId(),new UpdateListener() {
						@Override
						public void onSuccess() {
							Log.i("smile", "更改密码成功");
							callback.Success();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							Log.i("shit", "更改密码失败");
							callback.Fail();
						}
					});
				}else{
					Log.i("shit","该手机号拥有的用户不止一个");
					callback.Fail();
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.i("shit","查询失败");
				callback.Fail();
			}
		});
		
	}
	public interface resetPassCallback{
		public void Success();
		public void Fail();
	}
	
	/**
	 * 更新当前用户的性别
	 * @param context
	 * @param newSex
	 * @param callback
	 */
	public static void UpdateCurrentSex(Context context,
			String newSex,
			final updateCallback callback
			){
		User user=Config.getCurrentUser(context);
		user.setSex(newSex);
		user.update(context, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				Log.i("smile", "更新性别成功");
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("shit", "更新性别失败");
				callback.Fail();
			}
		});
		
	}
	public interface updateCallback{
		public void Success();
		public void Fail();
	}
	
	/**
	 * 更新当前用户的学校
	 * @param context
	 * @param newSchool
	 * @param callback
	 */
	public static void UpdateCurrentSchool(Context context,
			String newSchool,
			final updateCallback callback
			){
		User user=Config.getCurrentUser(context);
		user.setSchool(newSchool);;
		user.update(context, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				Log.i("smile", "更新学校成功");
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("shit", "更新学校失败");
				callback.Fail();
			}
		});
		
	}
	
	/**
	 * 更新当前用户的生日
	 * @param context
	 * @param newBirthday
	 * @param callback
	 */
	public static void UpdateCurrentBirthday(Context context,
			String newBirthday,
			final updateCallback callback
			){
		User user=Config.getCurrentUser(context);
		user.setBirthdata(newBirthday);
		user.update(context, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				Log.i("smile", "更新生日成功");
				callback.Success();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("shit", "更新生日失败");
				callback.Fail();
			}
		});
		
	}
	
}
