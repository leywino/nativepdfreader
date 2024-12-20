import 'package:flutter/services.dart';

class Nativepdfreader {
  static const platform = MethodChannel('leywin/pdfviewer/params');

  static Future<void> openPdf(String filePath) async {
    try {
      await platform.invokeMethod('openPdf', {'filePath': filePath});
    } on PlatformException catch (e) {
      print("Failed to open PDF: ${e.message}");
    }
  }
}
