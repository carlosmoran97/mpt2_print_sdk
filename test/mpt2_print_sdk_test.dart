import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mpt2_print_sdk/mpt2_print_sdk.dart';

void main() {
  const MethodChannel channel = MethodChannel('mpt2_print_sdk');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Mpt2PrintSdk.platformVersion, '42');
  });
}
