package ople.lapakproject.absensionline.activity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

@SuppressLint("CommitPrefEdits")
public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// nama sharepreference
	private static final String PREF_NAME = "Sesi";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	public static final String KEY_idg = "idg";
	public static final String KEY_nip = "nip";
	public static final String KEY_nama = "nama";
	public static final String KEY_jk = "jk";
	public static final String KEY_alamat = "alamat";
	public static final String KEY_idk = "idk";


	/**
	 * Create login session
	 * */
	public void createLoginSession(String idg, String nip, String nama,String jk,String alamat, String idk){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		editor.putString(KEY_idg, idg);
		editor.putString(KEY_nip, nip);

		editor.putString(KEY_nama, nama);
		editor.putString(KEY_jk, jk);
		editor.putString(KEY_alamat, alamat);
		editor.putString(KEY_idk, idk);

		editor.commit();
	}

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			Intent i = new Intent(_context, login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
			//((Activity)_context).finish();
		}
		
	}
	
	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();

		user.put(KEY_idg, pref.getString(KEY_idg, null));
		user.put(KEY_nip, pref.getString(KEY_nip, null));
		user.put(KEY_nama, pref.getString(KEY_nama, null));
		user.put(KEY_jk, pref.getString(KEY_jk, null));
		user.put(KEY_alamat, pref.getString(KEY_alamat, null));
		user.put(KEY_idk, pref.getString(KEY_idk, null));

		return user;
	}
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
		
		Intent i = new Intent(_context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
