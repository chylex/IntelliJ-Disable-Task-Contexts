package com.chylex.intellij.disabletaskcontexts

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.impl.ExtensionComponentAdapter
import com.intellij.tasks.context.WorkingContextProvider

@Suppress("UnstableApiUsage")
object WorkingContextExtensionChangeListener : Runnable {
	private val logger = Logger.getInstance("DisableTaskContextsPlugin")
	private val unregisterAllPredicate: (String, ExtensionComponentAdapter) -> Boolean = { _, _ -> false }
	
	private var isRunning = false
	
	override fun run() {
		if (!isRunning && WorkingContextProvider.EP_NAME.hasAnyExtensions()) {
			isRunning = true
			
			try {
				WorkingContextProvider.EP_NAME.point.unregisterExtensions(unregisterAllPredicate, false)
				
				if (!WorkingContextProvider.EP_NAME.hasAnyExtensions()) {
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
