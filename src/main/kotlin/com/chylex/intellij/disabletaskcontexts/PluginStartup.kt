package com.chylex.intellij.disabletaskcontexts

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.tasks.context.WorkingContextProvider

class PluginStartup : StartupActivity.DumbAware {
	private var isInitialized = false
	
	override fun runActivity(project: Project) {
		if (isInitialized) {
			return
		}
		
		isInitialized = true
		WorkingContextProvider.EP_NAME.addChangeListener(WorkingContextExtensionChangeListener, null)
		WorkingContextExtensionChangeListener.run()
	}
}
