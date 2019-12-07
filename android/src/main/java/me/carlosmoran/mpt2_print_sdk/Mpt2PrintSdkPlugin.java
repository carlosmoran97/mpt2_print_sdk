package me.carlosmoran.mpt2_print_sdk;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

// PrinterLib
import HPRTAndroidSDK.HPRTPrinterHelper;

/** Mpt2PrintSdkPlugin */
public class Mpt2PrintSdkPlugin implements MethodCallHandler {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "mpt2_print_sdk");
    channel.setMethodCallHandler(new Mpt2PrintSdkPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if(call.method.equals("connectPrinter")){
      String MAC = call.argument("mac");
      int status = connectPrinter(MAC);

      result.success(status);

    } else if(call.method.equals("disconnectPrinter")){
      result.success(disconnectPrinter());
    } else if(call.method.equals("isConnected")){
      result.success(isConnected());
    } else if(call.method.equals("printText")){
      try{
        String data = call.argument("data");
        int alignment = call.argument("alignment");
        int attribute = call.argument("attribute");
        int textSize = call.argument("textSize");
        result.success(printText(data, alignment, attribute, textSize));
      }catch (Exception e){
        System.out.println(e);
      }
    }
    else {
      result.notImplemented();
    }
  }

  private int connectPrinter(String MAC){
    try{
      return HPRTPrinterHelper.PortOpen("Bluetooth,"+MAC);
    }catch(Exception e){
      System.out.println(e);
      return -1;
    }
  }

  private boolean disconnectPrinter(){
    try {
      return HPRTPrinterHelper.PortClose();
    }catch(Exception e){
      return false;
    }
  }

  private boolean isConnected(){
    try{
      return HPRTPrinterHelper.IsOpened();
    }catch(Exception e){
      return false;
    }
  }

  private int printText(String data,int alignment,int attribute,int textSize){
    try{
      return HPRTPrinterHelper.PrintText(data, alignment, attribute, textSize);
    }catch(Exception e){
      return -1;
    }
  }
}
