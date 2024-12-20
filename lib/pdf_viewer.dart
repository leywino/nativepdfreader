import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

///Main canvas, call it on a full sized page(or almost full sized page)
class PdfViewer extends StatelessWidget {
  const PdfViewer({super.key, this.filePath, e});

  final String? filePath;

  @override
  Widget build(BuildContext context) {
    const String viewType = 'leywin/pdfviewer';

    final Map<String, dynamic> creationParams = <String, dynamic>{
      "filePath": filePath,
    };

    return AndroidView(
      viewType: viewType,
      layoutDirection: TextDirection.ltr,
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
    );
  }
}
