package com.chylex.intellij.disabletaskcontexts

import com.intellij.openapi.diagnostic.Logger
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
					logger.error("Failed unregistering task context providers.")
				}
			} catch (e: Exception) {
				logger.error("Failed unregistering task context providers.", e)
			} finally {
				isRunning = false
			}
		}
	}
}
