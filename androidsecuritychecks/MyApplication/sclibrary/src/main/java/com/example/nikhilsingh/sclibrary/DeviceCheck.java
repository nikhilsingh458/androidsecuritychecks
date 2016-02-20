package com.example.nikhilsingh.sclibrary;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nikhil.Singh on 18-Feb-16.
 */

public class DeviceCheck
{

    static Context m_context;
    boolean m_isBypass=false;
   // private static DeviceCheck ourInstance = new DeviceCheck();

    public DeviceCheck(boolean isBypass,Context context )
    {
        m_context=context;
        m_isBypass=isBypass;
    }

//    private DeviceCheck()
//    {
//    }

    public boolean isBluetoothStatusEnabled()
    {
        BluetoothManager cm = (BluetoothManager) m_context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter activeNetwork = cm.getAdapter();
        boolean isBluetooth = cm.getAdapter().isEnabled() == true;
        return isBluetooth;
    }
    //To check whether device is rooted or not
    public boolean isRooted()
    {
        if(m_isBypass!=true)
        {
            return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
        }
        else
        {
            return false;
        }
    }

    private boolean checkRootMethod1()
    {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private boolean checkRootMethod2()
    {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su" };
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private boolean checkRootMethod3()
    {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        }
        catch (Throwable t)
        {
            return false;
        }
        finally
        {
            if (process != null) process.destroy();
        }
    }
    private boolean CheckRootMethod4()
    {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;

        String IOPathExist = Environment.getExternalStorageState(Environment.getDataDirectory());
        if (Environment.MEDIA_MOUNTED.equals(IOPathExist)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(IOPathExist)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        return mExternalStorageAvailable||mExternalStorageWriteable;
    }

    //Check weather device is not using any emulator

    public boolean isEmulatorRunningProcessesDebuggerTest()
    {
        boolean getEmulator=false;
        final boolean isConnected = Debug.isDebuggerConnected();
        TelephonyManager tManager = (TelephonyManager) m_context.getSystemService(m_context.TELEPHONY_SERVICE);
        final String carriername = tManager.getNetworkOperatorName();
        final String operatorname = tManager.getSimOperatorName();

        if (!m_context.getPackageName().contains("google_sdk") &&
                !(m_context.getPackageName().contains("sdk")) &&
                (Build.MODEL != "unknown") &&
                (Build.ID != null && !(Build.ID.isEmpty())) &&
                (Build.VERSION.SDK_INT != 0) &&
                (isConnected) &&
                (carriername != null) &&
                (operatorname != "Android"))
        {

    }
        if(m_isBypass!=true)
        {
            return isConnected;
        }
        else
        {
            return false;
        }

}

public String getSignature()
{
    boolean getsignature=false;
    ArrayList<String> arr=  new ArrayList<String>();

    String strSignature=new String();

    final PackageManager pm = m_context.getPackageManager();
    String apkPath = m_context.getPackageCodePath();
    PackageInfo info = pm.getPackageArchiveInfo(apkPath, 0);
       android.content.pm.Signature[] sigs = new android.content.pm.Signature[0];
    try
    {
        sigs = pm.getPackageInfo(apkPath, PackageManager.GET_SIGNATURES).signatures;
    }
    catch (PackageManager.NameNotFoundException e)
    {
        e.printStackTrace();
    }
    if(info.signatures!=null)
    {
        byte[] bytesSignature;
        bytesSignature = info.signatures[0].toByteArray();
        strSignature = new String(bytesSignature);
    }
    return strSignature;
}

    public ArrayList<String> ReadValues(int resourceId)
    {
        ArrayList<String> values = new ArrayList<String>();
        // The InputStream opens the resourceId and sends it to the buffer
        InputStream is = m_context.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String readLine = null;

        try {
            // While the BufferedReader readLine is not null
            while ((readLine = br.readLine()) != null)
            {
                Log.d("TEXT", readLine);
                values.add(readLine);
            }

            // Close the InputStream and BufferedReader
            is.close();
            br.close();
            return  values;

        } catch (IOException e) {
            e.printStackTrace();
            return null ;
        }
    }

    public  String SHA1(String Filename) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] buffer=new byte[65536];
        //md.update(text.getBytes("iso-8859-1"), 0, text.length());
        InputStream fis = new FileInputStream(Filename);
        int n = 0;
        while (n != -1)
        {

            n = fis.read(buffer);
            if (n > 0)
            {
                md.update(buffer, 0, n);
            }
        }
        byte[] digestResult = md.digest();
        return convertToHex(digestResult);
    }
    public  String MD5(String text) throws UnsupportedEncodingException,NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        //md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md.update(text.getBytes("UTF-8"),0,text.length());
        byte[] md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private String convertToHex(byte[] data)
    {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public void ListOfRunningTasks()
    {
        ApplicationInfo appinfo = null;
        ActivityManager activityManager = (ActivityManager) m_context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = activityManager.getRunningTasks(Integer.MAX_VALUE);
        for (int ii = 0; ii < runningTaskInfo.size(); ii++) {
            String taskName = runningTaskInfo.get(ii).baseActivity.toShortString();
            int lastIndex = taskName.indexOf("/");
            if (-1 != lastIndex) {
                taskName = taskName.substring(1, lastIndex);
            }
            PackageManager packageManager = m_context.getPackageManager();
            try {
                appinfo = packageManager.getApplicationInfo(taskName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
            }
            final String title = (String) ((appinfo != null) ? packageManager.getApplicationLabel(appinfo) : "???");
        }
    }


    public String getRunningServicesInfo(Context context, List<ActivityManager.RunningServiceInfo> lstwhitelst) {
        StringBuffer serviceInfo = new StringBuffer();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager
                .getRunningServices(100);

        Iterator<ActivityManager.RunningServiceInfo> l = services.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = (ActivityManager.RunningServiceInfo) l.next();
            serviceInfo.append("pid: ").append(si.pid);
            serviceInfo.append("\nprocess: ").append(si.process);
            serviceInfo.append("\nservice: ").append(si.service);
            serviceInfo.append("\ncrashCount: ").append(si.crashCount);
            serviceInfo.append("\nclientCount: ").append(si.clientCount);
        }
        return serviceInfo.toString();
    }

}