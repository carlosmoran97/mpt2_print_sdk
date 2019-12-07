import 'dart:async';

import 'package:flutter/services.dart';

class Mpt2PrintSdk {
  static const MethodChannel _platformChannel =
      const MethodChannel('mpt2_print_sdk');

  static Future<String> get platformVersion async {
    final String version =
        await _platformChannel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<int> connectPrinter(String mac) async {
    return await _platformChannel
        .invokeMethod('connectPrinter', <String, dynamic>{"mac": mac});
  }

  static Future<int> printText(
    String data, {
    int alignment = 0,
    int attribute = 0,
    int textSize = 0,
  }) async {
    return await _platformChannel.invokeMethod('printText', <String, dynamic>{
      "data": data,
      "alignment": alignment,
      "attribute": attribute,
      "textSize": textSize,
    });
  }

  static Future<bool> disconnectPrinter() async {
    return await _platformChannel.invokeMethod('disconnectPrinter');
  }
}
