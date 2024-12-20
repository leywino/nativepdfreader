package com.leywin.nativepdfreader

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.github.barteksc.pdfviewer.PDFView
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import java.io.File

class PdfViewer(
    private val context: Context,
    creationParams: Map<*, *>?,
    private val channel: MethodChannel
) : PlatformView {

    private var pdfFilePath: String? = null
    private var view: FrameLayout? = null
    private var pdfView: PDFView? = null

    init {
        setupMethodChannel(channel)
        Log.d("PdfViewer", "Initializing PdfViewer")

        // Create FrameLayout to hold PDF components
        view = FrameLayout(context).apply {
            pdfView = PDFView(context, null)

            addView(pdfView)
        }

        if (creationParams != null) {
            Log.d("PdfViewer", "CreationParams: $creationParams")
            pdfFilePath = creationParams["filePath"] as? String
            Log.d("PdfViewer", "File path retrieved: $pdfFilePath")
            openPdf(pdfFilePath)
        } else {
            Log.w("PdfViewer", "CreationParams are null")
        }
    }

    override fun getView(): View {
        Log.d("PdfViewer", "getView called")
        return view ?: throw IllegalStateException("View is null")
    }

    override fun dispose() {
        Log.d("PdfViewer", "dispose called")
    }

    private fun setupMethodChannel(channel: MethodChannel) {
        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "openPdf" -> {
                    val filePath = call.argument<String>("filePath")
                    if (filePath != null) {
                        openPdf(filePath)
                        result.success("PDF opened successfully")
                    } else {
                        result.error("INVALID_ARGUMENT", "File path is required", null)
                    }
                }

                else -> {
                    result.notImplemented()
                }
            }
        }
        Log.d("PdfViewer", "MethodChannel set up successfully")
    }

    private fun openPdf(filePath: String?) {
        if (filePath != null) {
            try {
                Log.d("PdfViewer", "Opening PDF at path: $filePath")
                pdfView?.fromFile(File(filePath))
                    ?.enableSwipe(true)
                    ?.swipeHorizontal(true)
                    ?.enableDoubletap(true)?.pageFling(true)
                    ?.onLoad { Log.d("PdfViewer", "PDF loaded successfully") }
                    ?.onError { t -> Log.e("PdfViewer", "Error loading PDF", t) }
                    ?.load()
            } catch (e: Exception) {
                Log.e("PdfViewer", "Error opening PDF", e)
            }
        } else {
            Log.w("PdfViewer", "File path is null")
        }
    }
}
