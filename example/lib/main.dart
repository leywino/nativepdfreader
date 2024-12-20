import 'package:flutter/material.dart';
import 'package:nativepdfreader/pdf_viewer.dart';
import 'package:permission_handler/permission_handler.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await requestStoragePermission();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'PDF Viewer Example',
      home: PdfViewerExample(),
    );
  }
}

class PdfViewerExample extends StatelessWidget {
  const PdfViewerExample({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: PdfViewer(
        filePath: "/storage/emulated/0/Physics.pdf",
      ),
    );
  }
}

Future<void> requestStoragePermission() async {
  var status = await Permission.storage.status;
  if (!status.isGranted) {
    await Permission.storage.request();
  }
}
