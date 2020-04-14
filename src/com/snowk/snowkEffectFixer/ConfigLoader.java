package com.snowk.snowkEffectFixer;

import java.util.List;
import com.snowk.snowkEffectFixer.BanEffect;

public class ConfigLoader {
	public static List<String> bannedList = BanEffect.snowkPlugin.getConfig().getStringList("bannedEffect");
	public static boolean enableMsg = BanEffect.snowkPlugin.getConfig().getBoolean("enableMsg");
	public static String msg_cancel = BanEffect.snowkPlugin.getConfig().getString("Msg_Cancel").replace("&", "¡ì");
	public static String msg_reload = BanEffect.snowkPlugin.getConfig().getString("Msg_Reload").replace("&", "¡ì");
	public static String msg_reject = BanEffect.snowkPlugin.getConfig().getString("Msg_Reject").replace("&", "¡ì");
}
