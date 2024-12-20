package com.leywin.nativepdfreader

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


class NativepdfreaderPlugin : FlutterPlugin, MethodCallHandler {

  private lateinit var channel: MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "leywin/pdfviewer/params")
    channel.setMethodCallHandler(this)

    flutterPluginBinding.platformViewRegistry
      .registerViewFactory("leywin/pdfviewer", NativepdfViewFactory(channel))
  }

  override fun onMethodCall(call: MethodCall, result: Result) {

  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}