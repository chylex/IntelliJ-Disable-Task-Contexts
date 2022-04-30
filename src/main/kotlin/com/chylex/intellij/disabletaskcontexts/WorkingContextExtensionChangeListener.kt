package com.chylex.intellij.disabletaskcontexts

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.ExtensionPoint
import com.intellij.tasks.context.WorkingContextProvider

object WorkingContextExtensionChangeListener : Runnable {
	private val logger = Logger.getInstance("DisableTaskContextsPlugin")
	private var isRunning = false
	
	override fun run() {
		if (!isRunning && WorkingContextProvider.EP_NAME.hasAnyExtensions()) {
			isRunning = true
			
			val point = WorkingContextProvider.EP_NAME.point
			
			try {
				for (extension in point.extensions) {
					point.unregisterExtension(extension.javaClass)
				}
				
				if (point.size() == 0) {
					logger.info("Unregistered task context providers.")
				}
				else {
					logUnregisteringError(point)
				}
			} catch (e: Exception) {
				logUnregisteringError(point, e)
			} finally {
				isRunning = false
			}
		}
	}
	
	private fun logUnregisteringError(point: ExtensionPoint<WorkingContextProvider>, exception: Exception? = null) {
		logger.error("Failed unregistering task context providers. Remaining providers:\n" + point.extensions.joinToString("\n") { '\t' + it.javaClass.canonicalName }, exception)
	}
}
