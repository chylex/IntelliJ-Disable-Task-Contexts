package com.chylex.intellij.disabletaskcontexts

import com.intellij.ide.plugins.CannotUnloadPluginException
import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor

class PluginLoadListener : DynamicPluginListener {
	override fun checkUnloadPlugin(pluginDescriptor: IdeaPluginDescriptor) {
		if (pluginDescriptor.pluginId.idString == "com.chylex.intellij.disabletaskcontexts.DisableTaskContexts") {
			throw CannotUnloadPluginException("A restart is required to unload Disable Task Contexts plugin.")
		}
	}
}
